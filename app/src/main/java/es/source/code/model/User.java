package es.source.code.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @ClassName: User.java
 * @Description: 用户属性
 * @author Daniel
 * @date 2017/10/11 11:43
 */
public class User implements Parcelable {

    public User(String userName, String password, boolean oldUser) {
        this.userName = userName;
        this.password = password;
        this.oldUser = oldUser;
    }

    // 用户名
    private String userName;

    // 密码
    private String password;

    // 是否老用户
    private boolean oldUser;

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

    public boolean isOldUser() {
        return oldUser;
    }

    public void setOldUser(boolean oldUser) {
        this.oldUser = oldUser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.password);
        dest.writeByte(this.oldUser ? (byte) 1 : (byte) 0);
    }

    protected User(Parcel in) {
        this.userName = in.readString();
        this.password = in.readString();
        this.oldUser = in.readByte() != 0;
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
