package com.huang.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huang.testxmpp.R;
import com.huang.utils.Watch.Watcher;
import com.huang.xmpp.XMChatMessageListener;

import org.jivesoftware.smack.packet.Message;

import butterknife.ButterKnife;

/**
 * 会话
 * Created by Administrator on 2017/11/15.
 */

public class SessionFragment extends Fragment implements Watcher {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = null;
        try {
            layout = inflater.inflate(R.layout.fragment_session, container, false);
            ButterKnife.bind(this, layout);
            init();// 初始化
        } catch (Exception e) {
            e.printStackTrace();
        }
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        XMChatMessageListener.removeWatcher(this);// 删除XMPP消息观察者
    }

    private void init() {
        XMChatMessageListener.addWatcher(this);// 增加XMPP消息观察者
    }

    @Override
    public void update(Message message) {// 通过会话DB，刷新会话列表
        Log.e("SessionFragmentMessage", message.toString());
    }
}
