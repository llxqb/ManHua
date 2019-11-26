package com.shushan.manhua.entity.request;

import com.shushan.manhua.entity.constants.Constant;

public class LoginRequest {
    public LoginRequest() {
        from = Constant.FROM;
    }

    public String access_token;
    public String from;
    public String deviceId;
    public String id;
    /**
     * 1男频2女频
     */
    public String channel;
    /**
     * [1,2,3]喜欢的类型
     */
    public String book_type;
}
