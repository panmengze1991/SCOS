package es.source.code.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import es.source.code.App;
import es.source.code.R;
import es.source.code.activity.FoodDetailed;
import es.source.code.adapter.FoodRecyclerAdapter;
import es.source.code.callback.OnItemBtnClickListener;
import es.source.code.callback.OnItemClickListener;
import es.source.code.model.Food;
import es.source.code.utils.Const;

import java.util.ArrayList;

/**
 * @author Daniel
 * @ClassName: BaseOrderFragment.java
 * @Description: 菜单fragment基类
 * @date 2017/10/12 17:05
 */
public abstract class BaseFoodFragment extends BaseRecyclerFragment<Food> {

    @Override
    protected void initLayout() {
        contentView = inflater.inflate(R.layout.frag_food_list, container, false);
    }

    @Override
    protected void initData() {
        getDataList();
        listAdapter = new FoodRecyclerAdapter(dataList, mContext, R.layout.recycler_item_food);
        listAdapter.setOnItemBtnClickListener(new OnItemBtnClickListener<Food>() {
            @Override
            public void onClick(Food food, int position) {
                food.setOrder(!food.isOrder());
                listAdapter.updateData(dataList);
                App.getInstance().operateFoodList(food,food.isOrder());
            }
        });
        listAdapter.setOnItemClickListener(new OnItemClickListener<Food>() {
            @Override
            public void onClick(Food food, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Const.ParcelableKey.FOOD_LIST, (ArrayList<? extends
                        Parcelable>) dataList);
                Intent intent = new Intent(mContext, FoodDetailed.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rvList.setAdapter(listAdapter);
    }

    /**
     * @description: 传入食物数据到dataList中
     * @author: Daniel
     */
    protected abstract void getDataList();
}
