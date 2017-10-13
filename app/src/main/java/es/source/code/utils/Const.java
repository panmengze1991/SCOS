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
            ORDER, FORM, ACCOUNT, HELP
        }
//
//        // 功能按钮背景
//        public static final int[] FUNCTION_BACKGROUND = {R.drawable.guide_btn_order_selector, R
//                .drawable.guide_btn_form_selector, R.drawable.guide_btn_account_selector, R
//                .drawable.guide_btn_help_selector};
//
//        // 功能按钮图标
//        public static final int[] FUNCTION_DRAWABLE = {R.drawable.ic_order_white, R.drawable
//                .ic_form_white, R.drawable.ic_account_white, R.drawable.ic_help_white};
    }

    public static class ActivityCode{
        public static final int MAIN_SCREEN = 1;
        public static final int LOGIN_OR_REGISTER = 2;
    }

}
