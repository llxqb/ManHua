package com.shushan.manhua.entity.response;

import com.shushan.manhua.entity.CommentBean;

import java.util.List;

public class BookDetailInfoResponse {
    /**
     * detail : {"book_id":1,"book_name":"大国工程","des":"余庆阳一个搬砖二十年的老工程，梦回世纪之交，海河大学毕业，接老爸的班继续搬砖。","author":"和光万物","collect":0,"label":["重生过去","畅想未来"],"comment_count":0,"detail_cover":"","cost":0,"vip_cost":0,"state":0}
     * comment : [{"comment_id":1,"user_id":1,"book_id":1,"content":"这本书真棒！","pics":[],"comment_time":1574077027,"like":0,"name":"测试","head_portrait":"http://www.baidu.com","is_like":0,"review":[{"review_id":1,"comment_id":1,"content":"这本书真棒！","pics":["https://caricature.oss-ap-southeast-5.aliyuncs.com/comment/20191118/5dd26de2b9ca8.png"],"review_time":1574077894,"user_id":1,"be_user_id":1,"user_name":"测试","user_head_portrait":"http://www.baidu.com","be_user_name":"测试","be_user_head_portrait":"http://www.baidu.com","like":0}],"review_count":1}]
     * history : {}
     */

    private DetailBean detail;
    private HistoryBean history;
    private List<CommentBean> comment;

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public HistoryBean getHistory() {
        return history;
    }

    public void setHistory(HistoryBean history) {
        this.history = history;
    }

    public List<CommentBean> getComment() {
        return comment;
    }

    public void setComment(List<CommentBean> comment) {
        this.comment = comment;
    }

    public static class DetailBean {
        /**
         * book_id : 1
         * book_name : 大国工程
         * des : 余庆阳一个搬砖二十年的老工程，梦回世纪之交，海河大学毕业，接老爸的班继续搬砖。
         * author : 和光万物
         * collect : 0
         * label : ["重生过去","畅想未来"]
         * comment_count : 0
         * detail_cover :
         * cost : 0
         * vip_cost : 0
         * state : 0
         */

        private int book_id;
        private String book_name;
        private String des;
        private String author;
        private int collect;
        private int comment_count;
        private String detail_cover;
        private int cost;
        private int vip_cost;

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

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getCollect() {
            return collect;
        }

        public void setCollect(int collect) {
            this.collect = collect;
        }

        public int getComment_count() {
            return comment_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public String getDetail_cover() {
            return detail_cover;
        }

        public void setDetail_cover(String detail_cover) {
            this.detail_cover = detail_cover;
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
    }

    public static class HistoryBean {
    }


}
