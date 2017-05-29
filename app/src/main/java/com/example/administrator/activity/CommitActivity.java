package com.example.administrator.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.adapter.CommitAdapter;
import com.example.administrator.entity.CommitInfo;
import com.example.administrator.entity.CommitSend;
import com.example.administrator.net.EasyHttp;
import com.example.administrator.net.NetAsyncTask;
import com.example.administrator.util.BaseActivity;
import com.example.administrator.util.Contents;
import com.example.administrator.util.NewsLinks;
import com.example.administrator.view.XListView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

/**
 * 评论界面
 * Created by Administrator on 2017-04-26.
 */

public class CommitActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener {
    XListView xListView;//子条目
    EditText mETWriteCommit;//输入评论
    ImageView mIvSend;//发送评论
    CommitAdapter adapter;//评论 适配器
    String newsId;//新闻ID
    int dir=1;//方向
    String cid="1";//评论ID
    String token;//用户令牌
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit);
    }

    @Override
    public void initView() {
        //获取新闻详情界面传递过来的数据
        Intent intent=getIntent();
        newsId=intent.getStringExtra(Contents.NEWS_ID); //获取新闻ID
        token=intent.getStringExtra(Contents.TOKEN);//获取用户令牌
        xListView= (XListView) findViewById(R.id.lv_commit);//子条目
        mETWriteCommit= (EditText) findViewById(R.id.et_commit_write);//输入评论
        mIvSend= (ImageView) findViewById(R.id.iv_commit_send);//发送
        setOnClickListeners(this,mIvSend,mIvTitleLeft);//绑定点击事件的监听
        mTvTitleText.setText("评论");
        mIvTitleLeft.setImageResource(R.mipmap.back);//左上角返回键
        mIvTitleRight.setVisibility(View.GONE);
        adapter=new CommitAdapter(null,this,R.layout.item_commit);//适配器
        xListView.setAdapter(adapter);//将适配器加载到XListView
        xListView.setPullLoadEnable(true);//支持加载
        xListView.setPullRefreshEnable(true);//支持刷新
        xListView.setXListViewListener(this);//设置刷新加载的点击事件
        getCommit();//获取评论数据
    }

    /**
     *
     * 获取评论数据
     */
    public void getCommit(){
        Map<String,String> map=new HashMap<>();
        map.put("ver","0000000");//版本
        map.put("nid",newsId); //新闻ID
        map.put("type","1");
        map.put("stamp","20140321"); //评论时间
        map.put("cid",cid);  //评论ID
        map.put("dir",dir+"");//刷新列表的方向
        map.put("cnt","20");//每页展示的数目
        EasyHttp.doGet(this, NewsLinks.CMT_LIST, map, new NetAsyncTask.OnNetListener() {
            @Override
            public void success(String info) {
                Gson gson=new Gson();
                CommitInfo commitInfo=gson.fromJson(info, CommitInfo.class);
                if(commitInfo.getData()!=null){  //集合不为空
                    if(dir==1){ //1:刷新  2:加载
                        adapter.mList = commitInfo.getData();  //将数据加载到适配器
                    }else{  //加载
                        adapter.mList.addAll(commitInfo.getData());
                    }
                    adapter.notifyDataSetChanged();//刷新适配器
                }else{
                    Toast.makeText(CommitActivity.this,"没有新数据了(ಥ_ಥ)",Toast.LENGTH_SHORT).show();
                }
                xListView.stopLoadMore(); //停止加载
                xListView.stopRefresh();//停止刷新
            }
            @Override
            public void failure(int code) {
                Toast.makeText(CommitActivity.this,"获取数据失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 发布评论
     */
    public void sendCommit(){
        Map<String,String> map=new HashMap<>();
        map.put("ver","0000000");  //版本
        map.put("nid", newsId);  //  新闻编号
        map.put("token",token);  //用户令牌
        map.put("imei","1");  //手机标识符
        map.put("ctx",mETWriteCommit.getText().toString());//评论内容
        EasyHttp.doGet(this, NewsLinks.CMT_COMMIT, map, new NetAsyncTask.OnNetListener() {
            @Override
            public void success(String info) {
                Gson gson=new Gson();
                CommitSend commitSend=gson.fromJson(info, CommitSend.class);
                Toast.makeText(CommitActivity.this,commitSend.getData().getResult()+"--"+commitSend.getData().getExplain(),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void failure(int code) {
                Toast.makeText(CommitActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_commit_send:  //发送评论
                if(token!=null){
                    sendCommit();//发布评论
                    dir=1;//1:刷新 2：加载
                    getCommit();//获取评论数据
                    mETWriteCommit.setText("");
                }else{
                    Toast.makeText(CommitActivity.this,"登录后才可评论",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_title_home: //返回键
                finish();
                break;
        }
    }

    /**
     * 当刷新的时候自动调用
     */
    @Override
    public void onRefresh() {
        dir=1; //刷新
        getCommit(); //获取评论数据
    }

    /**
     * 当加载的时候自动调用
     */
    @Override
    public void onLoadMore() {
        dir=2; //2：加载
        cid=adapter.mList.get(adapter.mList.size()-1).getCid();//当前页面的最后一条数据
        getCommit(); //获取评论数据
        xListView.stopLoadMore(); //停止加载
    }
}
