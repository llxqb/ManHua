package com.shushan.manhua.entity.request;

public class ReadingSettingRequest {
    public String token;
    /**
     * 1男频2女频
     */
    public String channel;
    /**
     * [1,2,3]书籍类型id
     */
    public String book_type;
}
