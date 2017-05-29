package com.example.administrator.entity;

/**
 * 新闻分类中的详细数据
 * Created by Administrator on 2017-04-21.
 */

public class NewsTypeDetail {
    String subgroup;//子分类名
    int subid;//子分类号

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }

    public int getSubid() {
        return subid;
    }

    public void setSubid(int subid) {
        this.subid = subid;
    }
}
