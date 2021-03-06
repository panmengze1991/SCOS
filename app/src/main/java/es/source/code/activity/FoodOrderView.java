package es.source.code.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.source.code.App;
import es.source.code.R;
import es.source.code.adapter.FragmentViewPagerAdapter;
import es.source.code.callback.SimpleObserver;
import es.source.code.fragment.CustomOrderFragment;
import es.source.code.model.User;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FoodOrderView extends BaseActivity {

    private static final String TAG = "FoodOrderView";

    private static final int FRAGMENT_ORDER = 0;
    private static final int FRAGMENT_BILL = 1;

    private static int currentPageIndex = -1;
    List<Fragment> fragmentList = new ArrayList<>();

    User user;

    boolean isFinish;

    @BindView(R.id.tv_tab_order)
    TextView tvTabOrder;
    @BindView(R.id.tv_tab_bill)
    TextView tvTabBill;
    @BindViews({R.id.tv_tab_order, R.id.tv_tab_bill})
    List<TextView> tvOrderTabs;
    @BindView(R.id.vp_order)
    ViewPager vpOrder;
    @BindView(R.id.btn_submit)
    TextView btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order_view);
        ButterKnife.bind(this);
//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) {
//            user = (User) bundle.get(Const.ParcelableKey.USER);
//        }
        user = App.getInstance().getUser();
        isFinish = false;
        initView();
    }

    private void initView() {
        currentPageIndex = FRAGMENT_BILL;
        CustomOrderFragment orderFragment = new CustomOrderFragment();
        orderFragment.setStatus(CustomOrderFragment.STATUS_ORDER);
        CustomOrderFragment billFragment = new CustomOrderFragment();
        billFragment.setStatus(CustomOrderFragment.STATUS_BILL);

        fragmentList.add(orderFragment);
        fragmentList.add(billFragment);

        FragmentViewPagerAdapter pagerAdapter = new FragmentViewPagerAdapter(getFragmentManager()
                , vpOrder, fragmentList);
        vpOrder.setAdapter(pagerAdapter);
        vpOrder.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectTab(tvOrderTabs.get(position));
                loadButton();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        onTabClick(tvTabOrder);
        loadButton();
    }

    @OnClick({R.id.tv_tab_order, R.id.tv_tab_bill})
    public void onTabClick(View tab) {
        selectTab(tab);
        vpOrder.setCurrentItem(currentPageIndex);
    }

    @OnClick(R.id.btn_submit)
    public void onClick(View submit) {
        if (currentPageIndex == FRAGMENT_BILL) {
            showProgress(R.string.dialog_pay);
            Observable.timer(6, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SimpleObserver<Long>() {
                        @Override
                        public void onEvent(Long t) {
                            dismissProgress();
                            int amount = App.getInstance().getFoodList().size();
                            int price = ((CustomOrderFragment) (fragmentList.get(FRAGMENT_BILL))).getPrice();
                            showToast(getString(R.string.toast_pay_success, amount, price));
                            isFinish = true;
                            loadButton();
                        }
                    });
        }
    }

    /**
     * @description: 选中需要的tab
     * @author: Daniel
     */
    private void selectTab(View tab) {
        for (int position = 0; position < tvOrderTabs.size(); position++) {
            TextView tvTab = tvOrderTabs.get(position);
            if (tab.getId() == tvTab.getId()) {
                currentPageIndex = position;
            }
            tvTab.setSelected(false);
        }
        tab.setSelected(true);
    }

    /**
     * author:      Daniel
     * description: 设置按钮
     */
    private void loadButton() {
        // 设置按钮文字
        btnSubmit.setText(currentPageIndex == FRAGMENT_ORDER ? R.string.btn_submit : R.string.btn_bill);

        // 背景
        btnSubmit.setBackgroundResource(currentPageIndex == FRAGMENT_ORDER ? R.drawable.btn_bg_blue_selector : R
                .drawable.btn_bg_green_selector);

        // 设置是否可点击
        btnSubmit.setEnabled(!(currentPageIndex == FRAGMENT_BILL && isFinish));
    }
}
