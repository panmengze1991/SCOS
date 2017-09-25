package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    @OnClick({R.id.btn_login, R.id.btn_back})
    public void onClick(View view) {
        Intent intent = new Intent(mContext, MainScreen.class);
        switch (view.getId()) {
            case R.id.btn_login:
                intent.putExtra(Const.IntentKey.LOGIN_STATUS, Const.IntentValue.LOGIN_SUCCESS);
                skipActivity(intent);
                break;
            case R.id.btn_back:
                intent.putExtra(Const.IntentKey.LOGIN_STATUS, Const.IntentValue.LOGIN_RETURN);
                skipActivity(intent);
                break;
        }
    }

    private TextWatcher getTextWatcher(final EditText editText) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!pattern.matcher(s).matches() || s.length() == 0) {
                    editText.setError(mContext.getResources().getString(R.string
                            .error_account_edit));
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
