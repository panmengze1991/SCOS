package es.source.code.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

    protected void showToast(String toast){
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    protected void skipActivity(Class activity){
        Intent intent = new Intent(mContext,activity);
        startActivity(intent);
        finish();
    }

    protected void skipActivity(Intent intent){
        startActivity(intent);
        finish();
    }
}
