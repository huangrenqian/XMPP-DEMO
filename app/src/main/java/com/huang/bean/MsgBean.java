package com.huang.bean;

import java.io.Serializable;

/**
 * 保存open fire的消息类
 *
 * @author huang
 */
public class MsgBean implements Serializable {

    private static final long serialVersionUID = 1102L;

    private int msg_id;// 消息ID,自增长
    private int user_id;// 登录的用户ID
    private String from;// 从那里来 (永远都是好友)
    private String to;// 到那里去 (永远都是我)
    private String body;// 消息内容
    private String time;// 收到消息的时间或者发出消息时的时间
    private int read;// 0为末读,1为已读
    private int latest;// 0不是,1为最新

    public MsgBean() {
    }

    public MsgBean(int msg_id, int user_id, String from, String to,
                   String body, String time, int read, int latest) {
        this.msg_id = msg_id;
        this.user_id = user_id;
        this.from = from;
        this.to = to;
        this.body = body;
        this.time = time;
        this.read = read;
        this.latest = latest;
    }

    public int getLatest() {
        return latest;
    }

    public void setLatest(int latest) {
        this.latest = latest;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

}