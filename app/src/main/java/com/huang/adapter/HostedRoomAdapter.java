package com.huang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huang.bean.HostedRoomBean;
import com.huang.testxmpp.R;

import org.jivesoftware.smackx.muc.HostedRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * 群组
 * Created by Administrator on 2017/11/20.
 */

public class HostedRoomAdapter extends BaseAdapter {

    // 设置组视图的数据源
    private List<HostedRoomBean> list = new ArrayList<>();

    // 加载布局
    private LayoutInflater mInflater;

    // 构造器:加载布局,设置数据源
    public HostedRoomAdapter(Context context, List<HostedRoomBean> list) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
    }

    @Override
    public int getCount() {
        return null == list ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final GroupViewHolder holder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.listview_address_book, null);
            holder = new GroupViewHolder();
            holder.lv_address_book = (TextView) convertView.findViewById(R.id.lv_address_book);

            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        try {
            // 设置文本
            holder.lv_address_book.setText(list.get(position).getRoomName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private class GroupViewHolder {
        TextView lv_address_book;
    }
}
