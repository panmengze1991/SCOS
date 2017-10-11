package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.source.code.R;
import es.source.code.model.User;
import es.source.code.utils.Const;

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
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_back)
    Button btnBack;

    private static boolean IsValidAccount = false;

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
        btnRegister.setEnabled(false);
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

    @OnClick({R.id.btn_login, R.id.btn_back, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                doLoginOrRegister(true);
                break;
            case R.id.btn_register:
                doLoginOrRegister(false);
                break;
            case R.id.btn_back:
                doBack();
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        doBack();
    }


    /**
     * @description: 登录
     * @author: Daniel
     */
    private void doLoginOrRegister(final boolean oldUser) {
        showProgress(R.string.dialog_login);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissProgress();
                if (IsValidAccount) {
                    User user = new User(etName.getText().toString().trim(), etPassword.getText()
                            .toString().trim(), oldUser);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Const.ParcelableKey.USER,user);
                    Intent intent = new Intent(mContext, MainScreen.class);
                    intent.putExtras(bundle);
                    intent.putExtra(Const.IntentKey.LOGIN_STATUS, oldUser ? Const.IntentValue
                            .LOGIN_SUCCESS : Const.IntentValue.REGISTER_SUCCESS);
                    setResult(Const.ActivityCode.LOGIN_OR_REGISTER, intent);
                    finish();
                } else {
                    showToast(getString(R.string.toast_account_not_valid));
                }
            }
        }, 2000);
        validThread.run();
    }

    /**
     * @description: 注册未成功返回处理
     * @author: Daniel
     */
    private void doBack() {
        Intent intent = new Intent(mContext, MainScreen.class);
        intent.putExtra(Const.IntentKey.LOGIN_STATUS, Const.IntentValue.LOGIN_RETURN);
        setResult(Const.ActivityCode.LOGIN_OR_REGISTER, intent);
        finish();
    }

    // 校验线程
    Thread validThread = new Thread(new Runnable() {
        @Override
        public void run() {
            if (!pattern.matcher(etName.getText().toString().trim()).matches()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        etName.setError(mContext.getResources().getString(R.string
                                .error_account_edit));
                    }
                });
                IsValidAccount = false;
            } else if (!pattern.matcher(etPassword.getText().toString().trim()).matches()) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        etPassword.setError(mContext.getResources().getString(R.string
                                .error_account_edit));
                    }
                });
                IsValidAccount = false;
            } else {
                IsValidAccount = true;
            }
        }
    });

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
                if (etName.getText().toString().trim().length() == 0) {
                    etName.setError(getString(R.string.error_account_empty));
                    btnLogin.setEnabled(false);
                    btnRegister.setEnabled(false);
                } else if (etPassword.getText().toString().trim().length() == 0) {
                    etPassword.setError(getString(R.string.error_account_empty));
                    btnLogin.setEnabled(false);
                    btnRegister.setEnabled(false);
                } else {
                    editText.setError(null);
                    btnLogin.setEnabled(true);
                    btnRegister.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        return textWatcher;
    }

}
