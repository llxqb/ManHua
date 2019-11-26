package com.shushan.manhua.entity;

public class ReplyBean {

    /**
     *用户B
     */
    public int user_B;

    public String user_B_name;
    /**
     *用户C
     */
    public int user_C;
    public String user_C_name;
    /**
     *用户B 回复的内容
     */
    public String user_B_Content;

    public ReplyBean(int user_B, String user_B_name, int user_C, String user_C_name, String user_B_Content) {
        this.user_B = user_B;
        this.user_B_name = user_B_name;
        this.user_C = user_C;
        this.user_C_name = user_C_name;
        this.user_B_Content = user_B_Content;
    }
}
