package com.huang.bean;

/**
 * 聊天室
 * Created by Administrator on 2017/11/22.
 */

public class HostedRoomBean {
    private String roomName;// 聊天室名称

    public HostedRoomBean() {
    }

    public HostedRoomBean(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
