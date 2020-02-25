package com.shushan.manhua.entity.response;

import java.util.List;

public class RankingResponse {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * book_id : 5
         * book_name : Hitungan mundur 7 hari
         * label : [" Fantasi","Romantis"]
         * oblong_cover : https://img.pulaukomik.com/book/15756236893514.png
         * author : ake
         * lastBookCatalogue : 438
         */

        private int book_id;
        private String book_name;
        private String oblong_cover;
        private String author;
        private int lastBookCatalogue;
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

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getLastBookCatalogue() {
            return lastBookCatalogue;
        }

        public void setLastBookCatalogue(int lastBookCatalogue) {
            this.lastBookCatalogue = lastBookCatalogue;
        }

        public List<String> getLabel() {
            return label;
        }

        public void setLabel(List<String> label) {
            this.label = label;
        }
    }
}
