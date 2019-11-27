package com.shushan.manhua.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BookShelfResponse implements Parcelable {
    /**
     * last_read : {}
     * bookrack : []
     */

    private LastReadBean last_read;

    protected BookShelfResponse(Parcel in) {
    }

    public static final Creator<BookShelfResponse> CREATOR = new Creator<BookShelfResponse>() {
        @Override
        public BookShelfResponse createFromParcel(Parcel in) {
            return new BookShelfResponse(in);
        }

        @Override
        public BookShelfResponse[] newArray(int size) {
            return new BookShelfResponse[size];
        }
    };

    public LastReadBean getLast_read() {
        return last_read;
    }

    public void setLast_read(LastReadBean last_read) {
        this.last_read = last_read;
    }

    public static class LastReadBean {
    }

    private List<BookrackBean> bookrack;

    public List<BookrackBean> getBookrack() {
        return bookrack;
    }

    public void setBookrack(List<BookrackBean> bookrack) {
        this.bookrack = bookrack;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


    public static class BookrackBean implements Parcelable{
        public BookrackBean() {
        }

        /**
         * book_id : 1
         * book_name : 大国工程
         * detail_cover :
         * comment_count : 0
         * like : 0
         * type : 1
         * create_time : 1574142425
         * catalogue_id : 2
         * catalogue_name : 第二话 青铜时代
         */

        private int book_id;
        private String book_name;
        private String detail_cover;
        private int comment_count;
        private int like;
        private int type;
        private int create_time;
        private int catalogue_id;
        private String catalogue_name;

        /**
         * 点我显示更多
         */
        public boolean isMore;
        /**
         * 长按删除，是否选择
         */
        public boolean isCheck;

        protected BookrackBean(Parcel in) {
            book_id = in.readInt();
            book_name = in.readString();
            detail_cover = in.readString();
            comment_count = in.readInt();
            like = in.readInt();
            type = in.readInt();
            create_time = in.readInt();
            catalogue_id = in.readInt();
            catalogue_name = in.readString();
            isMore = in.readByte() != 0;
            isCheck = in.readByte() != 0;
        }

        public static final Creator<BookrackBean> CREATOR = new Creator<BookrackBean>() {
            @Override
            public BookrackBean createFromParcel(Parcel in) {
                return new BookrackBean(in);
            }

            @Override
            public BookrackBean[] newArray(int size) {
                return new BookrackBean[size];
            }
        };

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

        public String getDetail_cover() {
            return detail_cover;
        }

        public void setDetail_cover(String detail_cover) {
            this.detail_cover = detail_cover;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(book_id);
            dest.writeString(book_name);
            dest.writeString(detail_cover);
            dest.writeInt(comment_count);
            dest.writeInt(like);
            dest.writeInt(type);
            dest.writeInt(create_time);
            dest.writeInt(catalogue_id);
            dest.writeString(catalogue_name);
            dest.writeByte((byte) (isMore ? 1 : 0));
            dest.writeByte((byte) (isCheck ? 1 : 0));
        }
    }


}
