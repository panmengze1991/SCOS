package es.source.code.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.source.code.R;
import es.source.code.callback.OnItemBtnClickListener;
import es.source.code.model.Event;
import es.source.code.model.Function;
import es.source.code.utils.Const;
import es.source.code.utils.RxBus;

import java.util.List;

/**
 * Author        Daniel
 * Class:        GridHelpAdapter
 * Date:         2017/10/19 13:16
 * Description:  帮助导航
 */
public class GridHelpAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Function> functionList;
    private Context mContext;

    public GridHelpAdapter(List<Function> functionList, Context mContext) {
        this.functionList = functionList;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public void setData(List<Function> functionList) {
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
            convertView = inflater.inflate(R.layout.grid_item_help, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvHelp.setText(function.getName());
        viewHolder.tvHelp.setCompoundDrawables(null, getFunctionDrawable(function),
                null, null);
        return convertView;
    }

    private Drawable getFunctionDrawable(Function function) {
        Drawable top = mContext.getResources().getDrawable(function.getImgId());// 获取res下的图片drawable
        top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());// 一定要设置setBounds();
        return top;
    }

    static class ViewHolder {
        @BindView(R.id.tv_help)
        TextView tvHelp;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

//    private OnItemBtnClickListener<Function> onItemBtnClickListener;
//
//    public void setOnItemBtnClickListener(OnItemBtnClickListener<Function> onItemBtnClickListener) {
//        this.onItemBtnClickListener = onItemBtnClickListener;
//    }
}
