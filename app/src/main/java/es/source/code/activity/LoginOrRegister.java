package es.source.code.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.source.code.R;
import es.source.code.utils.Const;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Daniel
 * @ClassName: LoginOrRegister.java
 * @Description: 登录注册页面
 * @date 2017/9/25 10:47
 */
public class LoginOrRegister extends BaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_back)
    Button btnBack;

    private static final Pattern pattern = Pattern.compile(Const.VALID_ACCOUNT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        ButterKnife.bind(this);

        initLayout();
    }

    private void initLayout() {
        etName.addTextChangedListener(getTextWatcher(etName));
        etPassword.addTextChangedListener(getTextWatcher(etPassword));
        btnLogin.setEnabled(false);
    }

//    /**
//     * @description: 显示加载对话框
//     * @author: Daniel
//     */
//    private void showProgress() {
//        progressDialog = new ProgressDialog(mContext);
//        progressDialog.setTitle(getResources().getString(R.string.app_name));
//        progressDialog.setMessage(getResources().getString(R.string.dialog_login));
//        progressDialog.setIcon(R.mipmap.ic_launcher);
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                progressDialog.dismiss();
//            }
//        },2000);
//    }

    /**
     * @description: 登录
     * @author: Daniel
     */
    private void doLogin() {
        showProgress(R.string.dialog_login);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissProgress();
                Intent intent = new Intent(mContext, MainScreen.class);
                intent.putExtra(Const.IntentKey.LOGIN_STATUS, Const.IntentValue.LOGIN_SUCCESS);
                skipActivity(intent);
            }
        }, 2000);
    }

    @OnClick({R.id.btn_login, R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                doLogin();
                break;
            case R.id.btn_back:
                Intent intent = new Intent(mContext, MainScreen.class);
                intent.putExtra(Const.IntentKey.LOGIN_STATUS, Const.IntentValue.LOGIN_RETURN);
                skipActivity(intent);
                break;
        }
    }

    /**
     * @description: 获取字符输入时的监视器
     * @author: Daniel
     */
    private TextWatcher getTextWatcher(final EditText editText) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!pattern.matcher(s).matches()) {
                    btnLogin.setEnabled(false);
                    editText.setError(mContext.getResources().getString(R.string
                            .error_account_edit));
                } else if (etName.getText().toString().trim().length() == 0 ||
                        etPassword.getText().toString().trim().length() == 0) {
                    btnLogin.setEnabled(false);
                } else {
                    editText.setError(null);
                    btnLogin.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        return textWatcher;
    }

}
