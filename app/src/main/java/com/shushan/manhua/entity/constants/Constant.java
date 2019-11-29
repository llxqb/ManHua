package com.shushan.manhua.entity.constants;


import com.shushan.manhua.R;

/**
 * Created by li.liu on 2017/12/18.
 */

public class Constant {
    //设备标识
    public static final String FROM = "android";
    /**
     * 默认图片
     */
    //占位图图片资源
    public static final int LOADING_DEFAULT_1 = R.mipmap.default_graph_1;
    public static final int LOADING_DEFAULT_2 = R.mipmap.default_graph_2;
    public static final int LOADING_DEFAULT_3 = R.mipmap.default_graph_3;
    public static final int LOADING_DEFAULT_4 = R.mipmap.default_graph_4;
    public static final int LOADING_AVATOR = R.mipmap.default_head;
//    public static final int LOADING_BANNER = R.mipmap.load_banner;

    /**
     * CommonDialog 样式
     */
    public static final int COMMON_DIALOG_STYLE_1 = 1;
    public static final int COMMON_DIALOG_STYLE_2 = 2;

    //Google登录回调
    public static final int GOOGLE_LOGIN = 100;
    //facebook登录回调
    public static final int FACEBOOK_LOGIN = 64206;


    public static final int PAGESIZE = 10;

    //recyclerView item 中某一/某些控件刷新
    public static final String ITEM_UPDATE = "itemUpdate";

    //sp保存的字段
    public static final String CHANNEL = "channel";
    public static final String BOOK_TYPE = "book_type";
    public static final String IS_BARRAGE = "barrage";//是否弹幕开关
    public static final String IS_TURN_PAGE = "turn_page";//是否点击上下翻页
    public static final String IS_NIGHT_MODEL = "night_model";//是否夜间模式开关
    /**
     * 登录模式： 游客模式 1、 账号登录 2
     */
    public static final String LOGIN_MODEL = "login_model";


}
