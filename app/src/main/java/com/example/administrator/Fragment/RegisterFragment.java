package com.example.administrator.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
 * Created by Administrator on 2017-04-25.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    Button mBtnReg;//立即注册
    CheckBox mChecked;//选中状态
    TextInputEditText mEtEmailAddress;//输入邮箱地址
    TextInputEditText mEtName;//输入昵称
    EditText mEtPassword;//输入密码
    boolean isDegreeChecked;//选中状态
    TextInputLayout mTILEmail;//邮箱输入框的包装
    TextInputLayout mTILName;//昵称输入框的包装
    TextInputLayout mTILPassword;//密码输入框的包装
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_register,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnReg = (Button) view.findViewById(R.id.btn_reg_reg);  //立即注册
        mChecked = (CheckBox) view.findViewById(R.id.cb_reg_checked);//同意条款的选中
        mEtEmailAddress = (TextInputEditText) view.findViewById(R.id.et_reg_email_address);//输入的邮箱地址
        mEtName = (TextInputEditText) view.findViewById(R.id.et_reg_name);//输入的用户名
        mEtPassword = (EditText) view.findViewById(R.id.et_reg_password);//输入的密码
        mTILEmail= (TextInputLayout) view.findViewById(R.id.til_reg_email_address);//邮箱输入框的包装
        mTILName= (TextInputLayout) view.findViewById(R.id.til_reg_name);//用户名输入框的包装
        mTILPassword= (TextInputLayout) view.findViewById(R.id.til_reg_password);//密码输入框的包装
        mTILName.setCounterEnabled(true);//输入的用户名计数
        mTILPassword.setCounterEnabled(true);//输入的密码计数
        mTILEmail.setCounterEnabled(true);//输入的邮箱地址计数
        mBtnReg.setOnClickListener(this);
        mChecked.setOnCheckedChangeListener(this);
        mEtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 if(mEtName.getText().toString().length()<6){
                     mTILName.setError("用户名不得小于6");
                 }else if(mEtName.getText().toString().length()>24){
                     mTILName.setError("用户名不得大于24");
                 }else{
                     mTILName.setError("");
                     if(mEtName.getText().toString().matches("[a-zA-Z0-9_]{6,24}")==false){
                         mTILName.setError("用户名格式不正确，必须由大小写字母、数字、下划线组成");
                     }else{
                         mTILName.setError("");
                     }
                 }
            }
        });

        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mEtPassword.getText().toString().length()<6){
                    mTILPassword.setError("密码不得小于6");
                }else if(mEtPassword.getText().toString().length()>24){
                    mTILPassword.setError("密码不得大于16");
                }else{
                    mTILPassword.setError("");
                    if(mEtPassword.getText().toString().matches("[a-zA-Z0-9_]{6,16}")==false){
                        mTILPassword.setError("密码格式不正确，必须由大小写字母、数字、下划线组成");
                    }else{
                        mTILPassword.setError("");
                    }
                }
            }
        });
    }

    /**
     * 传：用户名 密码 邮箱
     */
    public boolean regxData(){
        //输入邮箱地址地址
        boolean email=mEtEmailAddress.getText().toString().matches("(w{3}+(\\.)){0,4}+([a-zA-Z0-9_-]{8,10})+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})");
        //输入用户昵称
        boolean name=mEtName.getText().toString().matches("[a-zA-Z0-9_]{6,24}");
        //输入密码
        boolean password=mEtPassword.getText().toString().matches("[a-zA-Z0-9_]{6,16}");
        if(email==true&&name==true&&password==true){  //如果输入的格式全都正确
            return true;
        }
        if(!email){
            Toast.makeText(getContext(),"邮箱格式不正确",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_reg_reg://注册
                if(regxData()&&isDegreeChecked){ //格式正确
                    putData();//注册成功将数据传到服务器
                }else if(!isDegreeChecked){  //未选同意条款选框
                    Toast.makeText(getContext(),"请选择同意条款☺",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     *注册成功将数据传到服务器
     */
    public void putData(){
        Map<String,String> map=new HashMap<>();
        map.put("ver","0000000");  //版本
        map.put("uid",mEtName.getText().toString());  //用户名
        map.put("email",mEtEmailAddress.getText().toString());  //邮箱地址
        map.put("pwd",mEtPassword.getText().toString());  //密码
        EasyHttp.doGet(getContext(), NewsLinks.USER_REG,map, new NetAsyncTask.OnNetListener() {
            @Override
            public void success(String info) {
                Gson gson=new Gson();
                RegInfo regInfo=gson.fromJson(info, RegInfo.class);
                if(regInfo.getStatus()==0){   //注册成功
                    Toast.makeText(getContext(),"注册成功",Toast.LENGTH_SHORT).show();
                    HomeActivity homeActivity= (HomeActivity) getActivity();
                    homeActivity.mTvTitleText.setText("登录");
                    FragmentTransaction ft=getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.ll_mainmainmain,homeActivity.loginFragment).commit();//切换到登录碎片
                };
            }

            @Override
            public void failure(int code) {
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isDegreeChecked=isChecked;
    }
}
