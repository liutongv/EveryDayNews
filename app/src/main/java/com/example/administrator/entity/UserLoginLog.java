package com.example.administrator.entity;

/**
 * 用户中心登录数据
 * Created by Administrator on 2017-04-26.
 */

public class UserLoginLog {
    private String time;//登录时间
    private String address;//登录地点
    private int device;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }
}
