package com.example.administrator.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * 引导页面的适配器
 * Created by Administrator on 2017-04-11.
 */

public class GuidePagerAdapter extends PagerAdapter {
    ArrayList<ImageView> mList;//数据源
    public GuidePagerAdapter(ArrayList<ImageView> mList){
        this.mList=mList;
    }
    @Override
    public int getCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    /**
     * 需要加载此位置的时候调用
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position));
        return mList.get(position);
    }

    /**
     * 再无法直接到达此位置的时候调用，将此位置的ImageView移除
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }
}
