package com.huang.testxmpp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huang.xmpp.Constant;
import com.huang.xmpp.XmppConnection;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * TestActivity
 * Created by Administrator on 2017/11/10.
 */

public class TestActivity extends BaseActivity {

    private AbstractXMPPConnection connection;
    private Chat topChat;

    private LinearLayout msgList;
    private EditText msg;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);
        msgList = (LinearLayout) findViewById(R.id.messages);
        msg = (EditText) findViewById(R.id.msg);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!msg.getText().toString().equals("") && topChat != null) {
                    try {
                        String string = msg.getText().toString();
                        topChat.send(string);// 发消息
                    } catch (SmackException.NotConnectedException e) {
                        Toast.makeText(TestActivity.this, "发送失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                login("qian", "123456");
            }
        }).start();
    }

    private void setText(final String text) {
        runOnUiThread(new Runnable() {
            public void run() {
                TextView tv = new TextView(TestActivity.this);
                tv.setText(text);
                msgList.addView(tv);
            }
        });
    }

    public AbstractXMPPConnection getCreateConnection() {
        try {
            XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration.builder();
            //设置openfire主机IP
            config.setHostAddress(InetAddress.getByName(Constant.OPENFIRE_IP));
            //设置openfire服务器名称
            config.setXmppDomain(Constant.OPENFIRE_NAME);
            //设置端口号：默认5222
            config.setPort(Constant.OPENFIRE_PORT);
            //禁用SSL连接
            config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled).setCompressionEnabled(false);
            //设置Debug
            config.setDebuggerEnabled(true);
            //设置离线状态
            config.setSendPresence(false);
            //需要经过同意才可以添加好友
            Roster.setDefaultSubscriptionMode(Roster.SubscriptionMode.manual);
            // 将相应机制隐掉
            //SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1");
            //SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5");

            connection = new XMPPTCPConnection(config.build());
            connection.addConnectionListener(new ConnectionListener() {
                @Override
                public void connected(XMPPConnection xmppConnection) {
                    Log.e("ConnectionListener", "connected");
                }

                @Override
                public void authenticated(XMPPConnection xmppConnection, boolean b) {
                    Log.e("ConnectionListener", "authenticated" + b);
                }

                @Override
                public void connectionClosed() {
                    Log.e("ConnectionListener", "connectionClosed");
                }

                @Override
                public void connectionClosedOnError(Exception e) {
                    Log.e("ConnectionListener", "connectionClosedOnError");
                }

                @Override
                public void reconnectionSuccessful() {
                    Log.e("ConnectionListener", "reconnectionSuccessful");
                }

                @Override
                public void reconnectingIn(int i) {
                    Log.e("ConnectionListener", "reconnectingIn" + i);
                }

                @Override
                public void reconnectionFailed(Exception e) {
                    Log.e("ConnectionListener", "reconnectionFailed" + e.getMessage());
                }
            });
        } catch (UnknownHostException | XmppStringprepException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void login(String username, String password) {
        if (connection == null) {
            connection = getCreateConnection();
        }
        initChatManager();// 初始化ChatManager
        try {
            connection.connect();

            if (!connection.isAuthenticated()) {
                connection.login(username, password);
            }

            // 告诉服务器所有客户端当前所处的状态:在线状态
            Presence presence = new Presence(Presence.Type.available);
            connection.sendStanza(presence);
        } catch (SmackException | IOException | XMPPException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initChatManager() {
        ChatManager cm = ChatManager.getInstanceFor(connection);
        cm.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid entityBareJid, Message message, Chat chat) {
                topChat = chat;
                if (null != message.getBody()) {
                    String from = message.getFrom().toString();
                    setText("from " + from + " : " + message.getBody());
                }
            }
        });
        cm.addOutgoingListener(new OutgoingChatMessageListener() {
            @Override
            public void newOutgoingMessage(EntityBareJid entityBareJid, Message message, Chat chat) {
                setText("我： " + message.getBody());
            }
        });
    }
}
