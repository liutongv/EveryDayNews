package com.example.administrator.entity;

import java.util.ArrayList;

/**
 * 注册用户详细信息
 * Created by Administrator on 2017-04-25.
 */

public class RegInfo {
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

    private RegData data;
    public RegData getData() {
        return data;
    }
    public void setData(RegData data) {
        this.data = data;
    }
}
