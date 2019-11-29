package com.shushan.manhua.entity.request;

public class SubmitFeedbackRequest {
    public String token;
    /**
     * 联系方式（选填）
     */
    public String contact;
    /**
     * 反馈内容
     */
    public String content;
}
