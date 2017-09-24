package es.source.code.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import es.source.code.R;
import es.source.code.utils.Const;

/**
 * @ClassName: SCOSEntry.java
 * @Description: 启动页
 * @author Daniel
 * @date 2017/9/25 0:04
 */
public class SCOSEntry extends BaseActivity {

    private static final int MIN_DISTANCE = 100; // 最小滑动距离
    private static final int MIN_VELOCITY = 10; // 最小滑动速度

    GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
        mGestureDetector = new GestureDetector(this, mGestureListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float x = e1.getX() - e2.getX();
            if (x > MIN_DISTANCE && Math.abs(velocityX) > MIN_VELOCITY) {
                showToast(getResources().getString(R.string.toast_welcome));
                Intent intent = new Intent(mContext,MainScreen.class);
                intent.putExtra(Const.INTENT_KEY_FROM,Const.INTENT_VALUE_FROM);
                startActivity(intent);
                finish();
            }
            return false;
        }
    };


}
