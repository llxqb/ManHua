package com.shushan.manhua.entity.response;

import java.util.List;

public class CommentDetailResponse {


    /**
     * comment_id : 8
     * user_id : 10
     * book_id : 1
     * content : Dydy app
     * pics : ["https://img.pulaukomik.com/comment/20191128/5ddf348409b3b.png"]
     * comment_time : 1574909250
     * like : 0
     * name : ruzhi shu
     * type : 1
     * head_portrait : https://img.pulaukomik.com/cover/20191127/5dde3ccc6e141.jpg
     * is_like : 0
     * review : [{"review_id":4,"comment_id":8,"content":"@测试：yikes","pics":[],"review_time":1574910736,"user_id":10,"be_user_id":10,"user_name":"ruzhi shu","user_head_portrait":"https://img.pulaukomik.com/cover/20191127/5dde3ccc6e141.jpg","be_user_name":"ruzhi shu","be_user_head_portrait":"https://img.pulaukomik.com/cover/20191127/5dde3ccc6e141.jpg","like":0,"is_like":0},{"review_id":3,"comment_id":8,"content":"Ggghhuh","pics":[],"review_time":1574910121,"user_id":10,"be_user_id":10,"user_name":"ruzhi shu","user_head_portrait":"https://img.pulaukomik.com/cover/20191127/5dde3ccc6e141.jpg","be_user_name":"ruzhi shu","be_user_head_portrait":"https://img.pulaukomik.com/cover/20191127/5dde3ccc6e141.jpg","like":0,"is_like":0},{"review_id":2,"comment_id":8,"content":"app crashes","pics":[],"review_time":1574909927,"user_id":10,"be_user_id":10,"user_name":"ruzhi shu","user_head_portrait":"https://img.pulaukomik.com/cover/20191127/5dde3ccc6e141.jpg","be_user_name":"ruzhi shu","be_user_head_portrait":"https://img.pulaukomik.com/cover/20191127/5dde3ccc6e141.jpg","like":0,"is_like":0}]
     */

    private int comment_id;
    private int user_id;
    private int book_id;
    private String content;
    private int comment_time;
    private int like;
    private String name;
    private int type;
    private String head_portrait;
    private int is_like;
    private List<String> pics;
    private List<ReviewBean> review;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public List<ReviewBean> getReview() {
        return review;
    }

    public void setReview(List<ReviewBean> review) {
        this.review = review;
    }

    public static class ReviewBean {
        /**
         * review_id : 4
         * comment_id : 8
         * content : @测试：yikes
         * pics : []
         * review_time : 1574910736
         * user_id : 10
         * be_user_id : 10
         * user_name : ruzhi shu
         * user_head_portrait : https://img.pulaukomik.com/cover/20191127/5dde3ccc6e141.jpg
         * be_user_name : ruzhi shu
         * be_user_head_portrait : https://img.pulaukomik.com/cover/20191127/5dde3ccc6e141.jpg
         * like : 0
         * is_like : 0
         */

        private int review_id;
        private int comment_id;
        private String content;
        private int review_time;
        private int user_id;
        private int be_user_id;
        private String user_name;
        private String user_head_portrait;
        private String be_user_name;
        private String be_user_head_portrait;
        private int like;
        private int is_like;
        private List<String> pics;

        public int getReview_id() {
            return review_id;
        }

        public void setReview_id(int review_id) {
            this.review_id = review_id;
        }

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getReview_time() {
            return review_time;
        }

        public void setReview_time(int review_time) {
            this.review_time = review_time;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getBe_user_id() {
            return be_user_id;
        }

        public void setBe_user_id(int be_user_id) {
            this.be_user_id = be_user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_head_portrait() {
            return user_head_portrait;
        }

        public void setUser_head_portrait(String user_head_portrait) {
            this.user_head_portrait = user_head_portrait;
        }

        public String getBe_user_name() {
            return be_user_name;
        }

        public void setBe_user_name(String be_user_name) {
            this.be_user_name = be_user_name;
        }

        public String getBe_user_head_portrait() {
            return be_user_head_portrait;
        }

        public void setBe_user_head_portrait(String be_user_head_portrait) {
            this.be_user_head_portrait = be_user_head_portrait;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public int getIs_like() {
            return is_like;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }

        public List<String> getPics() {
            return pics;
        }

        public void setPics(List<String> pics) {
            this.pics = pics;
        }
    }
}
