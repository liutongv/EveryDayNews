package com.example.administrator.util;

/**
 * Created by Administrator on 2017-04-19.
 * 所有链接地址
 */

public class NewsLinks {
    public static final String BASE_LINKS="http://118.244.212.82:9092/newsClient";
    //主界面展示新闻内容的链接
    public static final String MAIN_LINKS=BASE_LINKS+"/news_list";
    //主界面展示新闻分类的链接
    public static final String MAIN_NEWS_LINKS=BASE_LINKS+"/news_sort";
    //用户注册链接
    public static final String USER_REG=BASE_LINKS+"/user_register";
    //用户登录链接
    public static final String USER_LOGIN=BASE_LINKS+"/user_login";
    //找回密码链接
    public static final String USER_FORGETPASS=BASE_LINKS+"/user_forgetpass";
    //头像上传链接
    public static final String USER_IMAGE=BASE_LINKS+"/user_image";
    //版本升级链接
    public static final String UPDATE=BASE_LINKS+"/update";
    //用户中心链接
    public static final String USER_HOME=BASE_LINKS+"/user_home";
    //评论数量链接
    public static final String CMT_NUM=BASE_LINKS+"/cmt_num";
    //发布评论链接
    public static final String CMT_COMMIT=BASE_LINKS+"/cmt_commit";
    //显示评论链接
    public static final String CMT_LIST=BASE_LINKS+"/cmt_list";

}
