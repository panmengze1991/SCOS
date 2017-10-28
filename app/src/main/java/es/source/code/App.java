package es.source.code;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.ddmeng.preferencesprovider.provider.PreferencesStorageModule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import es.source.code.model.Food;
import es.source.code.model.FoodCollection;
import es.source.code.model.User;
import es.source.code.utils.CommonUtil;
import es.source.code.utils.Const;

import java.lang.reflect.Type;
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

//    private List<Food> foodList;
//    private FoodCollection foodCollection;

    PreferencesStorageModule foodModule;

    @Override
    public void onCreate() {
        super.onCreate();
        setLoginStatus(Const.SharedPreferenceValue.LOGIN_FAILED); // 默认不让登陆
        app = this;
    }

    public static App getInstance() {
        return app;
    }

    public PreferencesStorageModule getFoodModule() {
        if (foodModule == null) {
            foodModule = new PreferencesStorageModule(this, Const.Module.FOOD_MODULE);
        }
        return foodModule;
    }

    public List<Food> getFoodList() {
        String foodListString = getFoodModule().getString(Const.Module.FOOD_LIST, "");
        Type type = new TypeToken<ArrayList<Food>>() {
        }.getType();
        return new Gson().fromJson(foodListString, type);
    }

    public void setFoodList(List<Food> foodList) {
        String foodListString = new Gson().toJson(foodList);
        getFoodModule().put(Const.Module.FOOD_LIST, foodListString);
    }

    /**
     * @description: 操作定义的FoodList
     * @author: Daniel
     */
    public void operateFoodList(Food food, boolean isAdd) {
        List<Food> foodList = getFoodList();
        boolean add = true;
        if (foodList == null) {
            foodList = new ArrayList<>();
        }
        for (Food orderFood : foodList) {
            if (orderFood.getFoodName().equals(food.getFoodName())) {
                if (isAdd) {
                    // 避免重复添加
                    add = false;
                } else {
                    foodList.remove(orderFood);
                }
                break;
            }
        }
        if (isAdd && add) {
            foodList.add(food);
        }
        setFoodList(foodList);
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

    /**
     * author:      Daniel
     * description: 获取食物列表
     */
    public FoodCollection getFoodCollection() {
        Gson gson = new Gson();
        String foodJsonString = CommonUtil.getJsonFromFile("food.json", getInstance());
        return gson.fromJson(foodJsonString, FoodCollection.class);
    }
}
