package com.example.administrator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建新闻收藏数据库
 * Created by Administrator on 2017-05-05.
 */

public class FavoriteOpenHelper extends SQLiteOpenHelper {
    public static final String SQL_NAME="SQLName";
    public FavoriteOpenHelper(Context context, int version) {
        super(context, SQL_NAME, null, version);
    }

    /**
     * 创建数据库
     * 主要用来进行表的创建以及一些数据库的初始化的操作，当第一次使用此数据对象的时候自动调用
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //格式：create table 表名(列名)
        //summary:摘要  icon:对应的图片  stamp:时间  title:标题  nid:新闻ID  link:内容链接  type:新闻类型(1:列表新闻，2:大图新闻)
        String sql="create table favorite(summary text,icon text,stamp text,title text,nid integer,link text,type integer)";
        db.execSQL(sql);
    }

    /**
     * 修改
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
