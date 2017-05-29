package com.example.administrator.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.Fragment.MainFragment;
import com.example.administrator.database.FavoriteDataBase;
import com.example.administrator.entity.CommitCount;
import com.example.administrator.entity.MainData;
import com.example.administrator.net.EasyHttp;
import com.example.administrator.net.NetAsyncTask;
import com.example.administrator.util.Contents;
import com.example.administrator.util.NewsLinks;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 新闻详情界面
 * Created by Administrator on 2017-04-26.
 */

public class NewsInfoActivity extends Activity implements View.OnClickListener {
    ImageView mIvBack;//左上角返回键
    TextView mTvCount;//跟帖数量
    ImageView mIvAddCollect;//右上角加入收藏
    WebView mWVNewsiInfo;//新闻详情
    public String newsNid;//新闻ID
    public String token;//用户令牌
    boolean isFavorited;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsinfo);
    }

    @Override
    public void onContentChanged() {
        findId();
        Intent intent = getIntent();
        String newsUrl = intent.getStringExtra(Contents.NEWS_LINKS);//获取新闻链接
        newsNid = intent.getStringExtra(Contents.NEWS_ID);//获取新闻ID
        token = intent.getStringExtra(Contents.TOKEN);//获取用户令牌
        newsCommitCount();//获取新闻评论数量
        mIvBack.setOnClickListener(this);
        mTvCount.setOnClickListener(this);
        mIvAddCollect.setOnClickListener(this);
        mWVNewsiInfo.loadUrl(newsUrl);  //展示网页
        /**
         * WebSettings 用来对WebView进行设置
         */
        WebSettings webSettings = mWVNewsiInfo.getSettings();
        webSettings.setSupportZoom(true); //支持手指缩放
        webSettings.setBuiltInZoomControls(true);//显示缩放按钮
        webSettings.setDisplayZoomControls(false);//隐藏缩放按钮
        mWVNewsiInfo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //true:使用当前的webView去加载
                //false:使用安装的浏览器进行加载
                return true;
            }
        });
    }

    /**
     * 寻找控件ID
     */
    public void findId() {
        mIvBack = (ImageView) findViewById(R.id.iv_newsinfo_back);//左上角返回键
        mTvCount = (TextView) findViewById(R.id.tv_newsinfo_commitinfo);//跟帖数量
        mIvAddCollect = (ImageView) findViewById(R.id.iv_newsinfo_add_collect);//加入收藏
        mWVNewsiInfo = (WebView) findViewById(R.id.wv_newsinfo);//新闻详情
    }

    /**
     * 获取新闻评论数量
     */
    public void newsCommitCount() {
        Map<String, String> map = new HashMap<>();
        map.put("ver", "0000000");//版本号
        map.put("nid", newsNid);//新闻编号
        EasyHttp.doGet(this, NewsLinks.CMT_NUM, map, new NetAsyncTask.OnNetListener() {
            @Override
            public void success(String info) {
                Gson gson = new Gson();
                CommitCount count = gson.fromJson(info, CommitCount.class);
                mTvCount.setText(count.getData() + "跟帖");//设置跟帖数量
            }

            @Override
            public void failure(int code) {
                Toast.makeText(NewsInfoActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_newsinfo_back://点击返回上个页面
                finish();
                break;
            case R.id.tv_newsinfo_commitinfo://点击查看评论详情
                Intent intent = new Intent(this, CommitActivity.class);//跳转到评论界面
                intent.putExtra(Contents.NEWS_ID, newsNid);//将新闻ID传递给评论界面
                intent.putExtra(Contents.TOKEN, token);//将用户令牌传递给评论界面
                startActivity(intent);//意图跳转
                break;
            case R.id.iv_newsinfo_add_collect://点击收藏新闻
                /**
                 * 加入收藏弹框的实现
                 */
                final PopupWindow popupWindow = new PopupWindow();//弹框
                View view = LayoutInflater.from(this).inflate(R.layout.activity_newsinfo_popwindow, null);
                popupWindow.setContentView(view);//设置展示的内容
                popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);  //设置宽度
                popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);  //设置高度
                popupWindow.setOutsideTouchable(true);//设置外部是否可点击
                popupWindow.showAsDropDown(mIvAddCollect);//展示PopupWindow到界面上
                Button mBtnAdd = (Button) view.findViewById(R.id.btn_add);
                mBtnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FavoriteDataBase database = new FavoriteDataBase(NewsInfoActivity.this);
                        //数据库集合长度:database.research().size()
                        if(database.research().size()==0){//当数据库为空的时候
                            database.insert();//往数据库里添加数据
                            Toast.makeText(NewsInfoActivity.this, "已加入收藏", Toast.LENGTH_SHORT).show();
                        }else {
                            for (MainData d : database.research()) {
                                //数据库集合中的所有summary:d.getSummary()
                                //准备添加的新闻的summary:MainFragment.mainData.getSummary()
                                if (d.getSummary().equals(MainFragment.mainData.getSummary())) {
                                    Toast.makeText(NewsInfoActivity.this, "该新闻已收藏过了❀", Toast.LENGTH_SHORT).show();
                                    isFavorited = true;
                                }
                            }
                            if (isFavorited==false) {
                                //当前数据与数据库集合中数据不同
                                database.insert();//往数据库里添加数据
                                Toast.makeText(NewsInfoActivity.this, "已加入收藏", Toast.LENGTH_SHORT).show();
                            }
                        }
                        popupWindow.dismiss();//弹框消失
                    }
                });
                break;
        }
    }
}
