package com.example.administrator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.administrator.Fragment.MainFragment;
import com.example.administrator.entity.MainData;
import java.util.ArrayList;

/**
 * 新闻收藏数据库
 * Created by Administrator on 2017-05-05.
 */

public class FavoriteDataBase {
    //获取对应的OpenHelper
    SQLiteDatabase db;
    public FavoriteDataBase(Context mContext) {
        FavoriteOpenHelper favoriteOpenHelper = new FavoriteOpenHelper(mContext, 2);
        db = favoriteOpenHelper.getWritableDatabase();//数量少的时候使用
                             //.getReadableDatabase();//数量多的时候使用
    }

    /**
     * 增
     */
    public void insert() {
        //使用SQLiteDataBase提供的方法直接传入数据
        ContentValues values = new ContentValues();
        values.put("summary", MainFragment.mainData.getSummary()); //摘要
        values.put("icon", MainFragment.mainData.getIcon()); //图片
        values.put("stamp", MainFragment.mainData.getStamp()); //时间
        values.put("title", MainFragment.mainData.getTitle());//标题
        values.put("nid", MainFragment.mainData.getNid());//新闻ID
        values.put("link", MainFragment.mainData.getLink());//新闻链接
        values.put("type", MainFragment.mainData.getType());//1.列表新闻  2.大图新闻
        db.insert("favorite", null, values);
    }

    /**
     * 查
     */
    public ArrayList<MainData> research() {
        String sql = "select * from favorite"; // 查询favorite表中所有列的内容
        Cursor c = db.rawQuery(sql, null);
        ArrayList<MainData> list = new ArrayList<>();
        while (c.moveToNext()) {
            String mSummary = c.getString(c.getColumnIndex("summary"));// 获取summary列的所有内容(摘要)
            String mIcon = c.getString(c.getColumnIndex("icon"));// 获取icon列的所有内容(图片)
            String mStamp = c.getString(c.getColumnIndex("stamp"));// 获取stamp列的所有内容(时间)
            String mTitle = c.getString(c.getColumnIndex("title"));// 获取title列的所有内容(标题)
            int mNid = c.getInt(c.getColumnIndex("nid"));// 获取nid列的所有内容(新闻ID)
            String mLink = c.getString(c.getColumnIndex("link"));// 获取link列的所有内容(链接)
            int mType = c.getInt(c.getColumnIndex("type"));// 获取type列的所有内容(新闻类型)
            list.add(new MainData(mSummary,mIcon,mStamp,mTitle,mNid,mLink,mType));
        }
        return list;
    }
}
