package es.source.code.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import es.source.code.R;
import es.source.code.adapter.GridHelpAdapter;
import es.source.code.callback.SimpleObserver;
import es.source.code.model.Event;
import es.source.code.model.Function;
import es.source.code.utils.Const;
import es.source.code.utils.RxBus;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.ArrayList;
import java.util.List;

import static es.source.code.utils.Const.Resources.FUNCTIONS_TAG;
import static es.source.code.utils.Const.Resources.FUNCTIONS_TAG.PHONE;

/**
 * Author        Daniel
 * Class:        SCOSHelper
 * Date:         2017/10/19 12:57
 * Description:  帮助activity
 */
public class SCOSHelper extends BaseActivity {

    private static final String TAG = "SCOSHelper";
    List<Function> functionList;
    @BindView(R.id.gv_function)
    GridView gvFunction;

    GridHelpAdapter gridHelpAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoshelper);
        ButterKnife.bind(this);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        functionList = new ArrayList<>();
        functionList.add(new Function(R.drawable.ic_help_protocol, 0, getString(R.string.text_protocol), Const
                .Resources.FUNCTIONS_TAG.PROTOCOL));
        functionList.add(new Function(R.drawable.ic_help_about, 0, getString(R.string.text_about), Const.Resources
                .FUNCTIONS_TAG.ABOUT));
        functionList.add(new Function(R.drawable.ic_help_phone, 0, getString(R.string.text_phone), PHONE));
        functionList.add(new Function(R.drawable.ic_help_message, 0, getString(R.string.text_message), Const
                .Resources.FUNCTIONS_TAG.MESSAGE));
        functionList.add(new Function(R.drawable.ic_help_email, 0, getString(R.string.text_email), Const.Resources
                .FUNCTIONS_TAG.EMAIL));
    }

    private void initView() {
        gridHelpAdapter = new GridHelpAdapter(functionList, mContext);
        gvFunction.setAdapter(gridHelpAdapter);
        gvFunction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Function function = gridHelpAdapter.getItem(position);
                switch (function.getTag()) {
                    case PROTOCOL:

                        break;
                    case ABOUT:

                        break;
                    case PHONE:
                    case MESSAGE:
                    case EMAIL:
                        showAlert(function.getTag());
                        break;
                }
            }
        });
    }

    /**
     * author:      Daniel
     * description: 注册EventBus
     */
    private void initListener() {
        EventBus.getDefault().register(mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(mContext);
    }

    /**
     * author:      Daniel
     * description: 显示警报
     */
    private void showAlert(final FUNCTIONS_TAG tag) {
        String message = "";
        switch (tag) {
            case PHONE:
                message = getString(R.string.alert_phone);
                break;
            case MESSAGE:
                message = getString(R.string.alert_message);
                break;
            case EMAIL:
                message = getString(R.string.alert_email);
                break;
        }
        new AlertDialog.Builder(mContext)
                .setMessage(message)
                .setTitle(getString(R.string.app_name))
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(R.string.btn_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handleAction(tag);
                    }
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .create()
                .show();
    }

    /**
     * author:      Daniel
     * description: 处理动作事件
     */
    private void handleAction(FUNCTIONS_TAG tag) {
        Intent intent = new Intent();
        String number = getString(R.string.phone_number);
        switch (tag) {
            case PHONE:
                Uri uri;
                uri = Uri.parse("tel:" + number);
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(uri);
                startActivity(intent);
                break;
            case MESSAGE:
                String sms = getString(R.string.sms_help);
                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(number, null, sms, null, null);
                showToast(R.string.sms_help_success);
                break;
            case EMAIL:
//                mailThread.start();
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                        try {
                            sendMail();
                            emitter.onNext(getString(R.string.email_help_success));
                        } catch (EmailException e) {
                            e.printStackTrace();
                            emitter.onNext(getString(R.string.email_help_fail));
                        }
                    }
                })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(new SimpleObserver<String>() {
                            @Override
                            public void onEvent(String toast) {
                                showToast(toast);
                            }
                        });
                break;
        }
    }

    // 发邮件线程
    Thread mailThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                sendMail();
                EventBus.getDefault().post(new Event<>(Const.EventKey.HELP_CLICK,true));
            } catch (EmailException e) {
                e.printStackTrace();
                EventBus.getDefault().post(new Event<>(Const.EventKey.HELP_CLICK,false));
            }
        }
    });

    //主线程执行订阅
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMailSend(Event<Boolean> event){
        if(event.getEventData()){
            showToast(R.string.email_help_success);
        } else {
            showToast(R.string.email_help_fail);
        }

    }

    /**
     * author:      Daniel
     * description: 发送邮件
     */
    private void sendMail() throws EmailException{
        Log.d(TAG,"send start");
        HtmlEmail email = new HtmlEmail();
        // SMTP发送服务器的名字
        email.setHostName("smtp.qq.com");
        // SMTP发送服务器端口
        email.setSmtpPort(587);
        // 发件人在邮件服务器上的注册名称和密码
        email.setAuthentication("xxx", "xxx");
        // 字符编码集的设置
        email.setCharset("gbk");
        // 收件人的邮箱
        email.addTo("xxx");
        // 发送人的邮箱
        email.setFrom("xxx", "xxx");
        // 邮件标题
        email.setSubject("SCOS求助邮件");
        // 要发送的信息
        email.setMsg("我遇到了一些问题，请帮我解决一下。");
        // 发送
        email.send();
        Log.d(TAG,"send finish");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            showToast(R.string.email_help_success);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
