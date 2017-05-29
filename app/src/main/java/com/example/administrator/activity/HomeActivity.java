package com.example.administrator.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.Fragment.CollectFragment;
import com.example.administrator.Fragment.ForgetPasswordFragment;
import com.example.administrator.Fragment.LogInFragment;
import com.example.administrator.Fragment.MainFragment;
import com.example.administrator.Fragment.RegisterFragment;
import com.example.administrator.entity.UpdateData;
import com.example.administrator.net.EasyHttp;
import com.example.administrator.net.NetAsyncTask;
import com.example.administrator.util.BaseActivity;
import com.example.administrator.util.Contents;
import com.example.administrator.util.NewsLinks;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 主界面
 * Created by Administrator on 2017-04-19.
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    public DrawerLayout mDLdirection;//控制左右侧滑
    LinearLayout mLLNews;//左侧滑新闻
    LinearLayout mLLFavorite;//左侧滑收藏
    LinearLayout mLLLocal;//左侧滑本地
    LinearLayout mLLComment;//左侧滑跟帖
    LinearLayout mLLPhoto;//左侧滑图片
    LinearLayout mLLShared;//右侧滑分享
    LinearLayout mLLVersionUpdate;//右侧滑版本更新
    public TextView mTvLogin;//右侧滑登录
    public LinearLayout mLlMainMainMain;//被替换的布局
    FragmentManager fragmentManager = getSupportFragmentManager();//获取fragment进入界面
    public static String token;//用户令牌
    /**
     * 声明Fragment
     */
    public MainFragment mainFragment = new MainFragment();//主界面Fragment
    public LogInFragment loginFragment = new LogInFragment();//登录Fragment
    public CollectFragment collectFragment = new CollectFragment();//收藏Fragment
    public RegisterFragment registerFragment = new RegisterFragment();//注册Fragment
    public ForgetPasswordFragment forgetPasswordFragment = new ForgetPasswordFragment();//忘记密码Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmainmain);
    }

    @Override
    public void initView() {
        mTvTitleText.setText("资讯");
        mDLdirection = (DrawerLayout) findViewById(R.id.dl_control_direction);//控制左右侧滑
        mLlMainMainMain = (LinearLayout) findViewById(R.id.ll_mainmainmain);//被替换的布局
        mLLNews = (LinearLayout) findViewById(R.id.left_news);//左侧滑新闻
        mLLFavorite = (LinearLayout) findViewById(R.id.left_favorite);//左侧滑收藏
        mLLLocal = (LinearLayout) findViewById(R.id.left_local);//左侧滑当地
        mLLComment = (LinearLayout) findViewById(R.id.left_comment);//左侧滑跟帖
        mLLPhoto = (LinearLayout) findViewById(R.id.left_photo);//左侧滑照片
        mTvLogin = (TextView) findViewById(R.id.tv_main_right_login);//右侧滑登录
        mLLShared = (LinearLayout) findViewById(R.id.ll_right_shared);//右侧滑分享
        mLLVersionUpdate = (LinearLayout) findViewById(R.id.ll_version_update);//右侧滑版本更新
        /**
         * 展示主界面
         */
        fragmentManager.beginTransaction().add(R.id.ll_mainmainmain, mainFragment).commit();
        /**
         * 绑定事件的监听
         */
        setOnClickListeners(this, mIvTitleLeft, mIvTitleRight, mTvLogin, mLLNews,
                mLLFavorite, mLLLocal, mLLComment, mLLPhoto, mLLShared, mLLVersionUpdate);
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.left_news: //左侧滑新闻
                mTvTitleText.setText("新闻");
                mDLdirection.closeDrawer(Gravity.LEFT);//关闭左侧滑
                fragmentTransaction2.replace(R.id.ll_mainmainmain, mainFragment).commit();//切换到新闻Fragment
                break;
            case R.id.left_favorite://左侧滑收藏
                mTvTitleText.setText("收藏");
                mDLdirection.closeDrawer(Gravity.LEFT);//关闭左侧滑
                fragmentTransaction2.replace(R.id.ll_mainmainmain, collectFragment).commit();//切换到收藏Fragment
                break;
            case R.id.left_local: //左侧滑本地
                //TODO
                break;
            case R.id.left_comment://左侧滑跟帖
                //TODO
                break;
            case R.id.left_photo: //左侧滑图片
                //TODO
                break;
            case R.id.iv_title_home: //左侧滑
                mDLdirection.openDrawer(Gravity.LEFT);//点击左边图片显示左侧滑
                break;
            case R.id.iv_title_share:  //右侧滑
                mDLdirection.openDrawer(Gravity.RIGHT);//点击右边图片显示右侧滑
                break;
            case R.id.tv_main_right_login:  //登录碎片
                mDLdirection.closeDrawer(Gravity.RIGHT);//关闭右侧滑
                mTvTitleText.setText("登录");
                if (mTvLogin.getText().equals("立即登录")) { //如果右侧滑的头像下面的文字为立即登录，则切换到登录碎片
                    fragmentTransaction2.replace(R.id.ll_mainmainmain, loginFragment).commit();
                } else {   //否则，跳转到个人中心界面
                    Intent intent = new Intent(this, UserHomeActivity.class);
                    intent.putExtra(Contents.TOKEN, token); //将用户令牌传递给个人中心Activity
                    startActivity(intent);
                }
                break;
            case R.id.ll_version_update:  //右侧滑版本更新
                upDateVersion();
                break;
            case R.id.ll_right_shared:  //右侧滑分享
                showShare();
                break;
        }
    }

    /**
     * 版本更新
     */
    public void upDateVersion() {
        Map<String, String> map = new HashMap<>();
        map.put("imei", "0");
        map.put("pkg", "com.example.administrator.activity");
        map.put("ver", "0000000");
        EasyHttp.doGet(this, NewsLinks.UPDATE, map, new NetAsyncTask.OnNetListener() {
            @Override
            public void success(String info) {
                Gson gson = new Gson();
                UpdateData data = gson.fromJson(info, UpdateData.class);
                //获取所用程序的包名
                PackageManager pManager=getPackageManager();
                //获取当前应用的包名
                String packageName=getPackageName();
                try {
                    PackageInfo packageInfo=pManager.getPackageInfo(packageName,0);
                    //获取当前版本号
                    int verCode=packageInfo.versionCode;
                    if (verCode != Integer.parseInt(data.getVersion())) {
                        /**
                         * 下载最新版本的apk
                         */
                        //获取DownloadManager对象
                        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        //新建一个请求，并且指定下载链接
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(data.getLink()));
                        //对请求进行设置
                        //下载时的网络设置类型
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                        //下载进度显示在通知栏
                        request.setShowRunningNotification(true);
                        //下载文件的名称
                        request.setDestinationInExternalFilesDir(HomeActivity.this, null, "EveryDayNews.apk");
                        //开始下载
                        manager.enqueue(request);
                    } else {
                        Toast.makeText(HomeActivity.this, "当前为最新版本", Toast.LENGTH_SHORT).show();
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(int code) {
                Toast.makeText(HomeActivity.this,"请求失败，失败码："+code,Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);
    }
}
