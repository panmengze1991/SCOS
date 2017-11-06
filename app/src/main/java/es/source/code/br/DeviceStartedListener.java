package es.source.code.br;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import es.source.code.service.UpdateService;
import es.source.code.utils.Const;

/**
 * Author        Daniel
 * Class:        DeviceStartedListener
 * Date:         2017/10/27 21:38
 * Description:  开机接收br
 */
public class DeviceStartedListener extends BroadcastReceiver {

    private static final String TAG = "DeviceStartedListener";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");
        if (Const.IntentAction.BOOT.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, UpdateService.class);
            context.startService(serviceIntent);
        } else if (Const.IntentAction.CLOSE_NOTIFICATION.equals(intent.getAction())) {
            NotificationManager notifyManager = (NotificationManager) context.getSystemService(Context
                    .NOTIFICATION_SERVICE);
            notifyManager.cancel(intent.getIntExtra(Const.IntentKey.NOTIFICATION_ID, 0));
        }
    }
}
