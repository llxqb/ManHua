package com.shushan.manhua.entity.response;

import com.shushan.manhua.entity.RecommendBean;

import java.util.List;

public class RecommendResponse {
    /**
     * error : 0
     * msg : success
     * data : [{"book_id":1,"book_name":"大国工程","square_cover":"","label":["重生过去","畅想未来"]}]
     */

    private int error;
    private String msg;
    private List<RecommendBean> data;

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

    public List<RecommendBean> getData() {
        return data;
    }

    public void setData(List<RecommendBean> data) {
        this.data = data;
    }


}
