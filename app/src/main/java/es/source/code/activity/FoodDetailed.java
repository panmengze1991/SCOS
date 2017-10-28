package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.source.code.App;
import es.source.code.R;
import es.source.code.adapter.FoodDetailPageAdapter;
import es.source.code.model.Food;
import es.source.code.utils.Const;

import java.util.List;

public class FoodDetailed extends BaseActivity {

    private static int currentPageIndex = -1;

    List<Food> foodList;

    FoodDetailPageAdapter foodDetailPageAdapter;

    @BindView(R.id.vp_food_detail)
    ViewPager vpFoodDetail;
    @BindView(R.id.btn_order)
    TextView btnOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detailed);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        currentPageIndex = intent.getIntExtra(Const.IntentKey.FOOD_POSITION, 0);
        Bundle bundle = intent.getExtras();
        foodList = bundle.getParcelableArrayList(Const.BundleKey.FOOD_LIST);
        foodDetailPageAdapter = new FoodDetailPageAdapter(foodList, R.layout.layout_food_detail,
                mContext);
        vpFoodDetail.setAdapter(foodDetailPageAdapter);
        vpFoodDetail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPageIndex = position;
                loadButton(foodList.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vpFoodDetail.setCurrentItem(currentPageIndex);
        loadButton(foodList.get(currentPageIndex));
    }

    /**
     * @param food
     * @description: 修改按钮显示
     * @author: Daniel
     */
    private void loadButton(Food food) {
        btnOrder.setText(food.isOrder() ? R.string.btn_cancel_order : food.getStore() > 0 ? R.string.btn_order : R
                .string.btn_empty);
        btnOrder.setBackgroundResource(food.isOrder() ? R.drawable.theme_button_bg_red_selector :
                R.drawable.theme_button_bg_blue_selector);
        // 已点或有库存时都是可以点击的。
        btnOrder.setEnabled(food.getStore() > 0 || food.isOrder());
    }

    @OnClick(R.id.btn_order)
    public void onClick(View v){
        Food food = foodList.get(currentPageIndex);
        food.setOrder(!food.isOrder());
        loadButton(food);
        App.getInstance().operateFoodList(food,food.isOrder());
    }
}
