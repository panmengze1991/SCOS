package es.source.code.fragment;

import es.source.code.R;
import es.source.code.model.Food;

/**
 * @ClassName: DrinkingFragment.java
 * @Description: 饮料Fragment
 * @author Daniel
 * @date 2017/10/11 14:48
 */
public class DrinkingFragment extends BaseFoodFragment {

    @Override
    protected void getDataList() {
        dataList.add(new Food("百香多多",20,false,R.drawable.food_drink_bxdd));
        dataList.add(new Food("草莓香蕉奶昔",18,false,R.drawable.food_drink_cmxjnx));
        dataList.add(new Food("红茶",10,false,R.drawable.food_drink_hc));
        dataList.add(new Food("红枣核桃山药饮",15,false,R.drawable.food_drink_hzhtsyy));
        dataList.add(new Food("玫瑰情人露",22,false,R.drawable.food_drink_mgqrl));
    }
}
