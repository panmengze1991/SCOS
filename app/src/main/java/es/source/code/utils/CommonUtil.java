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
import es.source.code.model.Food;
import es.source.code.model.Param;
import es.source.code.model.ResultJson;
import es.source.code.model.ResultXml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
     * author:      Daniel
     * description: 从网络获取的输入流转为String
     */
    public static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();
            is.close();
            Log.d(TAG, "Json test outputStream.size() = " + outputStream.size());
            byte[] byteArray = outputStream.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    /**
     * author:      Daniel
     * description: 从网络获取的输入流转化为Xml的Document
     */
    public static Document streamToXml(InputStream is) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(is);
    }

    /**
     * author:      Daniel
     * description: 解析Xml
     */
    public static ResultXml getResultFromXml(Document document) {
        ResultXml resultXml = new ResultXml();

        Element result = document.getDocumentElement();

        // 获取该节点下面的所有子节点
        NodeList resultChildNodes = result.getChildNodes();
        Log.d(TAG, "resultChildNodes.getLength() = " + resultChildNodes.getLength());
        //把节点转换成元素节点
        Element resultCodeElement = (Element) resultChildNodes.item(0);
        Element messageElement = (Element) resultChildNodes.item(1);
        resultXml.setRESULTCODE(Integer.valueOf(resultCodeElement.getTextContent()));
        resultXml.setMsg(messageElement.getTextContent());

        // 计时开始
        Date startDate = new Date(System.currentTimeMillis());
        // 开始解析列表
        Element dataStringElement = (Element) resultChildNodes.item(2);
        NodeList foodElemList = dataStringElement.getElementsByTagName(Const.ELEMENT_ID.FOOD);
        List<Food> foodList = new ArrayList<>();
        // 遍历food结点
        for (int i = 0; i < foodElemList.getLength(); i++) {
            Element foodElement = (Element) foodElemList.item(i);
            // 遍历food内容
            NodeList foodParams = foodElement.getChildNodes();
            Food food = new Food();
            for (int j = 0; j < foodParams.getLength(); j++) {
                Element param = (Element) foodParams.item(j);
                switch (param.getTagName()) {
                    case Const.ELEMENT_ID.FOOD_NAME:
                        food.setFoodName(param.getTextContent());
                        break;
                    case Const.ELEMENT_ID.PRICE:
                        food.setPrice(Integer.valueOf(param.getTextContent()));
                        break;
                    case Const.ELEMENT_ID.STORE:
                        food.setStore(Integer.valueOf(param.getTextContent()));
                        break;
                    case Const.ELEMENT_ID.ORDER:
                        food.setOrder(Boolean.valueOf(param.getTextContent()));
                        break;
                    case Const.ELEMENT_ID.IMGID:
                        food.setImgId(Integer.valueOf(param.getTextContent()));
                        break;
                }
            }
            foodList.add(food);
        }
        Date endDate = new Date(System.currentTimeMillis());
        long duration = endDate.getTime() - startDate.getTime();
        Log.d(TAG, "Xml test parse time = " + String.valueOf(duration) + "ms , size = " + String
                .valueOf(foodList.size()));

        resultXml.setDataList(foodList);
        return resultXml;
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
                return CommonUtil.streamToString(urlConnection.getInputStream());
            } else {
                Log.d(TAG, "请求失败");
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
    public static InputStream requestGet(String param, String urlString, String contentType) {
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
//            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Content-Type", contentType);
//            urlConn.setRequestProperty("charset", "utf-8");
            //设置客户端与服务连接类型
            urlConn.addRequestProperty("Connection", "Keep-Alive");
            // 开始连接
            urlConn.connect();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200) {
                // 统计Xml输入流长度
                if( contentType.startsWith("t")) {
                    Log.d(TAG, "Xml test，urlConn.getContentLength() = " + urlConn.getContentLength());
                }
                // 返回输入流以便于分情况处理
                return urlConn.getInputStream();
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
