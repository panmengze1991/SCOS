package es.source.code.callback;

/**
 * @ClassName: OnItemClickListener.java
 * @Description: item点击事件
 * @author Daniel
 * @date 2017/10/12 13:38
 */
public interface OnItemClickListener<T> {
    void onClick(T t, int position);
}
