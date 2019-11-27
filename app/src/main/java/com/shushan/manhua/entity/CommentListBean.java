package com.shushan.manhua.entity;

import java.util.List;

public class CommentListBean {
    /**
     * error : 0
     * msg : success
     * data : [{"comment_id":1,"user_id":1,"book_id":1,"content":"这本书真棒！","pics":[],"comment_time":1574077027,"like":0,"name":"测试","head_portrait":"http://www.baidu.com","is_like":0,"review":[{"review_id":1,"comment_id":1,"content":"这本书真棒！","pics":["https://caricature.oss-ap-southeast-5.aliyuncs.com/comment/20191118/5dd26de2b9ca8.png"],"review_time":1574077894,"user_id":1,"be_user_id":1,"user_name":"测试","user_head_portrait":"http://www.baidu.com","be_user_name":"测试","be_user_head_portrait":"http://www.baidu.com","like":0}],"review_count":1}]
     */

    private int error;
    private String msg;
    private List<CommentBean> data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CommentBean> getData() {
        return data;
    }

    public void setData(List<CommentBean> data) {
        this.data = data;
    }

}
