package es.source.code.fragment;

import android.widget.TextView;
import butterknife.BindView;
import es.source.code.App;
import es.source.code.R;
import es.source.code.adapter.OrderRecyclerAdapter;
import es.source.code.callback.OnItemBtnClickListener;
import es.source.code.model.Food;

/**
 * @author Daniel
 * @ClassName: CustomOrderFragment.java
 * @Description: 查看订单fragment类
 * @date 2017/10/12 17:05
 */
public class CustomOrderFragment extends BaseRecyclerFragment<Food> {

    public static final int STATUS_ORDER = 1;
    public static final int STATUS_BILL = 2;

    @BindView(R.id.tv_food_amount)
    TextView tvFoodAmount;
    @BindView(R.id.tv_order_amount)
    TextView tvOrderAmount;

    private int status;

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    protected void initLayout() {
        contentView = inflater.inflate(R.layout.frag_order_list, container, false);
    }

    @Override
    protected void initData() {
        dataList = App.getInstance().getFoodList();
        listAdapter = new OrderRecyclerAdapter(dataList, mContext, R.layout.recycler_item_food);
        listAdapter.setOnItemBtnClickListener(new OnItemBtnClickListener<Food>() {
            @Override
            public void onClick(Food food, int position) {
                dataList.remove(food);
                listAdapter.updateData(dataList);
            }
        });
        rvList.setAdapter(listAdapter);

        updateCount();
    }

    private void updateCount() {
        String foodAmount = getString(R.string.text_food_amount) + String.valueOf(dataList.size());
        int amount = 0; // 订单总价
        for (Food food : dataList) {
            amount += food.getPrice();
        }
        String orderAmount = getString(R.string.text_order_amount) + String.valueOf(amount);
        tvFoodAmount.setText(foodAmount);
        tvOrderAmount.setText(orderAmount);
    }

}
