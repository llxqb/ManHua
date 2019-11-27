package com.shushan.manhua.entity.response;

import com.shushan.manhua.entity.BannerBean;

import java.util.List;

public class HomeResponse {


    private List<BannerBean> banner;
    private List<BooksBean> books;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<BooksBean> getBooks() {
        return books;
    }

    public void setBooks(List<BooksBean> books) {
        this.books = books;
    }


    public static class BooksBean {
        /**
         * book_id : 1
         * book_name : 大国工程
         * label : ["重生过去","畅想未来"]
         * oblong_cover :
         */

        private int book_id;
        private String book_name;
        private String oblong_cover;
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

        public List<String> getLabel() {
            return label;
        }

        public void setLabel(List<String> label) {
            this.label = label;
        }
    }
}
