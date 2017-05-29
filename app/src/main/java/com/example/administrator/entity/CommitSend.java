package com.example.administrator.entity;

/**
 * 发布评论
 * Created by Administrator on 2017-04-27.
 */

public class CommitSend {
    private String meaasge;
    private int status;
    private CommitSendData data;

    public CommitSendData getData() {
        return data;
    }

    public void setData(CommitSendData data) {
        this.data = data;
    }

    public String getMeaasge() {
        return meaasge;
    }

    public void setMeaasge(String meaasge) {
        this.meaasge = meaasge;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
