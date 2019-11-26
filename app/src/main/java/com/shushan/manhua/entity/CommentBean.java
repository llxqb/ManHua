package com.shushan.manhua.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public  class CommentBean implements Parcelable {
    /**
     * comment_id : 1
     * user_id : 1
     * book_id : 1
     * content : 这本书真棒！
     * pics : []
     * comment_time : 1574077027
     * like : 0
     * name : 测试
     * head_portrait : http://www.baidu.com
     * is_like : 0
     * review : [{"review_id":1,"comment_id":1,"content":"这本书真棒！","pics":["https://caricature.oss-ap-southeast-5.aliyuncs.com/comment/20191118/5dd26de2b9ca8.png"],"review_time":1574077894,"user_id":1,"be_user_id":1,"user_name":"测试","user_head_portrait":"http://www.baidu.com","be_user_name":"测试","be_user_head_portrait":"http://www.baidu.com","like":0}]
     * review_count : 1
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
    private List<String> pics;
    private List<ReviewBean> review;

    protected CommentBean(Parcel in) {
        comment_id = in.readInt();
        user_id = in.readInt();
        book_id = in.readInt();
        content = in.readString();
        comment_time = in.readInt();
        like = in.readInt();
        name = in.readString();
        head_portrait = in.readString();
        is_like = in.readInt();
        review_count = in.readInt();
        pics = in.createStringArrayList();
    }

    public static final Creator<CommentBean> CREATOR = new Creator<CommentBean>() {
        @Override
        public CommentBean createFromParcel(Parcel in) {
            return new CommentBean(in);
        }

        @Override
        public CommentBean[] newArray(int size) {
            return new CommentBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(comment_id);
        dest.writeInt(user_id);
        dest.writeInt(book_id);
        dest.writeString(content);
        dest.writeInt(comment_time);
        dest.writeInt(like);
        dest.writeString(name);
        dest.writeString(head_portrait);
        dest.writeInt(is_like);
        dest.writeInt(review_count);
        dest.writeStringList(pics);
    }

    public static class ReviewBean {
        /**
         * review_id : 1
         * comment_id : 1
         * content : 这本书真棒！
         * pics : ["https://caricature.oss-ap-southeast-5.aliyuncs.com/comment/20191118/5dd26de2b9ca8.png"]
         * review_time : 1574077894
         * user_id : 1
         * be_user_id : 1
         * user_name : 测试
         * user_head_portrait : http://www.baidu.com
         * be_user_name : 测试
         * be_user_head_portrait : http://www.baidu.com
         * like : 0
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

        public List<String> getPics() {
            return pics;
        }

        public void setPics(List<String> pics) {
            this.pics = pics;
        }
    }
}
