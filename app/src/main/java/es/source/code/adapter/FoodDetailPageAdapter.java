package es.source.code.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import es.source.code.R;
import es.source.code.model.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @ClassName: FoodDetailPageAdapter.java
 * @Description: 食品详情adapter
 * @date 2017/10/13 19:22
 */
public class FoodDetailPageAdapter extends PagerAdapter {

    private List<Food> foodList;
    private List<View> viewList;
    private int resId;
    private Context mContext;

    public FoodDetailPageAdapter(List<Food> foodList, int resId, Context mContext) {
        this.foodList = foodList;
        this.resId = resId;
        this.mContext = mContext;
        initViewList();
    }

    private void initViewList() {
        viewList = new ArrayList<>();
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        for (Food food : foodList) {
            viewList.add(inflater.inflate(resId, null));
        }
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View pageView = viewList.get(position);
        Food food = foodList.get(position);

        TextView tvFoodName = (TextView) pageView.findViewById(R.id.tv_food_name);
        TextView tvFoodPrice = (TextView) pageView.findViewById(R.id.tv_food_price);
        ImageView ivFood = (ImageView) pageView.findViewById(R.id.iv_food);

        tvFoodName.setText(food.getFoodName());

        String price = String.valueOf(food.getPrice()) + mContext.getString(R.string.unit_yuan);
        tvFoodPrice.setText(price);

        Glide.with(mContext).load(food.getImgId()).into(ivFood);

        container.addView(viewList.get(position));


        return viewList.get(position);
    }
}
