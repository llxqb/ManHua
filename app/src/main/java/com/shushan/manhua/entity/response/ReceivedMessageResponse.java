package com.shushan.manhua.entity.response;

import java.util.List;

public class ReceivedMessageResponse {


    /**
     * error : 0
     * msg : success
     * data : [{"comment_id":17,"book_id":1,"catalogue_id":0,"content":"haha","pics":[],"like":0,"book_name":"大国工程","detail_cover":"","catalogue_name":"","be_user_id":19,"reply_id":17,"name":"bee T","head_portrait":"https://img.pulaukomik.com/book/20191202/5de4bffdc40da.png","review_time":1575516903,"user_id":18,"review_id":19,"comment":{"content":"三十岁","pics":[],"name":"bee T","be_name":"Nuage Laboratoire"}},{"comment_id":8,"book_id":1,"catalogue_id":0,"content":"123123","pics":[],"like":0,"book_name":"大国工程","detail_cover":"","catalogue_name":"","be_user_id":19,"reply_id":8,"name":"bee T","head_portrait":"https://img.pulaukomik.com/book/20191202/5de4bffdc40da.png","review_time":1575516434,"user_id":18,"review_id":18,"comment":{"pics":{}}}]
     */

    private int error;
    private String msg;
    private List<DataBean> data;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * comment_id : 17
         * book_id : 1
         * catalogue_id : 0
         * content : haha
         * pics : []
         * like : 0
         * book_name : 大国工程
         * detail_cover :
         * catalogue_name :
         * be_user_id : 19
         * reply_id : 17
         * name : bee T
         * head_portrait : https://img.pulaukomik.com/book/20191202/5de4bffdc40da.png
         * review_time : 1575516903
         * user_id : 18
         * review_id : 19
         * comment : {"content":"三十岁","pics":[],"name":"bee T","be_name":"Nuage Laboratoire"}
         */

        private int comment_id;
        private int book_id;
        private int catalogue_id;
        private String content;
        private int like;
        private String book_name;
        private String detail_cover;
        private String catalogue_name;
        private int be_user_id;
        private int reply_id;
        private String name;
        private String head_portrait;
        private int review_time;
        private int user_id;
        private int review_id;
        private CommentBean comment;
        private List<?> pics;

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public String getBook_name() {
            return book_name;
        }

        public void setBook_name(String book_name) {
            this.book_name = book_name;
        }

        public String getDetail_cover() {
            return detail_cover;
        }

        public void setDetail_cover(String detail_cover) {
            this.detail_cover = detail_cover;
        }

        public String getCatalogue_name() {
            return catalogue_name;
        }

        public void setCatalogue_name(String catalogue_name) {
            this.catalogue_name = catalogue_name;
        }

        public int getBe_user_id() {
            return be_user_id;
        }

        public void setBe_user_id(int be_user_id) {
            this.be_user_id = be_user_id;
        }

        public int getReply_id() {
            return reply_id;
        }

        public void setReply_id(int reply_id) {
            this.reply_id = reply_id;
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

        public int getReview_id() {
            return review_id;
        }

        public void setReview_id(int review_id) {
            this.review_id = review_id;
        }

        public CommentBean getComment() {
            return comment;
        }

        public void setComment(CommentBean comment) {
            this.comment = comment;
        }

        public List<?> getPics() {
            return pics;
        }

        public void setPics(List<?> pics) {
            this.pics = pics;
        }

        public static class CommentBean {
            /**
             * content : 三十岁
             * pics : []
             * name : bee T
             * be_name : Nuage Laboratoire
             */

            private String content;
            private String name;
            private String be_name;
            private List<?> pics;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBe_name() {
                return be_name;
            }

            public void setBe_name(String be_name) {
                this.be_name = be_name;
            }

            public List<?> getPics() {
                return pics;
            }

            public void setPics(List<?> pics) {
                this.pics = pics;
            }
        }
    }
}
