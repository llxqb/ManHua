package com.shushan.manhua.entity.response;

import com.shushan.manhua.entity.CommentBean;

import java.util.List;

public class BookDetailInfoResponse {


    /**
     * last_catalogue_id : 1
     * detail : {"book_id":1,"book_name":"大国工程","des":"余庆阳一个搬砖二十年的老工程，梦回世纪之交，海河大学毕业，接老爸的班继续搬砖。","author":"和光万物","collect":9,"label":["重生过去","畅想未来"],"comment_count":10,"detail_cover":"","cost":0,"vip_cost":0,"state":1}
     * comment : [{"comment_id":1,"user_id":1,"book_id":1,"content":"这本书真棒！","pics":[],"comment_time":1574077027,"like":5,"name":"测试","head_portrait":"http://www.baidu.com","is_like":0,"review":[{"review_id":1,"comment_id":1,"content":"这本书真棒！","pics":["https://caricature.oss-ap-southeast-5.aliyuncs.com/comment/20191118/5dd26de2b9ca8.png"],"review_time":1574077894,"user_id":1,"be_user_id":1,"user_name":"测试","user_head_portrait":"http://www.baidu.com","be_user_name":"测试","be_user_head_portrait":"http://www.baidu.com","like":1}],"review_count":1},{"comment_id":3,"user_id":5,"book_id":1,"content":"gshshsjs","pics":[],"comment_time":1574823457,"like":4,"name":"立刘","head_portrait":"https://img.pulaukomik.com/cover/20191126/5ddceda1625c9.jpg","is_like":0,"review":[],"review_count":0},{"comment_id":4,"user_id":5,"book_id":1,"content":"shhshs","pics":[null,null,null,null],"comment_time":1574824546,"like":4,"name":"立刘","head_portrait":"https://img.pulaukomik.com/cover/20191126/5ddceda1625c9.jpg","is_like":0,"review":[],"review_count":0}]
     * history : {"id":9,"user_id":19,"book_id":1,"catalogue_id":1,"create_time":1575427677,"type":1,"bean":0,"state":1,"deletetime":0,"words":575,"sort":1}
     */

    private int last_catalogue_id;
    private DetailBean detail;
    private HistoryBean history;
    private List<CommentBean> comment;

    public int getLast_catalogue_id() {
        return last_catalogue_id;
    }

    public void setLast_catalogue_id(int last_catalogue_id) {
        this.last_catalogue_id = last_catalogue_id;
    }

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
         * collect : 9
         * label : ["重生过去","畅想未来"]
         * comment_count : 10
         * detail_cover :
         * cost : 0
         * vip_cost : 0
         * state : 1
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
        private int state;
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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public List<String> getLabel() {
            return label;
        }

        public void setLabel(List<String> label) {
            this.label = label;
        }
    }

    public static class HistoryBean {
        /**
         * id : 9
         * user_id : 19
         * book_id : 1
         * catalogue_id : 1
         * create_time : 1575427677
         * type : 1
         * bean : 0
         * state : 1
         * deletetime : 0
         * words : 575
         * sort : 1
         */

        private int id;
        private int user_id;
        private int book_id;
        private int catalogue_id;
        private int create_time;
        private int type;
        private int bean;
        private int state;
        private int deletetime;
        private int words;
        private int sort;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getCatalogue_id() {
            return catalogue_id;
        }

        public void setCatalogue_id(int catalogue_id) {
            this.catalogue_id = catalogue_id;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getBean() {
            return bean;
        }

        public void setBean(int bean) {
            this.bean = bean;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getDeletetime() {
            return deletetime;
        }

        public void setDeletetime(int deletetime) {
            this.deletetime = deletetime;
        }

        public int getWords() {
            return words;
        }

        public void setWords(int words) {
            this.words = words;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }

}
