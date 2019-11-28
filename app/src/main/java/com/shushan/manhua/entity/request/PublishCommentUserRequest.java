package com.shushan.manhua.entity.request;

public class PublishCommentUserRequest {
    public String token;
    /**
     * 主评论id
     */
    public String comment_id;
    /**
     * 图片 不用了
     */
    public String pics;
    /**
     * 评论内容
     */
    public String comment;
    /**
     * 被评论用户id
     */
    public String be_user_id;
    /**
     * 子评论id
     */
    public String reply_id;
}
