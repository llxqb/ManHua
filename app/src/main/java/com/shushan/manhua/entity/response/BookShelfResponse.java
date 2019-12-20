package com.shushan.manhua.entity.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class BookShelfResponse  {


    /**
     * last_read : {"id":48,"user_id":54,"book_id":5,"catalogue_id":1,"create_time":1575685261,"type":1,"bean":0,"state":1,"deletetime":0,"book_name":"Hitungan mundur 7 hari ","oblong_cover":"https://img.pulaukomik.com/book/15756236893514.png","words":96,"catalogue_name":"第一话 纪元时代","sort":1,"residue_words":95}
     * bookrack : [{"book_id":1,"book_name":"大国工程","detail_cover":"","comment_count":13,"like":0,"type":1,"create_time":1574142425,"catalogue_id":2,"catalogue_name":"第二话 青铜时代","is_like":0},{"book_id":4,"book_name":"倒数七天","detail_cover":"https://img.pulaukomik.com/book/15755934254648.png","comment_count":4,"like":0,"type":1,"create_time":1575613771,"catalogue_id":10,"catalogue_name":"第五话","is_like":0},{"book_id":3,"book_name":"测试网速zy","detail_cover":"https://img.pulaukomik.com/book/15755933491503.png","comment_count":20000,"like":100000,"type":2,"create_time":1575593984,"catalogue_id":4,"catalogue_name":"第二话 说啥呢","is_like":0},{"book_id":5,"book_name":"Hitungan mundur 7 hari ","detail_cover":"https://img.pulaukomik.com/book/15756236907282.png","comment_count":1,"like":200,"type":2,"create_time":1575689414,"catalogue_id":40,"catalogue_name":"Chapter 30 Efek kupu-kupu","is_like":0}]
     */

    private LastReadBean last_read;
    private List<BookrackBean> bookrack;

    public LastReadBean getLast_read() {
        return last_read;
    }

    public void setLast_read(LastReadBean last_read) {
        this.last_read = last_read;
    }

    public List<BookrackBean> getBookrack() {
        return bookrack;
    }

    public void setBookrack(List<BookrackBean> bookrack) {
        this.bookrack = bookrack;
    }

    public static class LastReadBean {
        /**
         * id : 48
         * user_id : 54
         * book_id : 5
         * catalogue_id : 1
         * create_time : 1575685261
         * type : 1
         * bean : 0
         * state : 1
         * deletetime : 0
         * book_name : Hitungan mundur 7 hari
         * oblong_cover : https://img.pulaukomik.com/book/15756236893514.png
         * words : 96
         * catalogue_name : 第一话 纪元时代
         * sort : 1
         * genre: 1  类型1漫画（默认）2小说
         * residue_words : 95
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
        private String book_name;
        private String oblong_cover;
        private int words;
        private String catalogue_name;
        private int sort;
        private int genre;
        private int residue_words;

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

        public int getWords() {
            return words;
        }

        public void setWords(int words) {
            this.words = words;
        }

        public String getCatalogue_name() {
            return catalogue_name;
        }

        public void setCatalogue_name(String catalogue_name) {
            this.catalogue_name = catalogue_name;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getGenre() {
            return genre;
        }

        public void setGenre(int genre) {
            this.genre = genre;
        }

        public int getResidue_words() {
            return residue_words;
        }

        public void setResidue_words(int residue_words) {
            this.residue_words = residue_words;
        }
    }

    public static class BookrackBean implements Parcelable {
        /**
         * book_id : 1
         * book_name : 大国工程
         * detail_cover :
         * comment_count : 13
         * like : 0
         * type : 1        1连载中2完结
         * create_time : 1574142425
         * catalogue_id : 2
         * catalogue_name : 第二话 青铜时代
         * is_like : 0
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
        private int is_like;
        public boolean isMore;
        public boolean isCheck;
        private int genre;

        protected BookrackBean(Parcel in) {
            book_id = in.readInt();
            book_name = in.readString();
            detail_cover = in.readString();
            comment_count = in.readInt();
            like = in.readInt();
            type = in.readInt();
            genre = in.readInt();
            create_time = in.readInt();
            catalogue_id = in.readInt();
            catalogue_name = in.readString();
            is_like = in.readInt();
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

        public BookrackBean() {
        }

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

        public int getGenre() {
            return genre;
        }

        public void setGenre(int genre) {
            this.genre = genre;
        }

        public String getCatalogue_name() {
            return catalogue_name;
        }

        public void setCatalogue_name(String catalogue_name) {
            this.catalogue_name = catalogue_name;
        }

        public int getIs_like() {
            return is_like;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
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
            dest.writeInt(genre);
            dest.writeInt(create_time);
            dest.writeInt(catalogue_id);
            dest.writeString(catalogue_name);
            dest.writeInt(is_like);
            dest.writeByte((byte) (isMore ? 1 : 0));
            dest.writeByte((byte) (isCheck ? 1 : 0));
        }
    }
}
