package es.source.code.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.source.code.R;
import es.source.code.adapter.BaseRecyclerAdapter;
import es.source.code.adapter.FoodRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @ClassName: BaseRecyclerFragment.java
 * @Description: 基础的RecyclerFragment
 * @date 2017/10/11 20:34
 */
public abstract class BaseRecyclerFragment<T> extends BaseFragment {

    protected Unbinder unbinder;
    protected LayoutInflater inflater;
    protected ViewGroup container;

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    List<T> dataList = new ArrayList<>();

    protected BaseRecyclerAdapter<T> listAdapter;

    protected RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        this.container = container;
        this.inflater = inflater;

        // 传入布局
        initLayout();
        unbinder = ButterKnife.bind(this, contentView);

        // 处理数据
        initData();

        initRecyclerView();
        return contentView;
    }

    /**
     * @description: 填充数据
     * @author: Daniel
     */
    protected abstract void initData();

    /**
     * @description: 初始化列表
     * @author: Daniel
     */
    protected void initRecyclerView() {
        layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        // 设置布局管理器
        rvList.setLayoutManager(layoutManager);
        // 设置adapter
        rvList.setAdapter(listAdapter);
    }

    protected abstract void initLayout();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
