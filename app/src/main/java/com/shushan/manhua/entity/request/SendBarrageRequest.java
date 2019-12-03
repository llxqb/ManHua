package com.shushan.manhua.entity.request;

public class SendBarrageRequest {
    public String token;
    public String book_id;
    /**
     * 章节id
     */
    public String catalogue_id;
    /**
     * 弹幕内容
     */
    public String barrage_content;
    /**
     * 样式id
     */
    public String style_id;
    /**
     * 210.65
     */
    public String xcoord;
    /**
     * 100.33
     */
    public String ycoord;
    /**
     *  漫豆
     */
    public String bean;
}
