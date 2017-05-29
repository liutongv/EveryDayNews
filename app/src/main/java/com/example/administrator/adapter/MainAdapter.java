package com.example.administrator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.activity.R;
import com.example.administrator.entity.MainData;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * 主界面&收藏界面的适配器
 * Created by Administrator on 2017-04-14.
 */

public class MainAdapter extends MyBaseAdapter<MainData>{
    public MainAdapter(ArrayList<MainData> mList, Context mContext, int layoutId) {
        super(mList, mContext, layoutId);
    }

    /**
     * 渲染子条目
     * @param convertView
     * @param mainData
     * @param position
     */
    @Override
    public void rendering(View convertView, MainData mainData, int position) {
        ImageView mNewsPhoto= (ImageView) convertView.findViewById(R.id.iv_item_main_collect_photo);//照片
        TextView mNewstitle= (TextView) convertView.findViewById(R.id.tv_item_main_collect_title);//标题
        TextView mNewsContent= (TextView) convertView.findViewById(R.id.tv_item_main_collect_content);//摘要
        TextView mNewsDate= (TextView) convertView.findViewById(R.id.tv_item_main_collect_date);//时间
        mNewstitle.setText(mainData.getTitle());//新闻标题
        mNewsContent.setText(mainData.getSummary());//新闻摘要
        mNewsDate.setText(mainData.getStamp());//新闻时间
        Glide.with(mContext).load(mainData.getIcon()).into(mNewsPhoto);//新闻对应的图片
    }
}
