package es.source.code.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import es.source.code.R;

/**
 * @ClassName: ColdFoodFragment.java
 * @Description: 冷菜Fragment
 * @author Daniel
 * @date 2017/10/11 14:48
 */
public class ColdFoodFragment extends BaseFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.frag_cold_food, container, false);
        ButterKnife.bind(this, contentView);
        return contentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
