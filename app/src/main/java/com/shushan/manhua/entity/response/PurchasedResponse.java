package com.shushan.manhua.entity.response;

import java.util.List;

public class PurchasedResponse {

    /**
     * error : 0
     * msg : success
     * data : [{"book_id":5,"catalogue_id":39,"buy_words":1,"book_name":"Hitungan mundur 7 hari ","words":96,"oblong_cover":"https://img.pulaukomik.com/book/15756236893514.png"},{"book_id":4,"catalogue_id":10,"buy_words":1,"book_name":"倒数七天","words":5,"oblong_cover":"https://img.pulaukomik.com/book/15755934237250.png"},{"book_id":1,"catalogue_id":2,"buy_words":1,"book_name":"大国工程","words":575,"oblong_cover":""}]
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
         * book_id : 5
         * catalogue_id : 39
         * buy_words : 1
         * book_name : Hitungan mundur 7 hari
         * words : 96
         * oblong_cover : https://img.pulaukomik.com/book/15756236893514.png
         */

        private int book_id;
        private int catalogue_id;
        private int buy_words;
        private String book_name;
        private int words;
        private String oblong_cover;

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

        public String getOblong_cover() {
            return oblong_cover;
        }

        public void setOblong_cover(String oblong_cover) {
            this.oblong_cover = oblong_cover;
        }
    }
}
