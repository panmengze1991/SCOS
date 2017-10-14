package es.source.code.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.source.code.R;
import es.source.code.adapter.FragmentViewPagerAdapter;
import es.source.code.fragment.ColdFoodFragment;
import es.source.code.fragment.DrinkingFragment;
import es.source.code.fragment.HotFoodFragment;
import es.source.code.fragment.SeaFoodFragment;
import es.source.code.model.User;
import es.source.code.utils.Const;

import java.util.ArrayList;
import java.util.List;

public class FoodView extends BaseActivity {

    private static final int FRAGMENT_COLD_FOOD = 0;
    private static final int FRAGMENT_HOT_FOOD = 1;
    private static final int FRAGMENT_SEA_FOOD = 2;
    private static final int FRAGMENT_DRINKING = 3;

    private static int currentPageIndex = -1;
    List<Fragment> fragmentList = new ArrayList<>();

    User user;

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
        if(bundle!=null) {
            user = (User) bundle.get(Const.ParcelableKey.USER);
        }
        initView();
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

        FragmentViewPagerAdapter pagerAdapter = new FragmentViewPagerAdapter(getFragmentManager()
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_order:
                break;
            case R.id.action_bill:
                Intent intent = getIntent();
                intent.setClass(mContext,FoodOrderView.class);
                startActivity(intent);
                break;
            case R.id.action_service:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
