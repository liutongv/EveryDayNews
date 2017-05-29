package com.example.administrator.entity;

import java.util.ArrayList;

/**
 * 新闻分类
 * Created by Administrator on 2017-04-21.
 */

public class NewsType {
    String message;  //状态说明
    int status;//状态码
    ArrayList<NewsGroup> data;

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

    public ArrayList<NewsGroup> getData() {
        return data;
    }

    public void setData(ArrayList<NewsGroup> data) {
        this.data = data;
    }
}
