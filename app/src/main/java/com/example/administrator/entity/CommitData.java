package com.example.administrator.entity;

/**
 * 评论的Data数据
 * Created by Administrator on 2017-04-27.
 */

public class CommitData {
    private String uid;//用户名
    private String content;//评论内容
    private String stamp;//评论时间
    private String portrait;//用户头像链接
    private String cid;//评论编号

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
