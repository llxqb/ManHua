package com.shushan.manhua.entity.response;

import java.util.List;

public class RechargeRecordResponse {
    /**
     * error : 0
     * msg : success
     * data : [{"book_id":1,"user_id":1,"name":"看漫画","count":10,"book_name":"大国工程","detail_cover":"","catalogue_name":"第一话 纪元时代","catalogue_id":1}]
     */

    private int error;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * book_id : 1
         * user_id : 1
         * name : 看漫画
         * count : 10
         * book_name : 大国工程
         * detail_cover :
         * catalogue_name : 第一话 纪元时代
         * catalogue_id : 1
         */

        private int book_id;
        private int user_id;
        private String name;
        private int count;
        private String book_name;
        private String detail_cover;
        private String catalogue_name;
        private int catalogue_id;

        public int getBook_id() {
            return book_id;
        }

        public void setBook_id(int book_id) {
            this.book_id = book_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getBook_name() {
            return book_name;
        }

        public void setBook_name(String book_name) {
            this.book_name = book_name;
        }

        public String getDetail_cover() {
            return detail_cover;
        }

        public void setDetail_cover(String detail_cover) {
            this.detail_cover = detail_cover;
        }

        public String getCatalogue_name() {
            return catalogue_name;
        }

        public void setCatalogue_name(String catalogue_name) {
            this.catalogue_name = catalogue_name;
        }

        public int getCatalogue_id() {
            return catalogue_id;
        }

        public void setCatalogue_id(int catalogue_id) {
            this.catalogue_id = catalogue_id;
        }
    }
}
