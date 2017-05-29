package com.example.administrator.entity;

import java.util.ArrayList;

/**
 * 主界面数据
 * Created by Administrator on 2017-04-19.
 */

public class MainInfo {
    /**
     * 状态码
     * 0 -1 -2 -3
     */
    private int status;
    /**
     * 对状态码的文字说明
     * 0：正常响应
     * -1：无效参数(参数错误，参数不完整)
     * -2：拒绝服务(如客户版本太低)
     * -3：服务器内部错误(如无法连接到数据库)
     */
    private String message;
    private ArrayList<MainData> data;

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

    public ArrayList<MainData> getData() {
        return data;
    }

    public void setData(ArrayList<MainData> data) {
        this.data = data;
    }
}
