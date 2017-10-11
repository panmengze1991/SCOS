package es.source.code.model;

import es.source.code.utils.Const;

/**
 * @ClassName: function.java
 * @Description: 首页图标信息
 * @author Daniel
 * @date 2017/10/10 11:30
 */
public class Function {

    public Function(int imgId, int bgId, String name, Const.Resources.FUNCTIONS_TAG tag) {
        this.imgId = imgId;
        this.bgId = bgId;
        this.name = name;
        this.tag = tag;
    }

    // 图标资源ID
    private int imgId;

    // 背景色ID
    private int bgId;

    // 功能名称
    private String name;

    // 标签
    private Const.Resources.FUNCTIONS_TAG tag;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getBgId() {
        return bgId;
    }

    public void setBgId(int bgId) {
        this.bgId = bgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Const.Resources.FUNCTIONS_TAG getTag() {
        return tag;
    }

    public void setTag(Const.Resources.FUNCTIONS_TAG tag) {
        this.tag = tag;
    }
}
