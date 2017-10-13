package es.source.code.callback;

/**
 * @ClassName: OnItemBtnClickListener.java
 * @Description: 控件单击接口
 * @author Daniel
 * @date 2017/10/10 22:18
 */
public interface OnItemBtnClickListener<T> {
    void onClick(T t,int position);
}
