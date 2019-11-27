package com.shushan.manhua.entity.request;

public class CommentSuggestRequest {
    public String token;
    /**
     * id=1书本 id2章节 id3书本主评论 id4章节评论 id5评论的评论id
     */
    public String relation_id;
    /**
     * 1点赞书本 2点赞章节 3点赞书本评论 4点赞章节评论 5点赞评论的回复
     */
    public String type;
}
