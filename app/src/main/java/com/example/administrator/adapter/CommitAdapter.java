package com.example.administrator.adapter;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.activity.R;
import com.example.administrator.entity.CommitData;
import com.example.administrator.entity.MainInfo;

import java.util.ArrayList;

/**
 * 评论界面的适配器
 * Created by Administrator on 2017-04-27.
 */

public class CommitAdapter extends MyBaseAdapter<CommitData> {
    public CommitAdapter(ArrayList<CommitData> mList, Context mContext, int layoutId) {
        super(mList, mContext, layoutId);
    }

    @Override
    public void rendering(View convertView, CommitData commitData, int position) {
        ImageView mIvUserPhoto= (ImageView) convertView.findViewById(R.id.iv_commit_user_photo);
        TextView mTvUserName= (TextView) convertView.findViewById(R.id.tv_commit_user_name);
        TextView mTvContent= (TextView) convertView.findViewById(R.id.tv_commit_user_content);
        TextView mTvCommitTime= (TextView) convertView.findViewById(R.id.tv_commit_time);
        mTvUserName.setText(commitData.getUid());//用户名
        mTvContent.setText(commitData.getContent());//评论内容
        mTvCommitTime.setText(commitData.getStamp());//评论时间
        Glide.with(mContext).load(commitData.getPortrait()).into(mIvUserPhoto);//用户头像
    }
}
