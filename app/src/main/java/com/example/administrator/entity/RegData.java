package com.example.administrator.entity;

/**
 * 注册用户的Data信息
 * Created by Administrator on 2017-04-25.
 */

public class RegData {
    private int result;//登录状态
    private String explain;//注册成功
    private String token;//用户令牌

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
