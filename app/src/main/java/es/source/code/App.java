package es.source.code;

import android.app.Application;
import es.source.code.model.Food;
import es.source.code.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @ClassName: App.java
 * @Description: app类
 * @date 2017/10/11 11:41
 */
public class App extends Application {
    private static App app;

    private User userInfo;
    private List<Food> foodList;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static App getInstance() {
        return app;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    public List<Food> getFoodList() {
        if (foodList == null) {
            foodList = new ArrayList<>();
        }
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    /**
     * @description: 操作定义的FoodList
     * @author: Daniel
     */
    public void operateFoodList(Food food, boolean isAdd) {
        if (foodList == null) {
            foodList = new ArrayList<>();
        }
        if (isAdd && foodList.indexOf(food) == -1) {
            foodList.add(food);
        } else if (!isAdd && foodList.indexOf(food) > -1) {
            foodList.remove(food);
        }
    }
}
