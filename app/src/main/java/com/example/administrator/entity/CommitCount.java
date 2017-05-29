package com.example.administrator.entity;

/**
 * 新闻评论总数
 * Created by Administrator on 2017-04-27.
 */

public class CommitCount {
    private String message;
    private int status;
    private int data;  //评论数

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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
