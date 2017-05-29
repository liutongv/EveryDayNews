package com.example.administrator.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.adapter.GuidePagerAdapter;
import com.example.administrator.util.Contents;
import java.util.ArrayList;

/**
 * 引导页
 */
public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    ArrayList<ImageView> mList=new ArrayList<>();//存放引导界面图片  数据源
    int mPhoto[]={R.mipmap.welcome,R.mipmap.wy,R.mipmap.small}; //存放图片的数组
    Button mBtnJump;//直接跳转
    SharedPreferences mSharedPreferences;//用来存储数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        isFirstInto();//是否第一次进入
    }

    @Override
    public void onContentChanged() {
        mBtnJump= (Button) findViewById(R.id.btn_jump);
        ViewPager viewPager= (ViewPager) findViewById(R.id.vp_guide);
        getData();//数据源
        GuidePagerAdapter adapter=new GuidePagerAdapter(mList); //适配器
        viewPager.setAdapter(adapter);//加载适配器
        viewPager.setOnPageChangeListener(this);//绑定ViewPager的监听
        mBtnJump.setOnClickListener(this);
    }
    //获取数据源
    public void getData(){
        for (int i=0;i<mPhoto.length;i++){
            ImageView img=new ImageView(this);
            img.setScaleType(ImageView.ScaleType.FIT_XY);//使得图片的显示大小和View一致
            img.setImageResource(mPhoto[i]);
            mList.add(img);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 页面选择的时候调用
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        if(position==mPhoto.length-1) {
            mBtnJump.setVisibility(View.VISIBLE);
        }else{
            mBtnJump.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_jump:
                jump();
                SharedPreferences.Editor editor=mSharedPreferences.edit();//获取Editor对象
                editor.putBoolean(Contents.IS_FIRST,false);//写数据
                editor.commit();//提交
                break;
        }
    }

    /**
     * 跳转页面
     */
    public void jump(){
        Intent intent=new Intent(this,LoadingActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 判断是否是第一次进入
     */
    public void isFirstInto(){
        mSharedPreferences=getSharedPreferences(Contents.FILE_NAME,MODE_PRIVATE);//获取SharedPreference对象
        boolean isFirst=mSharedPreferences.getBoolean(Contents.IS_FIRST,true);//读数据
        if(!isFirst){
            jump();//跳转页面
            finish();
        }
    }
}
