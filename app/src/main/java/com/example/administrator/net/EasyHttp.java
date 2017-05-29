package com.example.administrator.net;

import android.content.Context;

import java.util.Map;

/**
 * Created by Administrator on 2017-04-13.
 */

public class EasyHttp {
    public static final String POST="POST";
    public static final String GET="GET";

    /**
     * 使用post请求
     * @param reURL 请求链接
     * @param reParam 请求参数
     */
    public static void doPost(Context context, String reURL, Map<String,String> reParam, NetAsyncTask.OnNetListener listener){
        Request request=new Request();
        request.requestURL=reURL;
        request.requestMethod=POST;
        request.requestParam=requestInfo(reParam,POST);
        NetAsyncTask task=new NetAsyncTask(context);
        task.setOnNetListener(listener);
        task.execute(request);
    }

    /**
     * 使用get请求
     */
    public static void doGet(Context context, String reURL, Map<String,String> reParam, NetAsyncTask.OnNetListener listener){
        Request request=new Request();
        request.requestURL=reURL; //请求链接
        request.requestMethod=GET;//请求方式
        request.requestParam=requestInfo(reParam,GET);//请求参数
        NetAsyncTask task=new NetAsyncTask(context);
        task.setOnNetListener(listener);//自定义的回调接口
        task.execute(request); //该方法只能在主线程调用
    }

    /**
     * 请求内容
     */
    public static String requestInfo(Map<String,String> reParam,String reMethod){
        StringBuilder builder=new StringBuilder();
        if(GET.equals(reMethod)){
            builder.append("?");
        }
        for (String key:reParam.keySet()) {
            builder.append(key).append("=").append(reParam.get(key)).append("&");
        }
        if(builder.length()>1){
            builder.deleteCharAt(builder.length()-1);
        }
        return builder.toString();
    }
}
