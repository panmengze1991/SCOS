package es.source.code.model;

import java.util.List;

/**
 * Author        Daniel
 * Class:        Result
 * Date:         2017/11/3 19:35
 * Description:  XML请求结果
 */
public class ResultXml extends ResultBase{

    // Food列表
    private List<Food> dataList;

    public ResultXml() {

    }

    public ResultXml(int RESULTCODE, String msg, List<Food> dataList) {
        super(RESULTCODE,msg);
        this.dataList = dataList;
    }

    public List<Food> getDataList() {
        return dataList;
    }

    public void setDataList(List<Food> dataList) {
        this.dataList = dataList;
    }
}
