package es.source.code.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import es.source.code.App;
import es.source.code.R;
import es.source.code.activity.FoodDetailed;
import es.source.code.activity.MainScreen;
import es.source.code.model.*;
import es.source.code.utils.CommonUtil;
import es.source.code.utils.Const;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author        Daniel
 * Class:        UpdateService
 * Date:         2017/10/27 21:14
 * Description:  模拟数据更新并展示
 */
public class UpdateService extends IntentService {

    private static final String TAG = "UpdateService";

    // 请求flag
    private static final int FLAG_REQUEST = 100;
    // 删除通知flag
    private static final int FLAG_CLEAN = 101;

    private static final int NOTIFICATION_ID = 5445;

    private static final int TYPE_JSON = 1;
    private static final int TYPE_XML = 2;
    private static int Type;

    private Context mContext;

    public UpdateService() {
        super("UpdateService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UpdateService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent");
//        getFoodCollection();
        mContext = this;
        // 这里修改类型
//        Type = TYPE_JSON;
        Type = TYPE_XML;
        getServerUpdate();
    }

    /**
     * author:      Daniel
     * description: 获取服务端数据
     */
    private void getServerUpdate() {
        Observable.create(new ObservableOnSubscribe<List<Food>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<Food>> e) throws Exception {
                if (Type == TYPE_JSON) {
                    // json
                    InputStream resultStream = CommonUtil.requestGet(null, Const.URL.FOOD, "application/json");
                    if (resultStream == null) {
                        // 取消业务
                        e.onComplete();
                    }
                    String resultString = CommonUtil.streamToString(resultStream);
                    Log.d(TAG, "request = " + resultString);
                    ResultJson resultJson = new Gson().fromJson(resultString, ResultJson.class);
                    String foodString = resultJson.getDataString();
                    Type type = new TypeToken<ArrayList<Food>>() {
                    }.getType();

                    // 解析列表统计时间
                    Date startDate = new Date(System.currentTimeMillis());
                    List<Food> foodList = new Gson().fromJson(foodString, type);
                    Date endDate = new Date(System.currentTimeMillis());
                    long duration = endDate.getTime() - startDate.getTime();
                    Log.d(TAG, "Json test parse time = " + String.valueOf(duration) + "ms , size = " + String
                            .valueOf(foodList.size()));
                    e.onNext(foodList);
                } else if (Type == TYPE_XML) {
                    // xml
                    InputStream resultStream = CommonUtil.requestGet(null, Const.URL.FOOD, "text/xml");
                    if (resultStream == null) {
                        // 取消业务
                        e.onComplete();
                    }
                    ResultXml resultXml = CommonUtil.getResultFromXml(CommonUtil.streamToXml(resultStream));
                    e.onNext(resultXml.getDataList());
                } else {
                    e.onComplete();
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Food>>() {
                    @Override
                    public void accept(List<Food> foodList) throws Exception {
                        // 构造打开首页的PendingIntent
                        Intent intent = new Intent(mContext, MainScreen.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent
                                .FLAG_CANCEL_CURRENT);
                        // 发送状态栏通知
                        sendServerNotification(foodList, pendingIntent);
                        // 播放提示音
                        playNotification();
                    }
                });
    }

    /**
     * author:      Daniel
     * description: 发服务器通知
     */
    private void sendServerNotification(List<Food> foodList, PendingIntent intent) {
        String content = getString(R.string.notify_new_food_title) + getString(R.string
                .notify_new_food_content_server, foodList.size());

        Intent serviceIntent = new Intent(Const.IntentAction.CLOSE_NOTIFICATION);
        serviceIntent.putExtra(Const.IntentKey.NOTIFICATION_ID, NOTIFICATION_ID);
        PendingIntent closeIntent = PendingIntent.getBroadcast(mContext, FLAG_CLEAN, serviceIntent, PendingIntent
                .FLAG_UPDATE_CURRENT);

        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // 设置图标和食物图片、标题、内容
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(content)
                .addAction(R.drawable.ic_close, getString(R.string.text_back), closeIntent)
                //设置intent、点击自动消除
                .setContentIntent(intent)
                .setAutoCancel(true);
        //通过builder.build()方法生成Notification对象，设置提示音，并发送通知,id=1
        Notification notification = builder.build();
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notifyManager.notify(NOTIFICATION_ID, builder.build());
    }

    /**
     * author:      Daniel
     * description:  调用MediaPlayer播放消息通知
     */
    private void playNotification() {
        Uri ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mediaPlayer = MediaPlayer.create(mContext, ringtone);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    /**
     * author:      Daniel
     * description: 模拟获取更新数据
     */
    private void getFoodCollection() {
        FoodCollection foodCollection = App.getInstance().getFoodCollection();
        List<Food> hotFoodList = foodCollection.getHotFoodList();

        int position = (int) (Math.random() * (hotFoodList.size() - 1));
        List<Food> newFoodList = new ArrayList<>();
        newFoodList.add(hotFoodList.get(position));
        Intent intent = new Intent(this, FoodDetailed.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Const.BundleKey.FOOD_LIST, (ArrayList<? extends Parcelable>) newFoodList);
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        sendNotification(hotFoodList.get(position), pendingIntent);
    }

    /**
     * author:      Daniel
     * description: 发通知
     */
    private void sendNotification(Food food, PendingIntent intent) {
        String price = String.valueOf(food.getPrice());
        String content = getString(R.string.notify_new_food_content, food.getFoodName(), price);
        Bitmap foodBitMap = BitmapFactory.decodeResource(getResources(), food.getImgId(), new BitmapFactory.Options());

        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // 设置图标和食物图片、标题、内容
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(foodBitMap)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(content)

                //设置intent、点击自动消除
                .setContentIntent(intent)
                .setAutoCancel(true);
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        notifyManager.notify(1, builder.build());
    }
}
