package es.source.code.model;

/**
 * Author        Daniel
 * Class:        ResultBase
 * Date:         2017/11/5 3:01
 * Description:  结果基类
 */
public class ResultBase {
    // 结果码
    private int RESULTCODE;

    // 携带信息
    private String msg;

    public ResultBase(){

    }

    public ResultBase(int RESULTCODE, String msg) {
        this.RESULTCODE = RESULTCODE;
        this.msg = msg;
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
}
