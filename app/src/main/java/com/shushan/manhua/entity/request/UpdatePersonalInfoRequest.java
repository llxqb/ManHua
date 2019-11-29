package com.shushan.manhua.entity.request;

public class UpdatePersonalInfoRequest {
    public String token;
    /**
     * 昵称（可选）
     */
    public String name;
    /**
     * 头像（可选）
     */
    public String head_portrait;
    /**
     * 1男2女0未填写（可选）
     */
    public String sex;
    /**
     * 时间戳（可选）
     */
    public String birthday;
}
