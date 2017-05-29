package com.example.administrator.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.activity.HomeActivity;
import com.example.administrator.activity.NewsInfoActivity;
import com.example.administrator.activity.R;
import com.example.administrator.adapter.MainAdapter;
import com.example.administrator.entity.MainData;
import com.example.administrator.entity.MainInfo;
import com.example.administrator.entity.NewsType;
import com.example.administrator.entity.NewsTypeDetail;
import com.example.administrator.net.EasyHttp;
import com.example.administrator.net.NetAsyncTask;
import com.example.administrator.util.Contents;
import com.example.administrator.util.NewsLinks;
import com.example.administrator.view.XListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-04-25.
 */

public class MainFragment extends Fragment implements XListView.IXListViewListener {
    MainAdapter adapter;//适配器
    XListView xListView;//子条目
    LinearLayout mLLNewsType;//新闻分类的布局
    public static MainData mainData; //新闻数据
    ArrayList<NewsTypeDetail> newsTypeDataDetail;//所有新闻分类的数据
    int dir=1;//方向
    int nid=1;//新闻ID
    static int subid;//新闻分类
    HomeActivity homeActivity= (HomeActivity) getActivity();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        xListView = (XListView) view.findViewById(R.id.xlv_main);
        adapter = new MainAdapter(null, getContext(), R.layout.item_main_collect);
        xListView.setAdapter(adapter);
        xListView.setPullLoadEnable(true);//支持加载
        xListView.setPullRefreshEnable(true);//支持刷新
        xListView.setXListViewListener(this);//设置刷新加载的点击事件
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mainData=adapter.mList.get(position-1);
                Intent intent=new Intent(getContext(), NewsInfoActivity.class);
                intent.putExtra(Contents.SUBID,subid+"");//新闻分类
                intent.putExtra(Contents.TOKEN,homeActivity.token);//用户令牌
                intent.putExtra(Contents.NEWS_LINKS,adapter.mList.get(position-1).getLink());//新闻链接
                intent.putExtra(Contents.NEWS_ID,adapter.mList.get(position-1).getNid()+"");//新闻id
                startActivity(intent);
            }
        });//设置子条目的点击事件
        getNewsType();
        mLLNewsType = (LinearLayout) view.findViewById(R.id.ll_news_type);//新闻分类的布局
    }

    /**
     * 获取新闻分类数据
     */
    public void getNewsType() {
        Map<String, String> map = new HashMap<>();
        map.put("ver", "0000000");  //版本
        map.put("imei", "0");  //手机标识符
        EasyHttp.doGet(getContext(), NewsLinks.MAIN_NEWS_LINKS, map, new NetAsyncTask.OnNetListener() {
            @Override
            public void success(String info) {
                Gson gson = new Gson();
                NewsType type = gson.fromJson(info, NewsType.class);
                //设置新闻类型的展示
                setViewShowNewsType(type);
            }

            @Override
            public void failure(int code) {
                Toast.makeText(getContext(), "请求失败，请检查网络ღ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 设置新闻类型的展示
     */
    public void setViewShowNewsType(NewsType newsType) {;
        newsTypeDataDetail = new ArrayList<>();
        //展示分类
        for (int i = 0; i < newsType.getData().size(); i++) {
            //展示子分类
            for (int j = 0; j < newsType.getData().get(i).getSubgrp().size(); j++) {
                newsTypeDataDetail.add(newsType.getData().get(i).getSubgrp().get(j));
            }
        }
        //展示到界面上
        for (int i = 0; i < newsTypeDataDetail.size(); i++) {
            TextView newsTypeView = new TextView(getContext());
            //设置内容
            newsTypeView.setText(newsTypeDataDetail.get(i).getSubgroup());
            //设置字体大小
            newsTypeView.setTextSize(18);
            //设置宽度 高度 权重
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            //设置重力
            newsTypeView.setGravity(Gravity.CENTER);
            newsTypeView.setLayoutParams(params);
            //绑定监听k
            newsTypeView.setOnClickListener(new View.OnClickListener() {
                //设置新闻分类监听
                @Override
                public void onClick(View v) {
                    dir=1;//获取最新数据
                    for (int i = 0; i < mLLNewsType.getChildCount(); i++) {
                        TextView childView = (TextView) mLLNewsType.getChildAt(i);
                        if (v.equals(childView)) {
                            //字体颜色设为红色
                            childView.setTextColor(getResources().getColor(R.color.colorNewsTitle));
                            //设置新闻类型的展示
                            getNewsDeta(newsTypeDataDetail.get(i).getSubid());
                            subid=newsTypeDataDetail.get(i).getSubid();
                        } else {
                            //字体颜色设为黑色
                            childView.setTextColor(getResources().getColor(R.color.colorBlack));
                        }
                    }
                }
            });
            mLLNewsType.addView(newsTypeView);
        }
        //第一个TextView
        TextView showView = (TextView) mLLNewsType.getChildAt(0);
        //字体设置为红色
        showView.setTextColor(getResources().getColor(R.color.colorNewsTitle));
        //参数：子分类号  默认展示下标为1的
        getNewsDeta(newsTypeDataDetail.get(0).getSubid());
    }

    /**
     * 获取新闻列表数据
     */
    public void getNewsDeta(int subid) {
        Map<String, String> map = new HashMap<>();
        map.put("ver", "0000000");  //版本
        map.put("subid", subid + "");  //新闻分类号
        map.put("dir", dir+"");  //方向
        map.put("nid", nid+"");  //新闻ID
        map.put("stamp", "20140321000000");  //新闻时间
        map.put("cnt", "20");  //最大更新条目数
        /**
         * 获取数据源，将数据源加载到适配器
         */
        EasyHttp.doGet(getContext(), NewsLinks.MAIN_LINKS, map, new NetAsyncTask.OnNetListener() {
            @Override
            public void success(String info) {
                Gson gson = new Gson();
                //将json字符串转为对象
                MainInfo mainInfo = gson.fromJson(info, MainInfo.class);
                if(mainInfo.getData()!=null){  //集合不为空
                    if(dir==1){ //刷新
                        adapter.mList = mainInfo.getData();  //将数据加载到适配器
                    }else{  //加载
                        adapter.mList.addAll(mainInfo.getData());
                    }
                    adapter.notifyDataSetChanged();//刷新适配器
                }else{
                    Toast.makeText(getContext(),"没有新数据了(ಥ_ಥ)",Toast.LENGTH_SHORT).show();
                }
                xListView.stopLoadMore(); //停止加载
                xListView.stopRefresh();//停止刷新
            }

            @Override
            public void failure(int code) {
                Toast.makeText(getContext(), "请求失败，请检查网络ღ", Toast.LENGTH_SHORT).show();
                xListView.stopLoadMore(); //停止加载
                xListView.stopRefresh();//停止刷新
            }
        });
    }

    /**
     * 刷新的时候自动调用
     */
    @Override
    public void onRefresh() {
        dir=1;//最新数据
        getNewsDeta(subid);
        if(adapter.mList==null){
            getNewsType();
        }
    }

    /**
     * 加载的时候自动调用
     */
    @Override
    public void onLoadMore() {
        if(adapter.mList!=null){
            dir=2;//加载数据
            nid=adapter.mList.get(adapter.mList.size()-1).getNid();//当前页面的最后一条数据
            getNewsDeta(subid);
        }else{  //没网，没获得数据的时候
            xListView.stopLoadMore(); //停止加载
            Toast.makeText(getContext(),"没有数据,请检查网络❀",Toast.LENGTH_SHORT).show();
        }
    }

}
