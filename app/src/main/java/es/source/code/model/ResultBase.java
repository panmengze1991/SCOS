package es.source.code.model;

/**
 * Author        Daniel
 * Class:        Result
 * Date:         2017/11/3 19:35
 * Description:  请求结果
 */
public class ResultBase {

    // 结果码
    private int RESULTCODE;

    // 携带信息
    private String msg;

    // 对象jsonString
    private String jsonString;

    public ResultBase(int RESULTCODE, String msg, String jsonString) {
        this.RESULTCODE = RESULTCODE;
        this.msg = msg;
        this.jsonString = jsonString;
    }

    public int getRESULTCODE() {
        return RESULTCODE;
    }

    public void setRESULTCODE(int RESULTCODE) {
        this.RESULTCODE = RESULTCODE;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}
