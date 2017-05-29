package com.example.administrator.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.activity.HomeActivity;
import com.example.administrator.activity.R;
import com.example.administrator.entity.RegInfo;
import com.example.administrator.net.EasyHttp;
import com.example.administrator.net.NetAsyncTask;
import com.example.administrator.util.NewsLinks;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录碎片
 * Created by Administrator on 2017-04-24.
 */

public class LogInFragment extends Fragment implements View.OnClickListener {
    Button mBtnRag;//注册按钮
    Button mBtnForget;//忘记密码按钮
    Button mBtnLogin;//登录按钮
    EditText mETName;//输入的用户名
    EditText mETPassword;//输入的密码
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnRag= (Button) view.findViewById(R.id.btn_login_reg);//注册按钮
        mBtnForget= (Button) view.findViewById(R.id.btn_login_forget_password);//忘记密码按钮
        mBtnLogin= (Button) view.findViewById(R.id.btn_login_login);//登录按钮
        mETName= (EditText) view.findViewById(R.id.et_userName);//输入的用户名
        mETPassword= (EditText) view.findViewById(R.id.et_userPassword);//输入的密码
        /**
         * 绑定点击事件监听
         */
        mBtnRag.setOnClickListener(this);
        mBtnForget.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
    }

    /**
     *判断输入的用户名密码是否存在
     */
    public void judgeUserInfo(){
        Map<String,String> map=new HashMap<>();
        map.put("ver","0000000");  //版本
        map.put("uid",mETName.getText().toString());  //用户名
        map.put("pwd",mETPassword.getText().toString());  //密码
        map.put("device","0");  //登录设备
        EasyHttp.doGet(getContext(), NewsLinks.USER_LOGIN, map, new NetAsyncTask.OnNetListener() {
            @Override
            public void success(String info) {
                Gson gson=new Gson();
                RegInfo regInfo=gson.fromJson(info, RegInfo.class);//解析字符串
                if(regInfo.getStatus()==0){  //登录成功
                    final HomeActivity homeActivity= (HomeActivity) getActivity();
                    homeActivity.mTvTitleText.setText("资讯");
                    homeActivity.token=regInfo.getData().getToken();//将用户令牌赋值给主界面的token
                    homeActivity.mTvLogin.setText(mETName.getText().toString());//将用户名展示到右侧滑的头像下面
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.ll_mainmainmain,homeActivity.mainFragment).commit();//登陆成功切换到主页面
                }else{
                    Toast.makeText(getContext(),"登录失败",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void failure(int code) {
                 Toast.makeText(getContext(),"登录失败，请检查网络",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        HomeActivity homeActivity= (HomeActivity) getActivity();
        FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
        switch (v.getId()){
            case R.id.btn_login_reg://切换到注册Fragment
                homeActivity.mDLdirection.closeDrawer(Gravity.RIGHT);//关闭右侧滑
                homeActivity.mTvTitleText.setText("注册");
                ft.replace(R.id.ll_mainmainmain,homeActivity.registerFragment).commit();
                break;
            case R.id.btn_login_forget_password://切换到忘记密码Fragment
                homeActivity.mTvTitleText.setText("忘记密码");
                homeActivity.mDLdirection.closeDrawer(Gravity.RIGHT);//关闭右侧滑
                ft.replace(R.id.ll_mainmainmain,homeActivity.forgetPasswordFragment).commit();
                break;
            case R.id.btn_login_login://登录按钮
                judgeUserInfo();//判断用户名和密码是否正确
                break;
        }
    }
}
