package com.shushan.manhua.entity.request;

public class ReadRecordingRequest {
    public String token;
    public String book_id;
    /**
     * 章节id
     */
    public String catalogue_id;
    /**
     * 1免费看2收费
     */
    public String type;
    /**
     * 漫豆
     */
    public String bean;
}
