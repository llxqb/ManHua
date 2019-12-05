package com.shushan.manhua.entity.response;

import java.util.List;

public class MessageResponse {

    /**
     * error : 0
     * msg : success
     * data : [{"comment_id":16,"book_id":1,"catalogue_id":0,"content":"123456789","pics":[],"comment_time":1575511652,"like":0,"book_name":"大国工程","detail_cover":"","catalogue_name":"","type":1},{"comment_id":15,"book_id":1,"catalogue_id":1,"content":"ttttt","pics":[],"comment_time":1575457343,"like":0,"book_name":"大国工程","detail_cover":"","catalogue_name":"第一话 纪元时代","type":2}]
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
         * comment_id : 16
         * book_id : 1
         * catalogue_id : 0
         * content : 123456789
         * pics : []
         * comment_time : 1575511652
         * like : 0
         * book_name : 大国工程
         * detail_cover :
         * catalogue_name :
         * type : 1
         */

        private int comment_id;
        private int book_id;
        private int catalogue_id;
        private String content;
        private int comment_time;
        private int like;
        private String book_name;
        private String detail_cover;
        private String catalogue_name;
        private int type;
        private List<?> pics;

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public int getBook_id() {
            return book_id;
        }

        public void setBook_id(int book_id) {
            this.book_id = book_id;
        }

        public int getCatalogue_id() {
            return catalogue_id;
        }

        public void setCatalogue_id(int catalogue_id) {
            this.catalogue_id = catalogue_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getComment_time() {
            return comment_time;
        }

        public void setComment_time(int comment_time) {
            this.comment_time = comment_time;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<?> getPics() {
            return pics;
        }

        public void setPics(List<?> pics) {
            this.pics = pics;
        }
    }
}
