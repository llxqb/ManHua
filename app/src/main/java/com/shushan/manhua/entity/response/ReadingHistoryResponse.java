package com.shushan.manhua.entity.response;

import java.util.List;

public class ReadingHistoryResponse {


    /**
     * error : 0
     * msg : success
     * data : [{"id":48,"user_id":54,"book_id":5,"catalogue_id":1,"create_time":1575685261,"type":1,"bean":0,"state":1,"deletetime":0,"book_name":"Hitungan mundur 7 hari ","oblong_cover":"https://img.pulaukomik.com/book/15756236893514.png","words":96,"catalogue_name":"第一话 纪元时代","sort":1,"residue_words":95},{"id":44,"user_id":54,"book_id":4,"catalogue_id":10,"create_time":1575684751,"type":2,"bean":5,"state":1,"deletetime":0,"book_name":"倒数七天","oblong_cover":"https://img.pulaukomik.com/book/15755934237250.png","words":5,"catalogue_name":"第五话","sort":5,"residue_words":0},{"id":43,"user_id":54,"book_id":1,"catalogue_id":2,"create_time":1575682737,"type":2,"bean":5,"state":1,"deletetime":0,"book_name":"大国工程","oblong_cover":"","words":575,"catalogue_name":"第二话 青铜时代","sort":2,"residue_words":573}]
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
         * id : 48
         * user_id : 54
         * book_id : 5
         * catalogue_id : 1
         * create_time : 1575685261
         * type : 1
         * bean : 0
         * state : 1
         * deletetime : 0
         * book_name : Hitungan mundur 7 hari
         * oblong_cover : https://img.pulaukomik.com/book/15756236893514.png
         * words : 96
         * catalogue_name : 第一话 纪元时代
         * sort : 1
         * residue_words : 95
         */

        private int id;
        private int user_id;
        private int book_id;
        private int catalogue_id;
        private int create_time;
        private int type;
        private int bean;
        private int state;
        private int deletetime;
        private String book_name;
        private String oblong_cover;
        private int words;
        private String catalogue_name;
        private int sort;
        private int residue_words;
        public boolean isEditState;
        public boolean isCheck;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
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

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getBean() {
            return bean;
        }

        public void setBean(int bean) {
            this.bean = bean;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getDeletetime() {
            return deletetime;
        }

        public void setDeletetime(int deletetime) {
            this.deletetime = deletetime;
        }

        public String getBook_name() {
            return book_name;
        }

        public void setBook_name(String book_name) {
            this.book_name = book_name;
        }

        public String getOblong_cover() {
            return oblong_cover;
        }

        public void setOblong_cover(String oblong_cover) {
            this.oblong_cover = oblong_cover;
        }

        public int getWords() {
            return words;
        }

        public void setWords(int words) {
            this.words = words;
        }

        public String getCatalogue_name() {
            return catalogue_name;
        }

        public void setCatalogue_name(String catalogue_name) {
            this.catalogue_name = catalogue_name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getResidue_words() {
            return residue_words;
        }

        public void setResidue_words(int residue_words) {
            this.residue_words = residue_words;
        }
    }
}
