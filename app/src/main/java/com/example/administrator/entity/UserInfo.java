package com.example.administrator.entity;

/**
 * 用户中心数据
 * Created by Administrator on 2017-04-26.
 */

public class UserInfo {
    private int status;
    private String message;
    private UserData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
}
