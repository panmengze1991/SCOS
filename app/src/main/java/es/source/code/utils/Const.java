package es.source.code.utils;

import es.source.code.R;

/**
 * @author Daniel
 * @ClassName: Constants.java
 * @Description: 常量类
 * @date 2017/9/25 0:02
 */
public class Const {

    // IntentKey
    public static class IntentKey {
        public static final String FROM = "from";
        public static final String LOGIN_STATUS = "login_status";
        public static final String FOOD_POSITION = "food_position";
    }

    // IntentValue
    public static class IntentValue {
        public static final String FROM = "FromEntry";
        public static final String LOGIN_SUCCESS = "LoginSuccess";
        public static final String REGISTER_SUCCESS = "RegisterSuccess";
        public static final String LOGIN_RETURN = "Return";
    }

    // ParcelableKey
    public static class ParcelableKey {
        public static final String USER = "user";
        public static final String FOOD_LIST = "food_list";
    }

    // 正则表达式
    public static final String VALID_ACCOUNT = "^[A-Za-z1-9_-]+$";

    // resources
    public static class Resources {
        public enum FUNCTIONS_TAG {

            // 首页功能
            ORDER, FORM, ACCOUNT, HELP,

            // 帮助页功能
            PROTOCOL, ABOUT, PHONE, MESSAGE, EMAIL
        }

    }

    public static class ActivityCode {
        public static final int MAIN_SCREEN = 1;
        public static final int LOGIN_OR_REGISTER = 2;
        public static final int SCOS_HELPER = 3;
    }

    // 事件键值
    public static class EventKey {
        // 刷新主页导航
        public static final String REFRESH_FUNCTONS = "refresh_functions";

        // 点击事件
        public static final String HELP_CLICK = "help_click";
    }

    // sp的key
    public static class SharedPreferenceKey {
        // 资料
        public static final String PROFILE = "profile";
        public static final String USER = "user";
        public static final String LOGIN_STATUS = "login_status";
    }

    // sp的value
    public static class SharedPreferenceValue {
        // 登录状态值
        public static final int LOGIN_SUCCESS = 1;
        public static final int LOGIN_FAILED = 0;
    }
}
