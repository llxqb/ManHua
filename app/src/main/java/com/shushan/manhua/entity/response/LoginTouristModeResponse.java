package com.shushan.manhua.entity.response;

import java.util.List;

public class LoginTouristModeResponse {


    /**
     * userinfo : {"name":"立刘","head_portrait":"https://img.pulaukomik.com/cover/20191126/5ddce6620baca.jpg","token":"ca2c880d7ce3e0dbfb33a1c2b871a77a","vip":0,"vip_end_time":0,"channel":1,"book_type":[],"type":0}
     */

    private UserinfoBean userinfo;

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public static class UserinfoBean {
        /**
         * name : 立刘
         * head_portrait : https://img.pulaukomik.com/cover/20191126/5ddce6620baca.jpg
         * token : ca2c880d7ce3e0dbfb33a1c2b871a77a
         * vip : 0
         * vip_end_time : 0
         * channel : 1
         * book_type : []
         * type : 0
         */

        private String name;
        private String head_portrait;
        private String token;
        private int vip;
        private int vip_end_time;
        private int channel;
        private int type;
        private List<String> book_type;

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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getVip() {
            return vip;
        }

        public void setVip(int vip) {
            this.vip = vip;
        }

        public int getVip_end_time() {
            return vip_end_time;
        }

        public void setVip_end_time(int vip_end_time) {
            this.vip_end_time = vip_end_time;
        }

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<String> getBook_type() {
            return book_type;
        }

        public void setBook_type(List<String> book_type) {
            this.book_type = book_type;
        }
    }
}
