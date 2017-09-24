package es.source.code.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * @ClassName: BaseActivity.java
 * @Description: activity的基类，用于抽象一些公共方法和属性
 * @author Daniel
 * @date 2017/9/23 0:39
 */
public class BaseActivity extends AppCompatActivity {

    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showToast(String toast){
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }
}
