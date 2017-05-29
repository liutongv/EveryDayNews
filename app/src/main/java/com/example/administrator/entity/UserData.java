package com.example.administrator.entity;

import java.util.ArrayList;

/**
 * 用户中心 data数据
 * Created by Administrator on 2017-04-26.
 */

public class UserData {
    private String uid;//用户名
    private int integration;//用户积分票总数
    private ArrayList<UserLoginLog> loginlog;
    private String comnum;//评论总数

    public String getComnum() {
        return comnum;
    }

    public void setComnum(String comnum) {
        this.comnum = comnum;
    }

    public int getIntegration() {
        return integration;
    }

    public void setIntegration(int integration) {
        this.integration = integration;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<UserLoginLog> getLoginlog() {
        return loginlog;
    }

    public void setLoginlog(ArrayList<UserLoginLog> loginlog) {
        this.loginlog = loginlog;
    }
}
