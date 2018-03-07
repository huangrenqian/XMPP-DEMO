package com.huang.testxmpp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.huang.fragment.AddressBookFragment;
import com.huang.fragment.HostedRoomFragment;
import com.huang.fragment.MeFragment;
import com.huang.fragment.SessionFragment;
import com.huang.utils.CommonUtils;
import com.huang.xmpp.XmppConnection;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {
    @Bind(R.id.home_group)
    RadioGroup home_group;

    private FragmentManager manager;
    private Fragment sessionFragment, addressBookFragment, discoverFragment, meFragment, currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        setToolBarTitle("XMPP");

        sessionFragment = currentFragment = new SessionFragment();
        addressBookFragment = new AddressBookFragment();
        discoverFragment = new HostedRoomFragment();
        meFragment = new MeFragment();

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.homeFragmentContainer, sessionFragment);
        transaction.commit();

        home_group.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    private RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            try {
                switch (checkedId) {
                    case R.id.home_radio_data:
                        CommonUtils.switchFragment(currentFragment, sessionFragment, manager, R.id.homeFragmentContainer);
                        currentFragment = sessionFragment;//当前加载的碎片
                        break;
                    case R.id.home_radio_project:
                        CommonUtils.switchFragment(currentFragment, addressBookFragment, manager, R.id.homeFragmentContainer);
                        currentFragment = addressBookFragment;//当前加载的碎片
                        break;
                    case R.id.home_radio_outpatient:
                        CommonUtils.switchFragment(currentFragment, discoverFragment, manager, R.id.homeFragmentContainer);
                        currentFragment = discoverFragment;//当前加载的碎片
                        break;
                    case R.id.home_radio_recommend:
                        CommonUtils.switchFragment(currentFragment, meFragment, manager, R.id.homeFragmentContainer);
                        currentFragment = meFragment;//当前加载的碎片
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void finish() {
        try {
            // 更改用户状态为离线
            XmppConnection.getInstance().setPresence(5);
            // 1.退出程序应该移除监听
//            XmppConnection.getInstance().getConnection().getChatManager().removeChatListener(chatManagerListener);
            // 2.退出程序应该移除连接监听
//
            // 3.退出程序应该关闭连接
            XmppConnection.getInstance().closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.finish();
    }
}
