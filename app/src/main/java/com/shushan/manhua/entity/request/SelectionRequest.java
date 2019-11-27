package com.shushan.manhua.entity.request;

public class SelectionRequest {
    public String token;
    /**
     * 漫画id
     */
    public String book_id;
    /**
     * 默认desc降序 asc升序
     */
    public String orderby;
    public String page;
    public String pagesize;
}
