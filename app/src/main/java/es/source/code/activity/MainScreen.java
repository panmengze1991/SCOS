package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.source.code.R;
import es.source.code.adapter.GridFunctionAdapter;
import es.source.code.callback.OnFunctionClickListener;
import es.source.code.model.Function;
import es.source.code.model.User;
import es.source.code.utils.Const;

import java.util.ArrayList;
import java.util.List;

public class MainScreen extends BaseActivity {

//    private static final int TYPE_SHOW = 1;
//    private static final int TYPE_HIDE = 2;

    //    @BindView(R.id.btn_order)
//    Button btnOrder;
//    @BindView(R.id.btn_form)
//    Button btnForm;
//    @BindView(R.id.btn_account)
//    Button btnAccount;
//    @BindView(R.id.btn_help)
//    Button btnHelp;
    @BindView(R.id.gv_function)
    GridView gvFunction;

    GridFunctionAdapter gridFunctionAdapter;
    List<Function> functionList;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        ButterKnife.bind(this);
        initList(getIntent());
        initLayout();
    }

    /**
     * @description:
     * @author: Daniel
     */
    private void initList(Intent intent) {
        // 根据intent判断是否显示点餐和订单
        boolean show = Const.IntentValue.FROM.equals(intent.getStringExtra(Const.IntentKey.FROM))
                || Const.IntentValue.LOGIN_SUCCESS.equals(intent.getStringExtra(Const.IntentKey
                .LOGIN_STATUS)) || Const.IntentValue.LOGIN_SUCCESS.equals(intent.getStringExtra
                (Const.IntentKey.LOGIN_STATUS));
        functionList = new ArrayList<>();

        if (show && user!=null) {
            functionList.add(new Function(R.drawable.ic_order_white, R.drawable
                    .guide_btn_order_selector, getResources().getString(R
                    .string.label_order), Const.Resources.FUNCTIONS_TAG.ORDER));
            functionList.add(new Function(R.drawable.ic_form_white, R.drawable
                    .guide_btn_form_selector, getResources().getString(R
                    .string.label_form), Const.Resources.FUNCTIONS_TAG.FORM));
        }

        functionList.add(new Function(R.drawable.ic_account_white, R.drawable
                .guide_btn_account_selector, getResources().getString(R
                .string.label_account), Const.Resources.FUNCTIONS_TAG.ACCOUNT));
        functionList.add(new Function(R.drawable.ic_help_white, R.drawable
                .guide_btn_help_selector, getResources().getString(R
                .string.label_help), Const.Resources.FUNCTIONS_TAG.HELP));
    }

    /**
     * @description: 初始化控件
     * @author: Daniel
     */
    private void initLayout() {
        gridFunctionAdapter = new GridFunctionAdapter(functionList, mContext);
        gridFunctionAdapter.setOnFunctionClickListener(new OnFunctionClickListener() {
            @Override
            public void onClick(Function function, int position) {
                onFunctionCLick(function.getTag());
            }
        });
        gvFunction.setAdapter(gridFunctionAdapter);
    }

    public void onFunctionCLick(Const.Resources.FUNCTIONS_TAG functionTag) {
        // TODO: 2017/10/10 跳转页面
        switch (functionTag) {
            case ORDER:
                showActivity(FoodView.class);
                break;
            case FORM:
                break;
            case ACCOUNT:
//                skipActivity(LoginOrRegister.class);
                Intent intent = new Intent(mContext, LoginOrRegister.class);
                startActivityForResult(intent, Const.ActivityCode.MAIN_SCREEN);
                break;
            case HELP:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (resultCode) {
            case Const.ActivityCode.LOGIN_OR_REGISTER:
                // 登录或注册成功处理。
                user = (User) intent.getExtras().get(Const.ParcelableKey.USER);
                if(Const.IntentValue.REGISTER_SUCCESS.equals(intent.getStringExtra(Const.IntentKey.LOGIN_STATUS))){
                    showToast(getString(R.string.app_name));
                }
                initList(intent);
                gridFunctionAdapter.setData(functionList);
                gridFunctionAdapter.notifyDataSetChanged();
        }
    }
}
