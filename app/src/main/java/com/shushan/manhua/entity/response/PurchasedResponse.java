package com.shushan.manhua.entity.response;

import java.util.List;

public class PurchasedResponse {
    /**
     * error : 0
     * msg : success
     * data : [{"book_id":1,"catalogue_id":1,"buy_words":2,"book_name":"大国工程","words":575}]
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
         * catalogue_id : 1
         * buy_words : 2
         * book_name : 大国工程
         * words : 575
         */

        private int book_id;
        private int catalogue_id;
        private int buy_words;
        private String book_name;
        private int words;

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

        public int getBuy_words() {
            return buy_words;
        }

        public void setBuy_words(int buy_words) {
            this.buy_words = buy_words;
        }

        public String getBook_name() {
            return book_name;
        }

        public void setBook_name(String book_name) {
            this.book_name = book_name;
        }

        public int getWords() {
            return words;
        }

        public void setWords(int words) {
            this.words = words;
        }
    }
}
