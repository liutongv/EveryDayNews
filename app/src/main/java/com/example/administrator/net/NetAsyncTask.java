package com.example.administrator.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017-04-13.
 */

public class NetAsyncTask extends AsyncTask<Request,Object,ResponseData>{
    Context mContext;//上下文
    ProgressDialog mProgressDialog; //加载框
    public NetAsyncTask(Context context){
        mContext=context;
    }

    /**
     * 主线程，在启动任务之后立即调用
     * 主要用于做一些请求之前的初始化工作
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog=ProgressDialog.show(mContext,"","");
    }

    /**
     * 子线程，在onPreExecute()值执行
     * 主要是进行一些具体的请求
     * 注意：子线程中不能刷新UI
     * @param params 启动任务所需要的额外条件
     * @return 执行请求所得到的结果
     */
    @Override
    protected ResponseData doInBackground(Request... params) {
        Request request=params[0];
        HttpURLConnection urlConnection=null;
        StringBuilder builder=new StringBuilder();
        ResponseData responseData=new ResponseData();
        try {
            URL url=new URL(EasyHttp.GET.equals(request.requestMethod)?
                    request.requestURL+request.requestParam:
                    request.requestURL);
            urlConnection= (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000);
            if(EasyHttp.POST.equals(request.requestMethod)){   //post请求
                 urlConnection.setDoOutput(true);//可写
                 urlConnection.setRequestMethod(EasyHttp.POST);//请求方式
                 //将参数写入请求体
                urlConnection.getOutputStream().write(request.requestParam.getBytes());
            }
            //开始连接
            urlConnection.connect();
            //响应码
            int code=urlConnection.getResponseCode();
            responseData.code=code;
            if(code==HttpURLConnection.HTTP_OK){   //链接成功
                InputStream input=urlConnection.getInputStream();
                byte[] b=new byte[1024];
                int len;
                while((len=input.read(b))!=-1){
                    builder.append(new String(b,0,len));
                }
                //数据读取完毕
                responseData.info=builder.toString();
                responseData.input=input;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(urlConnection!=null){
                //释放网络连接中的资源
                urlConnection.disconnect();
            }
        }
        return responseData;
    }

    /**
     * 主线程，在doInBackground()执行之后调用
     * 主要处理请求的结果
     * @param o doInBackground()执行所返回的结果
     */
    @Override
    protected void onPostExecute(ResponseData o) {
        super.onPostExecute(o);
        if(o.code!=HttpURLConnection.HTTP_OK){  //连接失败
            mListener.failure(o.code);
        }else{//连接成功
            mListener.success(o.info);
        }
        mProgressDialog.cancel();
    }

    /**
     * 自定义的回调接口
     */
    public interface OnNetListener{
        void success(String info);//成功
        void failure(int code);//失败
    }
    OnNetListener mListener;
    public void setOnNetListener(OnNetListener listener){
        mListener=listener;
    }
}
