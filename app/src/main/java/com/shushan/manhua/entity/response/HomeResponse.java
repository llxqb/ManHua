package com.shushan.manhua.entity.response;

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

    public static class BannerBean {
        /**
         * banner_name : 测试banner图
         * banner_pic : http://www.baidu.com
         * banner_cate : 1
         * banner_url : http://www.baidu.com
         * custom :
         */

        private String banner_name;
        private String banner_pic;
        private int banner_cate;
        private String banner_url;
        private String custom;

        public String getBanner_name() {
            return banner_name;
        }

        public void setBanner_name(String banner_name) {
            this.banner_name = banner_name;
        }

        public String getBanner_pic() {
            return banner_pic;
        }

        public void setBanner_pic(String banner_pic) {
            this.banner_pic = banner_pic;
        }

        public int getBanner_cate() {
            return banner_cate;
        }

        public void setBanner_cate(int banner_cate) {
            this.banner_cate = banner_cate;
        }

        public String getBanner_url() {
            return banner_url;
        }

        public void setBanner_url(String banner_url) {
            this.banner_url = banner_url;
        }

        public String getCustom() {
            return custom;
        }

        public void setCustom(String custom) {
            this.custom = custom;
        }
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
