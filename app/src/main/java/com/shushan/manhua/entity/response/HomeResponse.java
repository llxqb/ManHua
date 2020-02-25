package com.shushan.manhua.entity.response;

import com.shushan.manhua.entity.BannerBean;

import java.util.List;

public class HomeResponse {


    private List<BannerBean> banner;
    private List<HomeCommonBean> books;
    private List<HomeCommonBean> recommend;
    private List<HomeCommonBean> novels;
    private List<HomeCommonBean> moods;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<HomeCommonBean> getBooks() {
        return books;
    }

    public void setBooks(List<HomeCommonBean> books) {
        this.books = books;
    }

    public List<HomeCommonBean> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<HomeCommonBean> recommend) {
        this.recommend = recommend;
    }

    public List<HomeCommonBean> getNovels() {
        return novels;
    }

    public void setNovels(List<HomeCommonBean> novels) {
        this.novels = novels;
    }

    public List<HomeCommonBean> getMoods() {
        return moods;
    }

    public void setMoods(List<HomeCommonBean> moods) {
        this.moods = moods;
    }

    //    public static class BooksBean {
//        /**
//         * book_id : 13
//         * book_name :  Istri lembut di Pernikahan Kilat Tuan He
//         * label : ["Perkotaan","Romantis"]
//         * oblong_cover : https://img.pulaukomik.com/book/15759497236747.png
//         */
//
//        private int book_id;
//        private String book_name;
//        private String oblong_cover;
//        private List<String> label;
//
//        public int getBook_id() {
//            return book_id;
//        }
//
//        public void setBook_id(int book_id) {
//            this.book_id = book_id;
//        }
//
//        public String getBook_name() {
//            return book_name;
//        }
//
//        public void setBook_name(String book_name) {
//            this.book_name = book_name;
//        }
//
//        public String getOblong_cover() {
//            return oblong_cover;
//        }
//
//        public void setOblong_cover(String oblong_cover) {
//            this.oblong_cover = oblong_cover;
//        }
//
//        public List<String> getLabel() {
//            return label;
//        }
//
//        public void setLabel(List<String> label) {
//            this.label = label;
//        }
//    }

//    public static class RecommendBean {
//        /**
//         * book_id : 13
//         * book_name :  Istri lembut di Pernikahan Kilat Tuan He
//         * label : ["Perkotaan","Romantis"]
//         * oblong_cover : https://img.pulaukomik.com/book/15759497236747.png
//         */
//
//        private int book_id;
//        private String book_name;
//        private String oblong_cover;
//        private List<String> label;
//
//        public int getBook_id() {
//            return book_id;
//        }
//
//        public void setBook_id(int book_id) {
//            this.book_id = book_id;
//        }
//
//        public String getBook_name() {
//            return book_name;
//        }
//
//        public void setBook_name(String book_name) {
//            this.book_name = book_name;
//        }
//
//        public String getOblong_cover() {
//            return oblong_cover;
//        }
//
//        public void setOblong_cover(String oblong_cover) {
//            this.oblong_cover = oblong_cover;
//        }
//
//        public List<String> getLabel() {
//            return label;
//        }
//
//        public void setLabel(List<String> label) {
//            this.label = label;
//        }
//    }
//
//    public static class NovelsBean {
//        /**
//         * book_id : 13
//         * book_name :  Istri lembut di Pernikahan Kilat Tuan He
//         * label : ["Perkotaan","Romantis"]
//         * oblong_cover : https://img.pulaukomik.com/book/15759497236747.png
//         */
//
//        private int book_id;
//        private String book_name;
//        private String oblong_cover;
//        private List<String> label;
//
//        public int getBook_id() {
//            return book_id;
//        }
//
//        public void setBook_id(int book_id) {
//            this.book_id = book_id;
//        }
//
//        public String getBook_name() {
//            return book_name;
//        }
//
//        public void setBook_name(String book_name) {
//            this.book_name = book_name;
//        }
//
//        public String getOblong_cover() {
//            return oblong_cover;
//        }
//
//        public void setOblong_cover(String oblong_cover) {
//            this.oblong_cover = oblong_cover;
//        }
//
//        public List<String> getLabel() {
//            return label;
//        }
//
//        public void setLabel(List<String> label) {
//            this.label = label;
//        }
//    }

    public static class HomeCommonBean {
        /**
         * book_id : 13
         * book_name :  Istri lembut di Pernikahan Kilat Tuan He
         * label : ["Perkotaan","Romantis"]
         * oblong_cover : https://img.pulaukomik.com/book/15759497236747.png
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
