package com.huang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.huang.adapter.HostedRoomAdapter;
import com.huang.bean.HostedRoomBean;
import com.huang.testxmpp.ChatActivity;
import com.huang.testxmpp.R;
import com.huang.xmpp.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 群组
 * Created by Administrator on 2017/11/15.
 */

public class HostedRoomFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.hosted_room_lv)
    ListView listView;

    private List<HostedRoomBean> list = new ArrayList<>();
    private HostedRoomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = null;
        try {
            layout = inflater.inflate(R.layout.fragment_hosted_room, container, false);
            ButterKnife.bind(this, layout);
            init();// 初始化
        } catch (Exception e) {
            e.printStackTrace();
        }
        return layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    private void init() {
        list.add(new HostedRoomBean(Constant.roomNameList[0]));
        list.add(new HostedRoomBean(Constant.roomNameList[1]));
        adapter = new HostedRoomAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                startActivity(new Intent(getActivity(), ChatActivity.class)
                        .putExtra("MultiUserChatPosition", position)
                        .putExtra("ChatType", true));
            }
        });
    }
}