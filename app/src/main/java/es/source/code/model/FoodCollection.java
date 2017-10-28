package es.source.code.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Author        Daniel
 * Class:        FoodCollection
 * Date:         2017/10/25 19:23
 * Description:  食品列表的集合
 */
public class FoodCollection implements Parcelable {

    private List<Food> coldFoodList;
    private List<Food> hotFoodList;
    private List<Food> seaFoodList;
    private List<Food> drinkingList;

    public FoodCollection(List<Food> coldFoodList, List<Food> hotFoodList, List<Food> seaFoodList, List<Food>
            drinkingList) {
        this.coldFoodList = coldFoodList;
        this.hotFoodList = hotFoodList;
        this.seaFoodList = seaFoodList;
        this.drinkingList = drinkingList;
    }

    public List<Food> getColdFoodList() {
        return coldFoodList;
    }

    public void setColdFoodList(List<Food> coldFoodList) {
        this.coldFoodList = coldFoodList;
    }

    public List<Food> getHotFoodList() {
        return hotFoodList;
    }

    public void setHotFoodList(List<Food> hotFoodList) {
        this.hotFoodList = hotFoodList;
    }

    public List<Food> getSeaFoodList() {
        return seaFoodList;
    }

    public void setSeaFoodList(List<Food> seaFoodList) {
        this.seaFoodList = seaFoodList;
    }

    public List<Food> getDrinkingList() {
        return drinkingList;
    }

    public void setDrinkingList(List<Food> drinkingList) {
        this.drinkingList = drinkingList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.coldFoodList);
        dest.writeTypedList(this.hotFoodList);
        dest.writeTypedList(this.seaFoodList);
        dest.writeTypedList(this.drinkingList);
    }

    protected FoodCollection(Parcel in) {
        this.coldFoodList = in.createTypedArrayList(Food.CREATOR);
        this.hotFoodList = in.createTypedArrayList(Food.CREATOR);
        this.seaFoodList = in.createTypedArrayList(Food.CREATOR);
        this.drinkingList = in.createTypedArrayList(Food.CREATOR);
    }

    public static final Parcelable.Creator<FoodCollection> CREATOR = new Parcelable.Creator<FoodCollection>() {
        @Override
        public FoodCollection createFromParcel(Parcel source) {
            return new FoodCollection(source);
        }

        @Override
        public FoodCollection[] newArray(int size) {
            return new FoodCollection[size];
        }
    };
}
