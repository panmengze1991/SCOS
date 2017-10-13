package es.source.code.fragment;

import es.source.code.R;
import es.source.code.model.Food;

/**
 * @ClassName: HotFoodFragment.java
 * @Description: 热菜Fragment
 * @author Daniel
 * @date 2017/10/11 14:48
 */
public class HotFoodFragment extends BaseFoodFragment {

    @Override
    protected void getDataList() {
        dataList.add(new Food("干煸豆角",20,false,R.drawable.food_hot_gbdj));
        dataList.add(new Food("宫保鸡丁",30,false,R.drawable.food_hot_gbjd));
        dataList.add(new Food("红烧茄子",20,false,R.drawable.food_hot_hsqz));
        dataList.add(new Food("红烧肉",45,false,R.drawable.food_hot_hsr));
        dataList.add(new Food("红烧鱼",40,false,R.drawable.food_hot_hsy));
        dataList.add(new Food("可乐鸡翅",30,false,R.drawable.food_hot_kljc));
        dataList.add(new Food("麻婆豆腐",25,false,R.drawable.food_hot_mpdf));
        dataList.add(new Food("羊肉汤",40,false,R.drawable.food_hot_yrt));
        dataList.add(new Food("孜然羊肉",50,false,R.drawable.food_hot_zryr));
    }
}
