package com.shushan.manhua.entity.response;

import com.shushan.manhua.entity.BannerBean;
import com.shushan.manhua.entity.CommentBean;
import com.shushan.manhua.entity.RecommendBean;

import java.util.List;

public class ReadingInfoResponse {


    /**
     * commend : [{"book_id":1,"book_name":"大国工程","label":["重生过去","畅想未来"],"oblong_cover":""}]
     * catalogue : {"catalogue_id":1,"catalogue_name":"第一话 纪元时代","catalogue_content":["https://caricature.oss-ap-southeast-5.aliyuncs.com/book/1.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/2.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/3.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/4.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/5.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/6.jpg"],"comment_count":1,"like":5,"change_like":2,"type":1,"width":"0","height":"0","is_like":1,"state":1,"count":1}
     * banner : [{"banner_name":"测试banner图","banner_pic":"http://www.baidu.com","banner_cate":1,"banner_url":"http://www.baidu.com","custom":""}]
     * comment : [{"comment_id":2,"user_id":1,"book_id":1,"content":"太好了！","pics":[],"comment_time":1574077027,"like":1,"name":"测试","head_portrait":"http://www.baidu.com","is_like":0,"review":[],"review_count":0}]
     */

    private CatalogueBean catalogue;
    private List<RecommendBean> commend;
    private List<BannerBean> banner;
    private List<CommentBean> comment;

    public CatalogueBean getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(CatalogueBean catalogue) {
        this.catalogue = catalogue;
    }

    public List<RecommendBean> getCommend() {
        return commend;
    }

    public void setCommend(List<RecommendBean> commend) {
        this.commend = commend;
    }

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public static class CatalogueBean {
        /**
         * catalogue_id : 1
         * catalogue_name : 第一话 纪元时代
         * catalogue_content : ["https://caricature.oss-ap-southeast-5.aliyuncs.com/book/1.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/2.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/3.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/4.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/5.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/6.jpg"]
         * comment_count : 1
         * like : 5
         * change_like : 2
         * type : 1
         * width : 0
         * height : 0
         * is_like : 1
         * state : 1
         * count : 1
         */

        private int catalogue_id;
        private String catalogue_name;
        private int comment_count;
        private int like;
        private int change_like;
        private int type;
        private String width;
        private String height;
        private int is_like;
        private int state;
        private int count;
        private List<String> catalogue_content;

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

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public int getIs_like() {
            return is_like;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<String> getCatalogue_content() {
            return catalogue_content;
        }

        public void setCatalogue_content(List<String> catalogue_content) {
            this.catalogue_content = catalogue_content;
        }
    }




}
