package es.source.code.adapter;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import es.source.code.R;
import es.source.code.callback.OnItemBtnClickListener;
import es.source.code.model.Food;
import es.source.code.utils.CommonUtil;

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
        TextView tvFoodStore = (TextView) holder.getView(R.id.tv_food_store);
        ImageView ivFood = (ImageView) holder.getView(R.id.iv_food);
        TextView btnOrder = (TextView) holder.getView(R.id.btn_order);

        String store = "剩余：" + String.valueOf(food.getStore()) + "份";
        String price = String.valueOf(food.getPrice()) + "元";

        tvFoodName.setText(food.getFoodName());
        tvFoodPrice.setText(CommonUtil.getColorString(price, 0, price.length() - 1, R.color.google_red, mContext));
        tvFoodStore.setText(CommonUtil.getColorString(store, 3, store.length() - 1, R.color.google_blue, mContext));
        Glide.with(mContext)
                .load(food.getImgId())
                .into(ivFood);
        btnOrder.setText(food.isOrder() ? R.string.btn_cancel_order : food.getStore() > 0 ? R.string.btn_order : R
                .string.btn_empty);
        btnOrder.setBackgroundResource(food.isOrder() ? R.drawable.theme_button_bg_red_selector :
                R.drawable.theme_button_bg_blue_selector);
        // 已点或有库存时都是可以点击的。
        btnOrder.setEnabled(food.getStore() > 0 || food.isOrder());
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
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(food, position);
                }
            }
        });
    }


}
