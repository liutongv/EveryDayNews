package com.example.administrator.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.activity.R;
import com.example.administrator.entity.ForgetInfo;
import com.example.administrator.net.EasyHttp;
import com.example.administrator.net.NetAsyncTask;
import com.example.administrator.util.NewsLinks;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * 忘记密码碎片
 * Created by Administrator on 2017-04-25.
 */

public class ForgetPasswordFragment extends Fragment implements View.OnClickListener {
    EditText mEtEmail;//输入注册的邮箱号
    Button mBtnAffirm;//确认按钮

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_forgetpassword, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEtEmail = (EditText) view.findViewById(R.id.et_forget_email);//输入注册的邮箱号
        mBtnAffirm = (Button) view.findViewById(R.id.btn_forgetpassword_affirm);//确认按钮
        mBtnAffirm.setOnClickListener(this);
    }

    /**
     * 找回密码
     */
    public void findPassword() {
        Map<String, String> map = new HashMap<>();
        map.put("ver", "0000000");  //版本
        map.put("email", mEtEmail.getText().toString());  //邮箱地址
        EasyHttp.doGet(getContext(), NewsLinks.USER_FORGETPASS, map, new NetAsyncTask.OnNetListener() {
            @Override
            public void success(String info) {
                Gson gson = new Gson();
                ForgetInfo forgetInfo = gson.fromJson(info, ForgetInfo.class);
                Toast.makeText(getContext(), forgetInfo.getData().getExplain(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(int code) {
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        findPassword();
    }
}
