package com.shushan.manhua.entity.response;

import java.util.List;

public class RecommendResponse {
    /**
     * error : 0
     * msg : success
     * data : [{"book_id":1,"book_name":"大国工程","square_cover":"","label":["重生过去","畅想未来"]}]
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
         * book_name : 大国工程
         * square_cover :
         * label : ["重生过去","畅想未来"]
         */

        private int book_id;
        private String book_name;
        private String square_cover;
        private List<String> label;

        public int getBook_id() {
            return book_id;
        }

        public void setBook_id(int book_id) {
            this.book_id = book_id;
        }

        public String getBook_name() {
            return book_name;
        }

        public void setBook_name(String book_name) {
            this.book_name = book_name;
        }

        public String getSquare_cover() {
            return square_cover;
        }

        public void setSquare_cover(String square_cover) {
            this.square_cover = square_cover;
        }

        public List<String> getLabel() {
            return label;
        }

        public void setLabel(List<String> label) {
            this.label = label;
        }
    }
}
