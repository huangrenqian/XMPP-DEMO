package com.huang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.huang.adapter.AddressBookAdapter;
import com.huang.testxmpp.ChatActivity;
import com.huang.testxmpp.R;
import com.huang.xmpp.XmppConnection;

import org.jivesoftware.smack.roster.RosterEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 通讯录
 * Created by Administrator on 2017/11/15.
 */

public class AddressBookFragment extends Fragment {
    @Bind(R.id.address_book_lv)
    ListView listView;

    private List<RosterEntry> list = new ArrayList<>();
    private AddressBookAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = null;
        try {
            layout = inflater.inflate(R.layout.fragment_address_book, container, false);
            ButterKnife.bind(this, layout);
            init();// 初始化
        } catch (Exception e) {
            e.printStackTrace();
        }
        return layout;
    }

    private void init() {
        adapter = new AddressBookAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String JID = list.get(position).getJid().toString();
                startActivity(new Intent(getActivity(), ChatActivity.class)
                        .putExtra("SingleUserChatJID", JID)
                        .putExtra("ChatType", false));
            }
        });

        loadData();
    }

    private void loadData() {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                list.addAll(XmppConnection.getInstance().getAllEntries());

                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }).start();
    }
}
