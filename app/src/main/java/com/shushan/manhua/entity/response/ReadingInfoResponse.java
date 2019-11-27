package com.shushan.manhua.entity.response;

import java.util.List;

public class ReadingInfoResponse {


    /**
     * commend : [{"book_id":1,"book_name":"大国工程","label":["重生过去","畅想未来"],"oblong_cover":""}]
     * catalogue : {"catalogue_id":1,"catalogue_name":"第一话 纪元时代","catalogue_content":["https://caricature.oss-ap-southeast-5.aliyuncs.com/book/1.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/2.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/3.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/4.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/5.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/6.jpg"],"comment_count":1,"like":5,"change_like":2,"type":1,"width":"0","height":"0","is_like":1,"state":1,"count":1}
     * banner : [{"banner_name":"测试banner图","banner_pic":"http://www.baidu.com","banner_cate":1,"banner_url":"http://www.baidu.com","custom":""}]
     * comment : [{"comment_id":2,"user_id":1,"book_id":1,"content":"太好了！","pics":[],"comment_time":1574077027,"like":1,"name":"测试","head_portrait":"http://www.baidu.com","is_like":0,"review":[],"review_count":0}]
     */

    private CatalogueBean catalogue;
    private List<CommendBean> commend;
    private List<BannerBean> banner;
    private List<CommentBean> comment;

    public CatalogueBean getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(CatalogueBean catalogue) {
        this.catalogue = catalogue;
    }

    public List<CommendBean> getCommend() {
        return commend;
    }

    public void setCommend(List<CommendBean> commend) {
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

    public static class CommendBean {
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

    public static class CommentBean {
        /**
         * comment_id : 2
         * user_id : 1
         * book_id : 1
         * content : 太好了！
         * pics : []
         * comment_time : 1574077027
         * like : 1
         * name : 测试
         * head_portrait : http://www.baidu.com
         * is_like : 0
         * review : []
         * review_count : 0
         */

        private int comment_id;
        private int user_id;
        private int book_id;
        private String content;
        private int comment_time;
        private int like;
        private String name;
        private String head_portrait;
        private int is_like;
        private int review_count;
        private List<?> pics;
        private List<?> review;

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getComment_time() {
            return comment_time;
        }

        public void setComment_time(int comment_time) {
            this.comment_time = comment_time;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHead_portrait() {
            return head_portrait;
        }

        public void setHead_portrait(String head_portrait) {
            this.head_portrait = head_portrait;
        }

        public int getIs_like() {
            return is_like;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }

        public int getReview_count() {
            return review_count;
        }

        public void setReview_count(int review_count) {
            this.review_count = review_count;
        }

        public List<?> getPics() {
            return pics;
        }

        public void setPics(List<?> pics) {
            this.pics = pics;
        }

        public List<?> getReview() {
            return review;
        }

        public void setReview(List<?> review) {
            this.review = review;
        }
    }
}
