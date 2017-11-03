package es.source.code.model;

/**
 * Author        Daniel
 * Class:        LoginParam
 * Date:         2017/11/3 16:06
 * Description:  登录参数
 */
public class LoginParam extends Param {
    private String userName;
    private String password;

    public LoginParam(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
