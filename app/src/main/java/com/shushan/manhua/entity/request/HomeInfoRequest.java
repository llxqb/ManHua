package com.shushan.manhua.entity.request;

public class HomeInfoRequest {
    public String token;
    /**
     * 1男频2女频
     */
    public String channel;
    /**
     * [1,2,3]用户选定喜欢漫画类型数组
     */
    public String book_type;
}
