package com.shushan.manhua.entity.response;

import java.util.List;

public class RechargeRecordResponse {

    /**
     * error : 0
     * msg : success
     * data : [{"book_id":0,"user_id":19,"name":"购买会员赠送","count":400,"book_name":null,"detail_cover":null,"catalogue_name":null,"catalogue_id":0,"status":6,"create_time":1575613266},{"book_id":0,"user_id":19,"name":"购买会员赠送","count":400,"book_name":null,"detail_cover":null,"catalogue_name":null,"catalogue_id":0,"status":6,"create_time":1575612854},{"book_id":0,"user_id":19,"name":"购买漫豆","count":100,"book_name":null,"detail_cover":null,"catalogue_name":null,"catalogue_id":0,"status":1,"create_time":1575610853},{"book_id":0,"user_id":19,"name":"充值赠送","count":20,"book_name":null,"detail_cover":null,"catalogue_name":null,"catalogue_id":0,"status":2,"create_time":1575610853},{"book_id":0,"user_id":19,"name":"购买漫豆","count":100,"book_name":null,"detail_cover":null,"catalogue_name":null,"catalogue_id":0,"status":1,"create_time":1575609646},{"book_id":0,"user_id":19,"name":"充值赠送","count":20,"book_name":null,"detail_cover":null,"catalogue_name":null,"catalogue_id":0,"status":2,"create_time":1575609646}]
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
         * book_id : 0
         * user_id : 19
         * name : 购买会员赠送
         * count : 400
         * book_name : null
         * detail_cover : null
         * catalogue_name : null
         * catalogue_id : 0
         * status : 6
         * create_time : 1575613266
         */

        private int book_id;
        private int user_id;
        private String name;
        private int count;
        private String book_name;
        private String detail_cover;
        private String catalogue_name;
        private int catalogue_id;
        private int status;
        private int create_time;

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

        public Object getBook_name() {
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
    }
}
