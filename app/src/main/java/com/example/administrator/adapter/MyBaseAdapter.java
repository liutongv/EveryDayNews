package com.example.administrator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * 我们所使用的所有适配器的父类
 * Created by Administrator on 2017-04-20.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    public ArrayList<T> mList; //数据源
    LayoutInflater mInflater;//布局加载器
    int LayoutId;//布局ID
    Context mContext;//上下文
    public MyBaseAdapter(ArrayList<T> mList,Context mContext,int layoutId){
        this.mList=mList;
        this.mContext=mContext;
        this.LayoutId=layoutId;
        mInflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder baseHolder;
        if(convertView==null){
            baseHolder=new BaseHolder();
            convertView=mInflater.inflate(LayoutId,null);
            convertView.setTag(baseHolder);
        }else{
             baseHolder= (BaseHolder) convertView.getTag();
        }
        T t=mList.get(position);
        rendering(convertView,t,position);//渲染
        return convertView;
    }

    /**
     * 渲染子条目布局
     * @param convertView
     * @param t
     * @param position
     */
    public abstract void rendering(View convertView, T t, int position);
    class BaseHolder{

    }
}
