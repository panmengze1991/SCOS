package es.source.code.fragment;

import es.source.code.R;
import es.source.code.model.Food;

/**
 * @author Daniel
 * @ClassName: ColdFoodFragment.java
 * @Description: 冷菜Fragment
 * @date 2017/10/11 14:48
 */
public class ColdFoodFragment extends BaseFoodFragment {

    @Override
    protected void getDataList() {
        dataList.add(new Food("椒油素鸡",10,false,R.drawable.food_cold_jysj));
        dataList.add(new Food("开胃泡菜",12,false,R.drawable.food_cold_kwpc));
        dataList.add(new Food("凉拌海带丝",10,false,R.drawable.food_cold_lbhds));
        dataList.add(new Food("凉拌黄瓜",10,false,R.drawable.food_cold_lbhg));
        dataList.add(new Food("卤牛肉",30,false,R.drawable.food_cold_lnr));
        dataList.add(new Food("东北家拌凉菜",20,false,R.drawable.food_cold_dbjlbc));
        dataList.add(new Food("浇汁豆腐",15,false,R.drawable.food_cold_jzdf));
        dataList.add(new Food("青椒拌干丝",10,false,R.drawable.food_cold_qjbgs));
    }
}
