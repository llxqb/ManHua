package com.shushan.manhua.entity.request;

public class PublishCommentRequest {
    public String token;
    /**
     * 书籍id
     */
    public String book_id;
    /**
     * 章节id为0表示评价漫画 不为空评价章节id
     */
    public String catalogue_id;
    public String pics;
    public String comment;
}
