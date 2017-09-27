package es.source.code.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import es.source.code.R;

/**
 * @author Daniel
 * @ClassName: BaseActivity.java
 * @Description: activity的基类，用于抽象一些公共方法和属性
 * @date 2017/9/23 0:39
 */
public class BaseActivity extends AppCompatActivity {

    Context mContext = this;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    protected void skipActivity(Class activity) {
        Intent intent = new Intent(mContext, activity);
        startActivity(intent);
        finish();
    }

    protected void skipActivity(Intent intent) {
        startActivity(intent);
        finish();
    }

    /**
     * @description: 显示加载对话框
     * @author: Daniel
     */
    protected void showProgress(int messageRes) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle(getResources().getString(R.string.app_name));
        progressDialog.setMessage(getResources().getString(messageRes));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void showProgress() {
        showProgress(R.string.dialog_loading);
    }

    /**
     * @description: 取消显示对话框
     * @author: Daniel
     */
    protected void dismissProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
