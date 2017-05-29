package com.example.administrator.entity;

import java.util.ArrayList;

/**
 * 评论的数据
 * Created by Administrator on 2017-04-27.
 */

public class CommitInfo {
    private String message;
    private int status;
    ArrayList<CommitData> data;

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

    public ArrayList<CommitData> getData() {
        return data;
    }

    public void setData(ArrayList<CommitData> data) {
        this.data = data;
    }
}
