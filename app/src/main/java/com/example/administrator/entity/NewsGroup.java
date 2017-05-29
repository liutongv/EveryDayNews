package com.example.administrator.entity;

import java.util.ArrayList;

/**
 * 新闻分类中的组
 * Created by Administrator on 2017-04-21.
 */

public class NewsGroup {
    int gid;//分类号
    String group;//分类名
    ArrayList<NewsTypeDetail> subgrp;//子分类
    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public ArrayList<NewsTypeDetail> getSubgrp() {
        return subgrp;
    }

    public void setSubgrp(ArrayList<NewsTypeDetail> subgrp) {
        this.subgrp = subgrp;
    }


}
