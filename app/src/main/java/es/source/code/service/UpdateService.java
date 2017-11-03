package es.source.code.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import es.source.code.App;
import es.source.code.R;
import es.source.code.activity.FoodDetailed;
import es.source.code.model.Food;
import es.source.code.model.FoodCollection;
import es.source.code.utils.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * Author        Daniel
 * Class:        UpdateService
 * Date:         2017/10/27 21:14
 * Description:  模拟数据更新并展示
 */
public class UpdateService extends IntentService {

    private static final String TAG = "UpdateService";

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
        getFoodCollection();
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
