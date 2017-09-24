package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.source.code.R;
import es.source.code.utils.Const;

public class MainScreen extends BaseActivity {

    private static final int TYPE_SHOW = 1;
    private static final int TYPE_HIDE = 2;

    @BindView(R.id.btn_order)
    Button btnOrder;
    @BindView(R.id.btn_form)
    Button btnForm;
    @BindView(R.id.btn_account)
    Button btnAccount;
    @BindView(R.id.btn_help)
    Button btnHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        ButterKnife.bind(this);
        initLayout();
    }

    private void initLayout() {
        Intent intent = getIntent();
        showButtons(Const.INTENT_VALUE_FROM.equals(intent.getStringExtra(Const.INTENT_KEY_FROM)));
    }

    /**
     * @description: 显示和隐藏按钮
     * @author: Daniel
     */
    private void showButtons(boolean isFromEntry) {
        int visible = isFromEntry ? View.VISIBLE : View.GONE;
        btnOrder.setVisibility(visible);
        btnForm.setVisibility(visible);
    }

    @OnClick({R.id.btn_order, R.id.btn_form, R.id.btn_account, R.id.btn_help})
    public void onClick(View view) {
        // TODO: 2017/9/25 跳转页面
        switch (view.getId()) {
            case R.id.btn_order:
                break;
            case R.id.btn_form:
                break;
            case R.id.btn_account:
                break;
            case R.id.btn_help:
                break;
        }
    }
}
