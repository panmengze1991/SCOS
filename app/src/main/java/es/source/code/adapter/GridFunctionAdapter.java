package es.source.code.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.source.code.R;
import es.source.code.callback.OnItemBtnClickListener;
import es.source.code.model.Food;
import es.source.code.model.Function;

import java.util.List;

/**
 * @author Daniel
 * @ClassName: GridFunctionAdapter.java
 * @Description: 导航四格的adapter
 * @date 2017/10/10 11:26
 */

public class GridFunctionAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Function> functionList;
    private Context mContext;

    public GridFunctionAdapter(List<Function> functionList, Context mContext) {
        this.functionList = functionList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<Function> functionList){
        this.functionList = functionList;
    }

    @Override
    public int getCount() {
        return null == functionList ? 0 : functionList.size();
    }

    @Override
    public Function getItem(int position) {
        return null == functionList ? null : functionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final Function function = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item_function, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.btnFunction.setBackgroundResource(function.getBgId());
        viewHolder.btnFunction.setText(function.getName());
        viewHolder.btnFunction.setCompoundDrawables(null, getFunctionDrawable(function),
                null, null);
        viewHolder.btnFunction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemBtnClickListener !=null){
                    onItemBtnClickListener.onClick(function,position);
                }
            }
        });
        return convertView;
    }

    private Drawable getFunctionDrawable(Function function) {
        Drawable top = mContext.getResources().getDrawable(function.getImgId());// 获取res下的图片drawable
        top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());// 一定要设置setBounds();
        return top;
    }

    static class ViewHolder {
        @BindView(R.id.btn_function)
        Button btnFunction;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private OnItemBtnClickListener<Function> onItemBtnClickListener;

    public void setOnItemBtnClickListener(OnItemBtnClickListener<Function> onItemBtnClickListener) {
        this.onItemBtnClickListener = onItemBtnClickListener;
    }
}
