package es.source.code;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import es.source.code.model.Food;
import es.source.code.model.User;
import es.source.code.utils.Const;

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

    private List<Food> foodList;

    @Override
    public void onCreate() {
        super.onCreate();
        setLoginStatus(Const.SharedPreferenceValue.LOGIN_FAILED); // 默认不让登陆
        app = this;
    }

    public static App getInstance() {
        return app;
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

    /**
     * author:      Daniel
     * description: 从sp中获取user
     */
    public User getUser() {
        SharedPreferences sp = getSharedPreferences(Const.SharedPreferenceKey.PROFILE, Context.MODE_PRIVATE);
        String userString = sp.getString(Const.SharedPreferenceKey.USER, null);
        return new Gson().fromJson(userString, User.class);
    }

    /**
     * author:      Daniel
     * description: 设置user到sp中
     */
    public App setUser(User user) {
        SharedPreferences sp = getSharedPreferences(Const.SharedPreferenceKey.PROFILE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String userString = gson.toJson(user);
        sp.edit().putString(Const.SharedPreferenceKey.USER, userString).apply();
        return getInstance();
    }

    /**
     * author:      Daniel
     * description: 获取登录状态
     */
    public int getLoginStatus() {
        SharedPreferences sp = getSharedPreferences(Const.SharedPreferenceKey.PROFILE, Context.MODE_PRIVATE);
        return sp.getInt(Const.SharedPreferenceKey.LOGIN_STATUS, 0);
    }

    /**
     * author:      Daniel
     * description: 设置登录状态到sp中
     */
    public App setLoginStatus(int loginStatus) {
        SharedPreferences sp = getSharedPreferences(Const.SharedPreferenceKey.PROFILE, Context.MODE_PRIVATE);
        sp.edit().putInt(Const.SharedPreferenceKey.LOGIN_STATUS, loginStatus).apply();
        return getInstance();
    }
}
