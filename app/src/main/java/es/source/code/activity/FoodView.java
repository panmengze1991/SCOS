package es.source.code.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.source.code.R;
import es.source.code.adapter.FragmentViewPagerAdapter;
import es.source.code.fragment.ColdFoodFragment;

import java.util.ArrayList;
import java.util.List;

public class FoodView extends BaseActivity {

    private static final int FRAGMENT_COLD_FOOD = 0;
    private static final int FRAGMENT_HOT_FOOD = 1;
    private static final int FRAGMENT_SEA_FOOD = 2;
    private static final int FRAGMENT_DRINKING = 3;

    private static int currentPageIndex = -1;
    List<Fragment> fragmentList = new ArrayList<>();

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

        initView();
    }

    private void initView() {
        currentPageIndex = FRAGMENT_COLD_FOOD;
        ColdFoodFragment coldFoodFragment = new ColdFoodFragment();
        ColdFoodFragment hotFoodFragment = new ColdFoodFragment();
        ColdFoodFragment seaFoodFragment = new ColdFoodFragment();
        ColdFoodFragment drinkingFragment = new ColdFoodFragment();

        fragmentList.add(coldFoodFragment);
        fragmentList.add(hotFoodFragment);
        fragmentList.add(seaFoodFragment);
        fragmentList.add(drinkingFragment);

        FragmentViewPagerAdapter pagerAdapter = new FragmentViewPagerAdapter(getFragmentManager()
                , vpFood, fragmentList);
        vpFood.setAdapter(pagerAdapter);
        onTabClick(tvColdFood);
    }

    @OnClick({R.id.tv_cold_food,R.id.tv_hot_food,R.id.tv_sea_food,R.id.tv_drinking})
    public void onTabClick(View tab){
        for(int position = 0;position<tvFoodTabs.size();position++){
            TextView tvTab = tvFoodTabs.get(position);

            if(tab.getId() == tvTab.getId()){
                vpFood.setCurrentItem(position);
            }
            tvTab.setSelected(false);
        }
        tab.setSelected(true);
    }
}
