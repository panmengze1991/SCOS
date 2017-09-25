package es.source.code.utils;

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
    }

    // IntentValue
    public static class IntentValue {
        public static final String FROM = "FromEntry";
        public static final String LOGIN_SUCCESS = "LoginSuccess";
        public static final String LOGIN_RETURN = "Return";
    }

    // 正则表达式
    public static final String VALID_ACCOUNT = "^[A-Za-z1-9_-]+$";

}
