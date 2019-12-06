package com.shushan.manhua.entity.user;

import java.io.Serializable;

/**
 * Created by li.liu on 2018/1/29.
 * 保存登陆信息
 * sp保存对象必须序列化
 */

public class User implements Serializable {

    public String token;
    public String name;
    public String avatar;
    /**
     * 0 非VIP 1 VIP
     */
    public int vip;
    public int vip_end_time;
    /**
     * 1 男 2 女
     */
    public int channel;
    public String bookType;
    /**
     * 漫豆数量
     */
    public int bean;
    /**
     * true ：游客模式登录
     * false: 账号登录
     */
    public boolean isTouristMode;
    /**
     * 选择的弹幕样式
     */
    public int barrageStyle;

}
