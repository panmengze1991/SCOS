package es.source.code.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import es.source.code.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Author        Daniel
 * Class:        CommonUtil
 * Date:         2017/10/25 20:02
 * Description:  通用工具
 */
public class CommonUtil {

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
}
