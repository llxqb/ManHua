package com.shushan.manhua.entity.response;

import com.shushan.manhua.entity.BannerBean;
import com.shushan.manhua.entity.CommentBean;
import com.shushan.manhua.entity.RecommendBean;

import java.util.List;

public class ReadingInfoResponse {


    /**
     * commend : [{"book_id":1,"book_name":"大国工程","label":["重生过去","畅想未来"],"oblong_cover":""}]
     * catalogue : {"catalogue_id":1,"catalogue_name":"第一话 纪元时代","catalogue_content":["https://caricature.oss-ap-southeast-5.aliyuncs.com/book/1.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/2.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/3.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/4.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/5.jpg","https://caricature.oss-ap-southeast-5.aliyuncs.com/book/6.jpg"],"comment_count":3,"like":8,"change_like":2,"type":0,"sort":1,"cost":0,"vip_cost":0,"is_like":0,"state":1,"count":3,"next_catalogue_id":2,"pre_catalogue_id":0}
     * banner : [{"banner_name":"测试banner图","banner_pic":"http://www.baidu.com","banner_cate":1,"banner_url":"http://www.baidu.com","custom":""}]
     * comment : [{"comment_id":2,"user_id":1,"book_id":1,"content":"太好了！","pics":[],"comment_time":1574077027,"like":2,"name":"测试","head_portrait":"http://www.baidu.com","is_like":0,"review":[],"review_count":0},{"comment_id":9,"user_id":10,"book_id":1,"content":"App does exactly","pics":[],"comment_time":1574913492,"like":0,"name":"zy1234","head_portrait":"https://img.pulaukomik.com/book/20191129/5de0b686ea304.png","is_like":0,"review":[],"review_count":0},{"comment_id":10,"user_id":10,"book_id":1,"content":"Dydy day would","pics":[],"comment_time":1574913527,"like":0,"name":"zy1234","head_portrait":"https://img.pulaukomik.com/book/20191129/5de0b686ea304.png","is_like":0,"review":[],"review_count":0}]
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
         * catalogue_id : 10
         * catalogue_name : 第五话
         * catalogue_content : [{"url":"https://img.pulaukomik.com/catalogue/15756136702771.png","width":1000,"height":9646},{"url":"https://img.pulaukomik.com/catalogue/15756136927292.png","width":1000,"height":8343},{"url":"https://img.pulaukomik.com/catalogue/15756137042182.png","width":1000,"height":8730},{"url":"https://img.pulaukomik.com/catalogue/15756137138834.png","width":1000,"height":9800},{"url":"https://img.pulaukomik.com/catalogue/15756137277419.png","width":1000,"height":9200},{"url":"https://img.pulaukomik.com/catalogue/15756137455479.png","width":1000,"height":9900}]
         * comment_count : 0
         * like : 30
         * change_like : 30
         * type : 1
         * sort : 5
         * cost : 4
         * vip_cost : 3
         * is_like : 0
         * state : 1
         * count : 0
         * next_catalogue_id : 0
         * pre_catalogue_id : 5
         */

        private int catalogue_id;
        private String catalogue_name;
        private int comment_count;
        private int like;
        private int change_like;
        private int type;
        private int sort;
        private int cost;
        private int vip_cost;
        private int userbean;
        private int is_like;
        private int state;
        private int count;
        private int next_catalogue_id;
        private int pre_catalogue_id;
        private List<CatalogueContentBean> catalogue_content;

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

        public int getUserbean() {
            return userbean;
        }

        public void setUserbean(int userbean) {
            this.userbean = userbean;
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

        public List<CatalogueContentBean> getCatalogue_content() {
            return catalogue_content;
        }

        public void setCatalogue_content(List<CatalogueContentBean> catalogue_content) {
            this.catalogue_content = catalogue_content;
        }

        public static class CatalogueContentBean {
            /**
             * url : https://img.pulaukomik.com/catalogue/15756136702771.png
             * width : 1000
             * height : 9646
             */

            private String url;
            private int width;
            private int height;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }


}
