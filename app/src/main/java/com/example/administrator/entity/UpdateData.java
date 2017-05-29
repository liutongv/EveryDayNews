package com.example.administrator.entity;

/**
 * 版本更新数据
 * Created by Administrator on 2017-05-16.
 */

public class UpdateData {
    private String pkgName;
    private String version;
    private String link;
    private String md5;

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
