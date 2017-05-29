package com.example.administrator.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * 加载页
 * Created by Administrator on 2017-04-11.
 */

public class LoadingActivity extends Activity implements Animation.AnimationListener {
    ImageView mIvLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }

    @Override
    public void onContentChanged() {
        mIvLoading= (ImageView) findViewById(R.id.iv_loading);
        setAnimation();//设置动画
    }

    /**
     * 设置动画
     */
    public void setAnimation(){
        //渐变动画
        AlphaAnimation anim=new AlphaAnimation(0,1);
        anim.setDuration(2000);//持续时间
        anim.setRepeatMode(Animation.REVERSE);//衔接模式
        mIvLoading.startAnimation(anim);//开启动画
        anim.setAnimationListener(this);//设置动画的监听
    }

    /**
     * 动画开始
     * @param animation
     */
    @Override
    public void onAnimationStart(Animation animation) {

    }

    /**
     * 动画结束
     * @param animation
     */
    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent=new Intent(this,HomeActivity.class); //跳转到主界面
        startActivity(intent);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
