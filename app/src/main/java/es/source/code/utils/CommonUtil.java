package es.source.code.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import com.google.gson.Gson;
import es.source.code.R;
import es.source.code.model.Param;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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

    /**
     * author:      Daniel
     * description: 返回result的JsonString
     */
    public static <T extends Param> String requestPost(T param, String urlString) {
        try {
            String postUrl = Const.URL.BASE + urlString;

            // 构造参数json字符串
            String paramString = new Gson().toJson(param);
            // 请求的参数转换为byte数组
            byte[] postData = paramString.getBytes("utf8");

            // 新建一个URL对象
            URL url = new URL(postUrl);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            urlConnection.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            urlConnection.setReadTimeout(5 * 1000);
            // Post请求必须设置允许输出 默认false
            urlConnection.setDoOutput(true);
            //设置请求允许输入 默认是true
            urlConnection.setDoInput(true);
            // Post请求不能使用缓存
            urlConnection.setUseCaches(false);
            // 设置为Post请求
            urlConnection.setRequestMethod("POST");
            //设置本次连接是否自动处理重定向
            urlConnection.setInstanceFollowRedirects(true);
            // 配置请求Content-Type
            urlConnection.setRequestProperty("Content-Type", "application/json");
            // 开始连接
            urlConnection.connect();
            // 发送请求参数
            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
            dos.write(postData);
            dos.flush();
            dos.close();
            // 判断请求是否成功
            if (urlConnection.getResponseCode() == 200) {
                // 获取返回的数据
                String result = CommonUtil.streamToString(urlConnection.getInputStream());
                Log.d(TAG, "Post方式请求成功，result--->" + result);
                return result;
            } else {
                Log.d(TAG, "Post方式请求失败");
            }
            // 关闭连接
            urlConnection.disconnect();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }

    /**
     * author:      Daniel
     * description: 返回result的JsonString
     */
    public static String requestGet(String param, String urlString) {
        try {
            String postUrl = Const.URL.BASE + urlString;

            // 新建一个URL对象
            URL url = new URL(postUrl);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接主机超时时间
            urlConn.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(5 * 1000);
            // 设置是否使用缓存  默认是true
            urlConn.setUseCaches(true);
            // 设置为Post请求
            urlConn.setRequestMethod("GET");
            //urlConn设置请求头信息
            //设置请求中的媒体类型信息。
            urlConn.setRequestProperty("Content-Type", "application/json");
            //设置客户端与服务连接类型
            urlConn.addRequestProperty("Connection", "Keep-Alive");
            // 开始连接
            urlConn.connect();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200) {
                // 获取返回的数据
                String result = CommonUtil.streamToString(urlConn.getInputStream());
                Log.d(TAG, "Get方式请求成功，result--->" + result);
                return result;
            } else {
                Log.d(TAG, "Get方式请求失败");
            }
            // 关闭连接
            urlConn.disconnect();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return null;
    }
}
