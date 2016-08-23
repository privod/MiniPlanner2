package ru.home.miniplanner2.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import ru.home.miniplanner2.model.Domain;

/**
 * Created by privod on 22.08.2016.
 */
public abstract class PlannerBaseAdapter<VH extends PlannerBaseAdapter.ViewHolder, T extends Domain> extends BaseAdapter {

    public abstract VH onCreateViewHolder(ViewGroup parent);
    public abstract void onBindViewHolder(VH holder, int position);

    public static class ViewHolder {
        View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
        }

        public View getItemView() {
            return itemView;
        }
    }

//    private Context mContext;
    private List<T> mData = null;

//    public PlannerBaseAdapter(Context mContext) {
//        this.mContext = mContext;
//    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH holder;

        if (convertView == null){
            holder = onCreateViewHolder(parent);
            convertView = holder.getItemView();
            convertView.setTag(holder);
        } else {
            holder = (VH) convertView.getTag();
        }

        onBindViewHolder(holder, position);

        return convertView;
    }
}
