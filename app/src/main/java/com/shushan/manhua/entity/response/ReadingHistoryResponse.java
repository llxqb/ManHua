package com.shushan.manhua.entity.response;

import java.util.List;

public class ReadingHistoryResponse {

    /**
     * error : 0
     * msg : success
     * data : [{"id":8,"user_id":19,"book_id":1,"catalogue_id":2,"create_time":1575424393,"type":1,"bean":5,"state":1,"deletetime":0,"book_name":"大国工程","detail_cover":"","words":575,"catalogue_name":"第二话 青铜时代","sort":2,"residue_words":573}]
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
         * id : 8
         * user_id : 19
         * book_id : 1
         * catalogue_id : 2
         * create_time : 1575424393
         * type : 1
         * bean : 5
         * state : 1
         * deletetime : 0
         * book_name : 大国工程
         * detail_cover :
         * words : 575
         * catalogue_name : 第二话 青铜时代
         * sort : 2
         * residue_words : 573
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
        private String detail_cover;
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

        public String getDetail_cover() {
            return detail_cover;
        }

        public void setDetail_cover(String detail_cover) {
            this.detail_cover = detail_cover;
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
