package com.shushan.manhua.entity.response;

import java.util.List;

public class BookClassificationResponse {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * book_id : 13
         * book_name :  Istri lembut di Pernikahan Kilat Tuan He
         * label : ["Perkotaan","Romantis"]
         * oblong_cover : https://img.pulaukomik.com/book/15759497236747.png
         * comment_count : 1100
         * like : 5502
         * des : Menjadi wanitaku harus mampu memenuhi 3 persyaratan: suka dimanja, suka disayang, dan suka dijaga dengan baik. wanita ini kebetulan sekali bisa memenuhi seluruh persyaratan ini, hanya butuh kesadarannya saja!
         * is_like : 0
         */

        private int book_id;
        private String book_name;
        private String oblong_cover;
        private int comment_count;
        private int like;
        private String des;
        private int is_like;
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

        public String getOblong_cover() {
            return oblong_cover;
        }

        public void setOblong_cover(String oblong_cover) {
            this.oblong_cover = oblong_cover;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public int getIs_like() {
            return is_like;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }

        public List<String> getLabel() {
            return label;
        }

        public void setLabel(List<String> label) {
            this.label = label;
        }
    }
}
