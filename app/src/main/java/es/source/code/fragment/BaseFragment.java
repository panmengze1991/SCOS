package es.source.code.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @ClassName: BaseFragment.java
 * @Description: fragment基类
 * @author Daniel
 * @date 2017/10/11 14:53
 */
public class BaseFragment extends Fragment {
    protected View contentView;
    protected String TAG = getClass().getSimpleName();
    protected Context mContext;
    protected Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        activity = (Activity) mContext;
    }

    /**
     * show to @param(cls)
     */
    public void showActivity(Intent it) {
        startActivity(it);
    }

    /**
     * show to @param(cls)
     */
    public void showActivity(Class<?> cls, Bundle extras) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtras(extras);
        startActivity(intent);
    }

    /**
     * show to @param(cls)
     */
    public void showActivity(Class<?> cls) {
        Intent intent = new Intent(mContext, cls);
        startActivity(intent);
    }

}
