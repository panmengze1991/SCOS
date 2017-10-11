package es.source.code;

import android.app.Application;
import es.source.code.model.User;

/**
 * @ClassName: App.java
 * @Description: app类
 * @author Daniel
 * @date 2017/10/11 11:41
 */
public class App extends Application {
    private static App app;

    private User userInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static App getInstantce() {
        return app;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }
}
