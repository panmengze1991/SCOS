package es.source.code.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.gson.Gson;
import es.source.code.App;
import es.source.code.R;
import es.source.code.callback.SimpleObserver;
import es.source.code.model.User;
import es.source.code.utils.Const;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Author        Daniel
 * Class:        LoginOrRegister
 * Date:         2017/10/18 19:16
 * Description:  登录注册Activity
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

    private User user;

    private static final Pattern pattern = Pattern.compile(Const.VALID_ACCOUNT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        ButterKnife.bind(this);
        initData();
        initLayout();
    }

    /**
     * author:      Daniel
     * description: 初始化数据
     */
    private void initData() {
        user = App.getInstance().getUser();
    }

    /**
     * author:      Daniel
     * description: 初始化布局
     */
    private void initLayout() {
        if (user != null && user.getUserName() != null) {
            etName.setText(user.getUserName());
            etPassword.requestFocus();
            btnRegister.setVisibility(View.GONE);
        }
        etName.addTextChangedListener(getTextWatcher(etName));
        etPassword.addTextChangedListener(getTextWatcher(etPassword));
        btnLogin.setEnabled(false);
        btnRegister.setEnabled(false);
    }

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
        doBack();
    }


    /**
     * author:Daniel
     * description: 注册登录
     */
    private void doLoginOrRegister(final boolean oldUser) {
        showProgress(R.string.dialog_login);
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new SimpleObserver<Long>() {
            @Override
            public void onEvent(Long t) {
                dismissProgress();
                if (IsValidAccount) {
                    User user = new User(etName.getText().toString().trim(), etPassword.getText().toString().trim(),
                            oldUser);
                    App.getInstance().setUser(user).setLoginStatus(Const.SharedPreferenceValue.LOGIN_SUCCESS);
                    Intent intent = new Intent(mContext, MainScreen.class);
                    intent.putExtra(Const.IntentKey.LOGIN_STATUS, oldUser ? Const.IntentValue.LOGIN_SUCCESS : Const
                            .IntentValue.REGISTER_SUCCESS);
                    setResult(Const.ActivityCode.LOGIN_OR_REGISTER, intent);
                    finish();
                } else {
                    showToast(getString(R.string.toast_account_not_valid));
                }
            }
        });
        startValid();
    }

    /**
     * author:Daniel
     * description: 注册未成功返回处理
     */
    private void doBack() {
        App.getInstance().setLoginStatus(Const.SharedPreferenceValue.LOGIN_FAILED);
        Intent intent = new Intent(mContext, MainScreen.class);
        intent.putExtra(Const.IntentKey.LOGIN_STATUS, Const.IntentValue.LOGIN_RETURN);
        setResult(Const.ActivityCode.LOGIN_OR_REGISTER, intent);
        finish();
    }

    /**
     * author:Daniel
     * description: 使用rxJava完成校验过程
     */
    private void startValid() {
        // test
        Observable.create(new ObservableOnSubscribe<EditText>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<EditText> emitter) throws Exception {
                if (!pattern.matcher(etName.getText().toString().trim()).matches()) {
                    emitter.onNext(etName);
                    IsValidAccount = false;
                } else if (!pattern.matcher(etPassword.getText().toString().trim()).matches()) {
                    emitter.onNext(etPassword);
                    IsValidAccount = false;
                } else {
                    IsValidAccount = true;
                }
                emitter.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new SimpleObserver<EditText>() {
                    @Override
                    public void onEvent(EditText editText) {
                        editText.setError(mContext.getResources().getString(R.string.error_account_edit));
                    }
                });
    }

    /**
     * author:      Daniel
     * description: 获取某个输入框的监听
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
