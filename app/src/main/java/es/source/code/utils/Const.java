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

    // IntentAction
    public static class IntentAction {
        // 开机action
        public static final String BOOT = "android.intent.action.BOOT_COMPLETED";
    }

    // BundleKey
    public static class BundleKey {
        public static final String USER = "user";
        public static final String FOOD_LIST = "food_list";
        public static final String FOOD_COLLECTION = "food_collection";
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

        // 传入handler的msg.what，开关自动刷新
        public static final int MSG_FOOD_GET_START = 1;
        public static final int MSG_FOOD_GET_STOP = 0;
        public static final int MSG_FOOD_GET_SUCCESS = 10;

        // 邮件发送成功
        public static final int MAIL_SEND = 10;
    }

    // sp的key
    public static class SharedPreferenceKey {
        // 资料
        public static final String PROFILE = "profile";
        public static final String USER = "user";
        public static final String LOGIN_STATUS = "login_status";
    }

    public static class Module{
        // module名
        public static final String FOOD_MODULE = "food_module";
        // 已点餐列表
        public static final String FOOD_LIST = "food_list";
    }

    // sp的value
    public static class SharedPreferenceValue {
        // 登录状态值
        public static final int LOGIN_SUCCESS = 1;
        public static final int LOGIN_FAILED = 0;
    }

    public static final String BASE_URL = "http://192.168.209.1:8080/SCOS/";
}
