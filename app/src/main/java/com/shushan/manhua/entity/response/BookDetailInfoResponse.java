package com.shushan.manhua.entity.response;

import com.shushan.manhua.entity.CommentBean;

import java.util.List;

public class BookDetailInfoResponse {


    /**
     * last_catalogue_id : 2
     * detail : {"book_id":5,"book_name":"Hitungan mundur 7 hari ","des":"Hai！ Jika hidupmu tersisa 7 hari. apa hal yang ingin kamu lakukan？Dunia akhirat didepartemen kematian mengumumkan diadakannya percobaan pemberitahuan kematian, Dalam waktu pendek 7 hari ini, tidak peduli kamu pernah hidup di puncak dunia, atau sedamg berjuang melawan kehidupan. di dalam hati pasti selalu ada rintangan, ada kerinduan, ada penyesalan....Sebenarnya tidak penting bagaimana meninggalnya; yang penting adalah bagaimana menjalani hidup","author":"ake","collect":3002,"label":[" Fantasi","Romantis"],"comment_count":1,"detail_cover":"https://img.pulaukomik.com/book/15756236907282.png","cost":3,"vip_cost":2,"state":0}
     * comment : [{"comment_id":29,"user_id":57,"book_id":5,"content":"hhhhh","pics":[],"comment_time":1575683324,"like":0,"name":"张怡","head_portrait":"https://img.pulaukomik.com/cover/20191207/5deb010029604.jpg","is_like":0,"review":[],"review_count":0}]
     * history : {"id":49,"user_id":54,"book_id":5,"catalogue_id":2,"create_time":1575685416,"type":1,"bean":0,"state":1,"deletetime":0,"words":96,"sort":2}
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
         * book_id : 5
         * book_name : Hitungan mundur 7 hari
         * des : Hai！ Jika hidupmu tersisa 7 hari. apa hal yang ingin kamu lakukan？Dunia akhirat didepartemen kematian mengumumkan diadakannya percobaan pemberitahuan kematian, Dalam waktu pendek 7 hari ini, tidak peduli kamu pernah hidup di puncak dunia, atau sedamg berjuang melawan kehidupan. di dalam hati pasti selalu ada rintangan, ada kerinduan, ada penyesalan....Sebenarnya tidak penting bagaimana meninggalnya; yang penting adalah bagaimana menjalani hidup
         * author : ake
         * collect : 3002
         * label : [" Fantasi","Romantis"]
         * comment_count : 1
         * detail_cover : https://img.pulaukomik.com/book/15756236907282.png
         * cost : 3
         * vip_cost : 2
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
         * id : 49
         * user_id : 54
         * book_id : 5
         * catalogue_id : 2
         * create_time : 1575685416
         * type : 1
         * bean : 0
         * state : 1
         * deletetime : 0
         * words : 96
         * sort : 2
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

//    public static class CommentBean {
//        /**
//         * comment_id : 29
//         * user_id : 57
//         * book_id : 5
//         * content : hhhhh
//         * pics : []
//         * comment_time : 1575683324
//         * like : 0
//         * name : 张怡
//         * head_portrait : https://img.pulaukomik.com/cover/20191207/5deb010029604.jpg
//         * is_like : 0
//         * review : []
//         * review_count : 0
//         */
//
//        private int comment_id;
//        private int user_id;
//        private int book_id;
//        private String content;
//        private int comment_time;
//        private int like;
//        private String name;
//        private String head_portrait;
//        private int is_like;
//        private int review_count;
//        private List<?> pics;
//        private List<?> review;
//
//        public int getComment_id() {
//            return comment_id;
//        }
//
//        public void setComment_id(int comment_id) {
//            this.comment_id = comment_id;
//        }
//
//        public int getUser_id() {
//            return user_id;
//        }
//
//        public void setUser_id(int user_id) {
//            this.user_id = user_id;
//        }
//
//        public int getBook_id() {
//            return book_id;
//        }
//
//        public void setBook_id(int book_id) {
//            this.book_id = book_id;
//        }
//
//        public String getContent() {
//            return content;
//        }
//
//        public void setContent(String content) {
//            this.content = content;
//        }
//
//        public int getComment_time() {
//            return comment_time;
//        }
//
//        public void setComment_time(int comment_time) {
//            this.comment_time = comment_time;
//        }
//
//        public int getLike() {
//            return like;
//        }
//
//        public void setLike(int like) {
//            this.like = like;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getHead_portrait() {
//            return head_portrait;
//        }
//
//        public void setHead_portrait(String head_portrait) {
//            this.head_portrait = head_portrait;
//        }
//
//        public int getIs_like() {
//            return is_like;
//        }
//
//        public void setIs_like(int is_like) {
//            this.is_like = is_like;
//        }
//
//        public int getReview_count() {
//            return review_count;
//        }
//
//        public void setReview_count(int review_count) {
//            this.review_count = review_count;
//        }
//
//        public List<?> getPics() {
//            return pics;
//        }
//
//        public void setPics(List<?> pics) {
//            this.pics = pics;
//        }
//
//        public List<?> getReview() {
//            return review;
//        }
//
//        public void setReview(List<?> review) {
//            this.review = review;
//        }
//    }
}
