package com.shushan.manhua.entity.user;

import java.io.Serializable;

/**
 * Created by li.liu on 2018/1/29.
 * 保存登陆信息
 * sp保存对象必须序列化
 */

public class User implements Serializable {
    public User() {
    }

    public User(String token, String name, String avatar, int vip, int vip_end_time, int channel, String bookType) {
        this.token = token;
        this.name = name;
        this.avatar = avatar;
        this.vip = vip;
        this.vip_end_time = vip_end_time;
        this.channel = channel;
        this.bookType = bookType;
    }

    public String token;
    public String name;
    public String avatar;
    public int vip;
    public int vip_end_time;
    public int channel;
    public String bookType;
    /**
     * 漫豆数量
     */
    public int bean;

}
