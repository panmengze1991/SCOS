package es.source.code.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import es.source.code.R;
import es.source.code.callback.OnItemBtnClickListener;
import es.source.code.model.Food;

import java.util.List;

/**
 * @author Daniel
 * @ClassName: FoodListAdapter.java
 * @Description: 食品列表adapter
 * @date 2017/10/11 20:47
 */
public class FoodRecyclerAdapter extends BaseRecyclerAdapter<Food> {


    public FoodRecyclerAdapter(List<Food> dataList, Context mContext, int layoutId) {
        super(dataList, mContext, layoutId);
    }

    protected void bindData(BaseViewHolder holder, final int position) {
        final Food food = dataList.get(position);
        LinearLayout llFoodItem = (LinearLayout) holder.getView(R.id.ll_food_item);
        TextView tvFoodName = (TextView) holder.getView(R.id.tv_food_name);
        TextView tvFoodPrice = (TextView) holder.getView(R.id.tv_food_price);
        ImageView ivFood = (ImageView) holder.getView(R.id.iv_food);
        TextView btnOrder = (TextView) holder.getView(R.id.btn_order);

        tvFoodName.setText(food.getFoodName());
        String price = String.valueOf(food.getPrice()) + "元";
        tvFoodPrice.setText(price);
        Glide.with(mContext)
                .load(food.getImgId())
                .into(ivFood);
        btnOrder.setText(food.isOrder() ? R.string.btn_cancel_order : R.string.btn_order);
        btnOrder.setBackgroundResource(food.isOrder() ? R.drawable.theme_button_bg_red_selector :
                R.drawable.theme_button_bg_blue_selector);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemBtnClickListener != null) {
                    onItemBtnClickListener.onClick(food, position);
                }
            }
        });

        llFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onClick(food,position);
                }
            }
        });
    }


}
