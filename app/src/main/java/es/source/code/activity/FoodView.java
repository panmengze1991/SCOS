package es.source.code.activity;

import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.*;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.gson.Gson;
import es.source.code.App;
import es.source.code.R;
import es.source.code.adapter.FragmentViewPagerAdapter;
import es.source.code.callback.SimpleObserver;
import es.source.code.fragment.*;
import es.source.code.model.Food;
import es.source.code.model.FoodCollection;
import es.source.code.model.User;
import es.source.code.service.ServerObserverService;
import es.source.code.utils.Const;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class FoodView extends BaseActivity {

    protected String TAG = "FoodView";

    private static final int FRAGMENT_COLD_FOOD = 0;
    private static final int FRAGMENT_HOT_FOOD = 1;
    private static final int FRAGMENT_SEA_FOOD = 2;
    private static final int FRAGMENT_DRINKING = 3;

    private static int currentPageIndex = -1;
    List<BaseFoodFragment> fragmentList = new ArrayList<>();

    User user;

    Messenger cMessenger;
    Messenger sMessenger;

    Menu menu;

    @BindView(R.id.tv_cold_food)
    TextView tvColdFood;
    @BindView(R.id.tv_hot_food)
    TextView tvHotFood;
    @BindView(R.id.tv_sea_food)
    TextView tvSeaFood;
    @BindView(R.id.tv_drinking)
    TextView tvDrinking;
    @BindViews({R.id.tv_cold_food, R.id.tv_hot_food, R.id.tv_sea_food, R.id.tv_drinking})
    List<TextView> tvFoodTabs;
    @BindView(R.id.vp_food)
    ViewPager vpFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_view);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user = (User) bundle.get(Const.BundleKey.USER);
        }
        // 先初始化布局，再通过线程获取数据，之后刷新布局
        initView();
        loadData(App.getInstance().getFoodCollection());
    }

    /**
     * @description: 初始化ViewPager和fragment
     * @author: Daniel
     */
    private void initView() {
        currentPageIndex = FRAGMENT_COLD_FOOD;
        ColdFoodFragment coldFoodFragment = new ColdFoodFragment();
        HotFoodFragment hotFoodFragment = new HotFoodFragment();
        SeaFoodFragment seaFoodFragment = new SeaFoodFragment();
        DrinkingFragment drinkingFragment = new DrinkingFragment();

        fragmentList.add(coldFoodFragment);
        fragmentList.add(hotFoodFragment);
        fragmentList.add(seaFoodFragment);
        fragmentList.add(drinkingFragment);

        FragmentViewPagerAdapter<BaseFoodFragment> pagerAdapter = new FragmentViewPagerAdapter<>(getFragmentManager()
                , vpFood, fragmentList);
        vpFood.setAdapter(pagerAdapter);
        vpFood.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectTab(tvFoodTabs.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        onTabClick(tvColdFood);
    }

    /**
     * author:      Daniel
     * description: 获取食品数据
     */
    private void loadData(final FoodCollection foodCollection) {
        Observable.create(new ObservableOnSubscribe<FoodCollection>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<FoodCollection> e) throws Exception {
//                FoodCollection foodCollection = App.getInstance().getFoodCollection();
                e.onNext(foodCollection);
            }
        })
                .map(new Function<FoodCollection, FoodCollection>() {
                    @Override
                    public FoodCollection apply(@NonNull FoodCollection foodCollection) throws Exception {
                        setFoodStatus(foodCollection.getColdFoodList());
                        setFoodStatus(foodCollection.getHotFoodList());
                        setFoodStatus(foodCollection.getSeaFoodList());
                        setFoodStatus(foodCollection.getDrinkingList());
                        return foodCollection;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleObserver<FoodCollection>() {
                    @Override
                    public void onEvent(FoodCollection foodCollection) {
                        refreshFragmentData(foodCollection);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initBinder();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
    }

    /**
     * author:      Daniel
     * description: 初始化服务绑定
     */
    private void initBinder() {
        cMessenger = new Messenger(sMessageHandler);
        Intent service = new Intent(getApplicationContext(), ServerObserverService.class);
        bindService(service, connection, Context.BIND_AUTO_CREATE);
    }

    /**
     * author:      Daniel
     * description: 设置食物点击状态
     */
    private void setFoodStatus(List<Food> originFoodList){
        for (Food orderFood : App.getInstance().getFoodList()){
            for(Food originFood : originFoodList){
                if(originFood.getFoodName().equals(orderFood.getFoodName())){
                    originFood.setOrder(true);
                    break;
                }
            }
        }
    }

    /**
     * author:      Daniel
     * description: 更新数据
     */
    private void refreshFragmentData(FoodCollection foodCollection) {
        fragmentList.get(FRAGMENT_COLD_FOOD).setAndRefresh(foodCollection.getColdFoodList());
        fragmentList.get(FRAGMENT_HOT_FOOD).setAndRefresh(foodCollection.getHotFoodList());
        fragmentList.get(FRAGMENT_SEA_FOOD).setAndRefresh(foodCollection.getSeaFoodList());
        fragmentList.get(FRAGMENT_DRINKING).setAndRefresh(foodCollection.getDrinkingList());
    }

    @OnClick({R.id.tv_cold_food, R.id.tv_hot_food, R.id.tv_sea_food, R.id.tv_drinking})
    public void onTabClick(View tab) {
        selectTab(tab);
        vpFood.setCurrentItem(currentPageIndex);
    }

    /**
     * @description: 选中需要的tab
     * @author: Daniel
     */
    private void selectTab(View tab) {
        for (int position = 0; position < tvFoodTabs.size(); position++) {
            TextView tvTab = tvFoodTabs.get(position);
            if (tab.getId() == tvTab.getId()) {
                currentPageIndex = position;
            }
            tvTab.setSelected(false);
        }
        tab.setSelected(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_food_view, menu);
        this.menu = menu;
//        menu.getItem(3).setTitle(R.string.menu_auto_refresh_off);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_order:
                break;
            case R.id.action_bill:
                Intent intent = getIntent();
                intent.setClass(mContext, FoodOrderView.class);
                startActivity(intent);
                break;
            case R.id.action_service:
                break;
            case R.id.action_auto_refresh:

                handleRefreshClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * author:      Daniel
     * description: 自动刷新开关
     */
    private void handleRefreshClick() {
        MenuItem item = menu.getItem(3);
        String title = item.getTitle().toString();
        int what;
        if (getString(R.string.menu_auto_refresh_on).equals(title)) {
            // 标题设置为关闭
            title = getString(R.string.menu_auto_refresh_off);
            // 开启动作
//            Log.d(TAG,"App.getInstance().getFoodList().size ?" +(App.getInstance().getFoodList().size()));
            what = Const.EventKey.MSG_FOOD_GET_START;
        } else {
            // 标题设置为开启
            title = getString(R.string.menu_auto_refresh_on);
            // 关闭动作
            what = Const.EventKey.MSG_FOOD_GET_STOP;
        }
        // 执行发送消息
        try {
            Message message = Message.obtain();
            message.what = what;
            message.replyTo = cMessenger;
            sMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        item.setTitle(title);
    }


    // messenger初始化
    SMessageHandler sMessageHandler = new SMessageHandler(FoodView.this);

    /**
     * author:      Daniel
     * description: 来自服务端消息的处理
     */
    private class SMessageHandler extends Handler {

        WeakReference<FoodView> foodViewWeakReference;

        SMessageHandler(FoodView foodView) {
            foodViewWeakReference = new WeakReference<>(foodView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            FoodView foodView = foodViewWeakReference.get();

            switch (msg.what) {
                case Const.EventKey.MSG_FOOD_GET_SUCCESS:
                    Bundle bundle = msg.getData();
//                    Log.d(TAG, "bundleget == null ?" + (bundle.get(Const.BundleKey.FOOD_COLLECTION) == null));
//                    FoodCollection foodCollection = bundle.getParcelable(Const.ParcelableKey.FOOD_COLLECTION);
                    String foodCollectionString = bundle.getString(Const.BundleKey.FOOD_COLLECTION);
                    FoodCollection foodCollection = new Gson().fromJson(foodCollectionString, FoodCollection.class);

//                    foodView.showToast("成功！");
//                    Log.d(TAG, "读取成功！库存为" + foodCollection.getColdFoodList().get(0).getStore());
                    loadData(foodCollection);
                    break;
            }
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "连接建立");
            sMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "连接断开");

        }
    };

}
