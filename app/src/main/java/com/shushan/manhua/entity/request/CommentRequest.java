package com.shushan.manhua.entity.request;

public class CommentRequest {
    public String token;
    /**
     * 漫画id
     */
    public String book_id;
    /**
     * 1为漫画评论2为章节评论
     */
    public String type;
    /**
     * 章节id为0表示评价漫画 不为空评价章节id
     */
    public String catalogue_id;
    /**
     * 0最新1最热
     */
    public String state;
    /**
     *
     */
    public String page;
    public String pagesize;

}
