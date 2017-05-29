package com.example.administrator.entity;

/**
 * 忘记密码 详细数据
 * Created by Administrator on 2017-04-25.
 */

public class ForgetInfo {
    private String message;
    private int status;
    private ForgetData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ForgetData getData() {
        return data;
    }

    public void setDate(ForgetData data) {
        this.data = data;
    }
}
