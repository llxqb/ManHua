package com.shushan.manhua.entity.request;

public class FacebookLoginRequest {
    public String access_token;
    /**
     * ios,android
     */
    public String from;
    /**
     * 设备id
     */
    public String deviceId;
    /**
     * 1男频2女频
     */
    public String channel;
    /**
     * [1,2,3]喜欢的类型
     */
    public String book_type;
}
