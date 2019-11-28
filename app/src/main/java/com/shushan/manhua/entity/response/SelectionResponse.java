package com.shushan.manhua.entity.response;

import java.util.List;

public class SelectionResponse {


    /**
     * anthology : [{"catalogue_id":1,"catalogue_name":"第一话 纪元时代","catalogue_cover":"http://www.baidu.com","create_time":1574142426,"like":6,"change_like":2,"type":0,"sort":1,"is_like":1},{"catalogue_id":2,"catalogue_name":"第二话 青铜时代","catalogue_cover":"http://www.baidu.com","create_time":1574142425,"like":7,"change_like":3,"type":1,"sort":2,"is_like":1}]
     * residue_words : 0
     * words : 0
     * last_catalogue_id : 0
     */

    private int residue_words;
    private int words;
    private int last_catalogue_id;
    private List<AnthologyBean> anthology;

    public int getResidue_words() {
        return residue_words;
    }

    public void setResidue_words(int residue_words) {
        this.residue_words = residue_words;
    }

    public int getWords() {
        return words;
    }

    public void setWords(int words) {
        this.words = words;
    }

    public int getLast_catalogue_id() {
        return last_catalogue_id;
    }

    public void setLast_catalogue_id(int last_catalogue_id) {
        this.last_catalogue_id = last_catalogue_id;
    }

    public List<AnthologyBean> getAnthology() {
        return anthology;
    }

    public void setAnthology(List<AnthologyBean> anthology) {
        this.anthology = anthology;
    }

    public static class AnthologyBean {
        /**
         * catalogue_id : 1
         * catalogue_name : 第一话 纪元时代
         * catalogue_cover : http://www.baidu.com
         * create_time : 1574142426
         * like : 6
         * change_like : 2
         * type : 0
         * sort : 1
         * is_like : 1
         */

        private int catalogue_id;
        private String catalogue_name;
        private String catalogue_cover;
        private int create_time;
        private int like;
        private int change_like;
        private int type;
        private int sort;
        private int is_like;

        public int getCatalogue_id() {
            return catalogue_id;
        }

        public void setCatalogue_id(int catalogue_id) {
            this.catalogue_id = catalogue_id;
        }

        public String getCatalogue_name() {
            return catalogue_name;
        }

        public void setCatalogue_name(String catalogue_name) {
            this.catalogue_name = catalogue_name;
        }

        public String getCatalogue_cover() {
            return catalogue_cover;
        }

        public void setCatalogue_cover(String catalogue_cover) {
            this.catalogue_cover = catalogue_cover;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public int getChange_like() {
            return change_like;
        }

        public void setChange_like(int change_like) {
            this.change_like = change_like;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getIs_like() {
            return is_like;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }
    }
}
