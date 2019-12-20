package com.shushan.manhua.entity;

import java.util.List;

public class RecommendBean {
    /**
     * book_id : 1
     * book_name : 大国工程
     * square_cover :
     * label : ["重生过去","畅想未来"]
     */

    private int book_id;
    private String book_name;
    private List<String> label;
    private String square_cover;//书架推荐
    private String oblong_cover;//阅读
    private int genre;

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

    public String getSquare_cover() {
        return square_cover;
    }

    public void setSquare_cover(String square_cover) {
        this.square_cover = square_cover;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public String getOblong_cover() {
        return oblong_cover;
    }

    public void setOblong_cover(String oblong_cover) {
        this.oblong_cover = oblong_cover;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }
}
