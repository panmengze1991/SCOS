package es.source.code.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import es.source.code.R;

import java.io.*;
import java.util.List;

/**
 * Author        Daniel
 * Class:        CommonUtil
 * Date:         2017/10/25 20:02
 * Description:  通用工具
 */
public class CommonUtil {

    private static final String TAG = "CommonUtil";

    /**
     * author:      Daniel
     * description: 从assets文件夹中读取数据
     */
    public static String getJsonFromFile(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * author:      Daniel
     * description: 检测进程是否在运行
     */
    public static boolean isProcessRunning(Context mContext, String processName) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);

        List<RunningAppProcessInfo> processInfoList = activityManager.getRunningAppProcesses();
        for (RunningAppProcessInfo info : processInfoList) {
            if (processName.equals(info.processName)) {
                isRunning = true;
            }
        }
        return isRunning;
    }

    /**
     * author:      Daniel
     * description: 多彩颜色字符串
     */
    public static SpannableStringBuilder getColorString(String text, int start, int end, int color, Context mContext) {
        SpannableStringBuilder priceBuilder = new SpannableStringBuilder(text);
        ForegroundColorSpan priceSpan = new ForegroundColorSpan(mContext.getResources().getColor(color));
        priceBuilder.setSpan(priceSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return priceBuilder;
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return
     */
    public static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }
}
