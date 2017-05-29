package com.example.administrator.util;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.activity.R;

/**
 * 我们所使用的所有页面的父类
 * Created by Administrator on 2017-04-11.
 */

public abstract class BaseActivity extends FragmentActivity {
    public ImageView mIvTitleLeft; //左边图片
    public ImageView mIvTitleRight;//右边图片
    public TextView mTvTitleText;//中间文字
    LinearLayout mContext;//联系上下文
    LayoutInflater mInflater;//布局填充器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将布局加载到整个页面
        super.setContentView(R.layout.activity_base);
        mInflater=getLayoutInflater();
        mContext= (LinearLayout) findViewById(R.id.base_bottom);
        mIvTitleLeft= (ImageView) findViewById(R.id.iv_title_home);
        mIvTitleRight= (ImageView) findViewById(R.id.iv_title_share);
        mTvTitleText= (TextView) findViewById(R.id.tv_title);
    }
    
    @Override
    public void setContentView(int layoutResID) {
        //将传入的布局layoutResID加载到mContext中
        //将Xml转换为View加载到mContext中
        mInflater.inflate(layoutResID,mContext);
        initView();
    }

    public abstract void initView();

    /**
     * 绑定监听事件
     */
    public void setOnClickListeners(View.OnClickListener listener, View...views){
        for (View view:views) {
            if(listener!=null){
                view.setOnClickListener(listener);
            }
        }
    }
}
