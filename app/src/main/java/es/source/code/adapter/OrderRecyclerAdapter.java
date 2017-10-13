package es.source.code.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import es.source.code.R;
import es.source.code.model.Food;

import java.util.List;

/**
 * @author Daniel
 * @ClassName: OrderRecyclerAdapter.java
 * @Description: 订单列表adapter
 * @date 2017/10/11 20:47
 */
public class OrderRecyclerAdapter extends BaseRecyclerAdapter<Food> {

    private boolean showButton;

    public OrderRecyclerAdapter(List<Food> dataList, Context mContext, int layoutId) {
        super(dataList, mContext, layoutId);
    }

    protected void bindData(BaseViewHolder holder, final int position) {
        final Food food = dataList.get(position);
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
        btnOrder.setVisibility(showButton?View.VISIBLE:View.GONE);
        btnOrder.setText(R.string.btn_cancel_order);
        btnOrder.setBackgroundResource(R.drawable.theme_button_bg_red_selector);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                food.setOrder(!food.isOrder());
                if(onItemBtnClickListener != null){
                    onItemBtnClickListener.onClick(food,position);
                }
            }
        });
    }


    public boolean isShowButton() {
        return showButton;
    }

    public void setShowButton(boolean showButton) {
        this.showButton = showButton;
    }
}
