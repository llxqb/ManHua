package com.shushan.manhua.entity.request;

public class DeleteMessageRequest {
    public String token;
    /**
     * 评论id
     */
    public String id;
    /**
     * 1删除主评论2删除回复
     */
    public String type;
}
