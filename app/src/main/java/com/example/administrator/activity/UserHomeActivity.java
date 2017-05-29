package com.example.administrator.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.entity.RegData;
import com.example.administrator.entity.RegInfo;
import com.example.administrator.entity.UserInfo;
import com.example.administrator.entity.UserLoginLog;
import com.example.administrator.net.EasyHttp;
import com.example.administrator.net.NetAsyncTask;
import com.example.administrator.util.BaseActivity;
import com.example.administrator.util.Contents;
import com.example.administrator.util.NewsLinks;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Manifest;

import javax.security.auth.login.LoginException;

/**
 * 个人中心界面
 * Created by Administrator on 2017-04-25.
 */

public class UserHomeActivity extends BaseActivity implements View.OnClickListener {
    Button mBtnExit;//退出登录
    TextView mTvUserName;//用户名
    TextView mTvScore;//积分
    TextView mTvCount;//跟帖数统计
    TextView mTvLoginLog;//登录日志
    ImageView mIvIcon;//头像
    PopupWindow popupWindow;//弹框(选择相机拍摄或本地图库)
    public static final int REQUEST_CAMERA=0;  //请求相机权限的响应码
    public static final int CAMERA_REQUEST_CODE=2;//相机的请求码
    public static final int LOCAL_REQUEST_CODE=3;//本地图库的请求码
    public static final int CROP_REQUEST_CODE=4;//裁剪的请求码
    //所拍(选)照片的存储路径
    public static final String PHOTO_PATH= Environment.getExternalStorageDirectory()
            + File.separator+"EveryDayNews"+ File.separator+"photo.jpg";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhome);
        getUserHomeInfo();//获取用户中心数据
    }

    @Override
    public void initView() {
        mIvTitleLeft.setImageResource(R.mipmap.back);//左边返回键
        mIvTitleRight.setVisibility(View.GONE);
        mTvTitleText.setText("个人中心");
        mBtnExit= (Button) findViewById(R.id.btn_user_home_exit);//退出登录
        mTvUserName= (TextView) findViewById(R.id.tv_user_home_name);//用户名
        mTvScore= (TextView) findViewById(R.id.tv_user_home_score);//用户积分
        mTvLoginLog= (TextView) findViewById(R.id.lv_user_log);//登录日志
        mTvCount= (TextView) findViewById(R.id.tv_user_home_count);//跟帖数统计
        mIvIcon= (ImageView) findViewById(R.id.iv_userhome_icon);//用户头像
        /**
         * 绑定点击事件的监听
         */
        setOnClickListeners(this,mIvTitleLeft,mBtnExit,mIvIcon);
        File file=new File(PHOTO_PATH);
        if(file.exists()){  //如果文件存在
            Bitmap bitmap=BitmapFactory.decodeFile(PHOTO_PATH);  //将文件转换为Bitmap
            mIvIcon.setImageBitmap(bitmap);//将转换的Bitmap设置为用户头像
        }
    }

    /**
     *
     * 获取用户中心数据
     */
    public void getUserHomeInfo(){
        Intent intent=getIntent();
        String token=intent.getStringExtra(Contents.TOKEN); //获取主界面传递过来的用户令牌
        Map<String,String> map=new HashMap<>();
        map.put("ver","0000000"); //版本
        map.put("imei","1");  //手机标识符
        map.put("token",token);  //用户令牌
        EasyHttp.doGet(this, NewsLinks.USER_HOME, map, new NetAsyncTask.OnNetListener() {
            @Override
            public void success(String info) {
                Gson gson=new Gson();
                UserInfo userInfo=gson.fromJson(info, UserInfo.class);//解析字符串
                mTvLoginLog.setMovementMethod(ScrollingMovementMethod.getInstance());//设置TextView可滑动
                mTvUserName.setText("用户名："+userInfo.getData().getUid());
                mTvScore.setText("积分："+userInfo.getData().getIntegration());
                mTvCount.setText("跟帖数统计："+userInfo.getData().getComnum());
                StringBuilder log=new StringBuilder();
                for (int i = 0; i <userInfo.getData().getLoginlog().size() ; i++) {
                    UserLoginLog loginLog=userInfo.getData().getLoginlog().get(i);
                    log.append(loginLog.getTime()+" ")
                            .append(loginLog.getAddress()+" ")
                            .append(loginLog.getDevice()+"\n");
                }
                mTvLoginLog.setText(log); //用户登录日志
            }
            @Override
            public void failure(int code) {
                Toast.makeText(UserHomeActivity.this,"请求失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_title_home: //返回键
                finish();
                break;
            case R.id.btn_user_home_exit://退出登录
                Intent intent=new Intent(this,HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_userhome_icon: //头像
                popupWindow=new PopupWindow(); //弹框
                View view= LayoutInflater.from(this).inflate(R.layout.activity_userhome_popwindow,null);//将xml文件转换为view
                popupWindow.setContentView(view);//设置展示的界面
                popupWindow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.normalbutton_pressed));//设置背景
                popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);//设置宽度
                popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);//设置高度
                popupWindow.setOutsideTouchable(true);//设置外部可点击
                popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);//展示PopupWindow到界面上(依赖于整个屏幕)
                /**
                 * 调用相机拍照
                 */
                TextView mTvCamera= (TextView) view.findViewById(R.id.tv_userhome_pop_camera);
                mTvCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //用户版本
                        if(Build.VERSION.SDK_INT==Build.VERSION_CODES.M){
                            //允许调用摄像头
                            if(PackageManager.PERMISSION_GRANTED==checkSelfPermission(android.Manifest.permission.CAMERA)&&
                                PackageManager.PERMISSION_GRANTED==checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                                photoCamera();//跳转到相机拍照
                            }else{//不允许
                                requestPermissions(new String[]{android.Manifest.permission.CAMERA,android.Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
                            }
                        }else{
                            photoCamera();
                        }
                    }
                });
                /**
                 * 从图库中选取照片
                 */
                TextView mTvLocalPhoto= (TextView) view.findViewById(R.id.tv__userhome_pop_photo);
                mTvLocalPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        photoLocal();//跳转到本地图库
                    }
                });
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CAMERA:  //请求的是相机的权限
                if(PackageManager.PERMISSION_GRANTED==grantResults[0]&&PackageManager.PERMISSION_GRANTED==grantResults[1]){
                    //用户选择了允许此权限
                    photoCamera();//跳转到相机拍照
                }else{
                    Toast.makeText(this,"打开相机需要此权限-->设置-->权限管理-->EveryDayNews",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 图片裁剪
     */
    public void photoCrop(String filePath){
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(new File(filePath)),"image/*");
        //设置裁剪
        intent.putExtra("crop","true");
        //设置宽高的比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //设置裁剪图片的宽高
        intent.putExtra("outputX",340);
        intent.putExtra("outputY",340);
        //裁剪完后是否返回数据
        intent.putExtra("return-data",true);
        startActivityForResult(intent,CROP_REQUEST_CODE);
    }
    /**
     * 跳转到相机拍照
     */
    public void photoCamera(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  //跳转到摄像头
        File file=new File(PHOTO_PATH);
        if(!file.exists()){  //如果文件不存在
            file.getParentFile().mkdirs();  //创建父目录
            try {
                file.createNewFile(); //创建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //向相机界面传递拍照路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
    }
    /**
     * 跳转到本地图库
     */
    public void photoLocal(){
        Intent intent=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,LOCAL_REQUEST_CODE);
    }

    /**
     * 接受返回的数据
     * @param requestCode  请求码
     * @param resultCode   结果码
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:  //相机返回的结果
                if(resultCode==RESULT_OK){ //拍照成功
                    photoCrop(PHOTO_PATH);
                }
                break;
            case LOCAL_REQUEST_CODE:  //本地图库返回的结果
                if(resultCode==RESULT_OK){
                    //获取存储此所选图片的URI
                    Uri path=data.getData();
                    //需要查询的列
                    String[] column={MediaStore.Images.Media.DATA};
                    //进行查询
                    Cursor query=getContentResolver().query(path,column,null,null,null);
                    query.moveToFirst();
                    //查询数据
                    String filePath=query.getString(query.getColumnIndex(column[0]));
                    photoCrop(filePath);
                }
                break;
            case CROP_REQUEST_CODE:  //裁剪返回的结果
                if(resultCode==RESULT_OK){
                    //获取裁剪的图片
                    Bundle ex=data.getExtras();
                    Bitmap photo=ex.getParcelable("data");
                    FileOutputStream output= null;
                    try {
                        output = new FileOutputStream(PHOTO_PATH);
                        photo.compress(Bitmap.CompressFormat.PNG,90,output);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }finally {
                        if(output!=null){
                            try {
                                output.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    mIvIcon.setImageBitmap(photo);//将头像设置为所剪裁的图片
                    popupWindow.dismiss();//取消展示弹框
                }
                break;
        }
    }
}
