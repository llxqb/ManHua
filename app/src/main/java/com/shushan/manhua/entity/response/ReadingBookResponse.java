package com.shushan.manhua.entity.response;

/**
 * 小说
 */
public class ReadingBookResponse {
    /**
     * catalogue : {"catalogue_id":424,"catalogue_name":"第一章","square_cover":"https://img.pulaukomik.com/book/15766518202816.png","words":100,"novel_url":"https://img.pulaukomik.com/novel/20191218/a56c41e8078e75122986fa2bff3fbfb2.txt","type":0,"sort":1,"cost":4,"vip_cost":3,"book_name":"起点","userbean":408,"state":0,"next_catalogue_id":425,"pre_catalogue_id":0}
     */

    private CatalogueBean catalogue;

    public CatalogueBean getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(CatalogueBean catalogue) {
        this.catalogue = catalogue;
    }

    public static class CatalogueBean {
        /**
         * catalogue_id : 424
         * catalogue_name : 第一章
         * square_cover : https://img.pulaukomik.com/book/15766518202816.png
         * words : 100
         * novel_url : https://img.pulaukomik.com/novel/20191218/a56c41e8078e75122986fa2bff3fbfb2.txt
         * type : 0
         * sort : 1
         * cost : 4
         * vip_cost : 3
         * book_name : 起点
         * userbean : 408
         * state : 0
         * next_catalogue_id : 425
         * pre_catalogue_id : 0
         */

        private int catalogue_id;
        private String catalogue_name;
        private String square_cover;
        private int words;
        private String novel_url;
        private int type;
        private int sort;
        private int cost;
        private int vip_cost;
        private String book_name;
        private int userbean;
        private int state;
        private int next_catalogue_id;
        private int pre_catalogue_id;

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

        public String getSquare_cover() {
            return square_cover;
        }

        public void setSquare_cover(String square_cover) {
            this.square_cover = square_cover;
        }

        public int getWords() {
            return words;
        }

        public void setWords(int words) {
            this.words = words;
        }

        public String getNovel_url() {
            return novel_url;
        }

        public void setNovel_url(String novel_url) {
            this.novel_url = novel_url;
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

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public int getVip_cost() {
            return vip_cost;
        }

        public void setVip_cost(int vip_cost) {
            this.vip_cost = vip_cost;
        }

        public String getBook_name() {
            return book_name;
        }

        public void setBook_name(String book_name) {
            this.book_name = book_name;
        }

        public int getUserbean() {
            return userbean;
        }

        public void setUserbean(int userbean) {
            this.userbean = userbean;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getNext_catalogue_id() {
            return next_catalogue_id;
        }

        public void setNext_catalogue_id(int next_catalogue_id) {
            this.next_catalogue_id = next_catalogue_id;
        }

        public int getPre_catalogue_id() {
            return pre_catalogue_id;
        }

        public void setPre_catalogue_id(int pre_catalogue_id) {
            this.pre_catalogue_id = pre_catalogue_id;
        }
    }
}
