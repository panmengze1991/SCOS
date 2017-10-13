package es.source.code.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import es.source.code.callback.OnItemBtnClickListener;
import es.source.code.callback.OnItemClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Daniel
 * @ClassName: BaseRecyclerAdapter.java
 * @Description: RecyclerAdapter基类
 * @date 2017/10/11 20:47
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.BaseViewHolder> {

    protected List<T> dataList;
    protected Context mContext;
    protected int layoutId;

    public BaseRecyclerAdapter(List<T> dataList, Context mContext, int layoutId) {
        this.dataList = dataList;
        this.mContext = mContext;
        this.layoutId = layoutId;
    }

    public void setData(List<T> dataList) {
        this.dataList = dataList;
    }

    public void updateData(List<T> dataList) {
        setData(dataList);
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        // 实例化ViewHolder
        BaseViewHolder viewHolder = new BaseViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerAdapter.BaseViewHolder holder, int position){
        bindData(holder,position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    /**
     * @description: 绑定数据
     * @author: Daniel
     */
    protected abstract void bindData(BaseViewHolder holder, int position);

    /**
     * @description: ViewHolder基类
     * @author: Daniel
     */
    public class BaseViewHolder extends  RecyclerView.ViewHolder{

        private Map<Integer, View> mViewMap;

        public BaseViewHolder(View itemView) {
            super(itemView);
            mViewMap = new HashMap<>();
        }

        /**
         * 获取设置的view
         * @param id
         * @return
         */
        public View getView(int id) {
            View view = mViewMap.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                mViewMap.put(id, view);
            }
            return view;
        }
    }

    OnItemClickListener<T> onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    OnItemBtnClickListener<T> onItemBtnClickListener;

    public void setOnItemBtnClickListener(OnItemBtnClickListener<T> onItemBtnClickListener) {
        this.onItemBtnClickListener = onItemBtnClickListener;
    }
}
