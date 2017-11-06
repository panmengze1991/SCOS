package es.source.code.model;

/**
 * Author        Daniel
 * Class:        Result
 * Date:         2017/11/3 19:35
 * Description:  Json请求结果
 */
public class ResultJson extends ResultBase {

    // 对象jsonString
    private String dataString;

    public ResultJson() {

    }

    public ResultJson(int RESULTCODE, String msg, String dataString) {
        super(RESULTCODE,msg);
        this.dataString = dataString;
    }

    public String getDataString() {
        return dataString;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }
}
