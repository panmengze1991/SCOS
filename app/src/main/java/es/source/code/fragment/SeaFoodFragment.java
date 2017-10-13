package es.source.code.fragment;

import es.source.code.R;
import es.source.code.model.Food;

/**
 * @ClassName: SeaFoodFragment.java
 * @Description: 海鲜Fragment
 * @author Daniel
 * @date 2017/10/11 14:48
 */
public class SeaFoodFragment extends BaseFoodFragment {

    @Override
    protected void getDataList() {
        dataList.add(new Food("大虾两吃",50,false,R.drawable.food_sea_dxlc));
        dataList.add(new Food("海螺炒韭菜",40,false,R.drawable.food_sea_hlcjc));
        dataList.add(new Food("海鲜汤",40,false,R.drawable.food_sea_hxt));
        dataList.add(new Food("节瓜章鱼鸡脚汤",55,false,R.drawable.food_sea_jgzyjjt));
        dataList.add(new Food("麻辣小龙虾",50,false,R.drawable.food_sea_mlxlx));
        dataList.add(new Food("啤酒海螺",40,false,R.drawable.food_sea_pjhl));
        dataList.add(new Food("清炒虾仁",44,false,R.drawable.food_sea_qcxr));
        dataList.add(new Food("清蒸梭子蟹",60,false,R.drawable.food_sea_qzszx));
        dataList.add(new Food("水蟹冬瓜汤",60,false,R.drawable.food_sea_sxdgt));
    }
}
