package es.source.code.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @ClassName: SquareLayout.java
 * @Description: 方形item
 * @author Daniel
 * @date 2017/10/10 21:46
 */
public class SquareLayout extends LinearLayout{
    public SquareLayout(Context context) {
        super(context);
    }

    public SquareLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
