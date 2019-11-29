package com.shushan.manhua.entity.response;

public class FacebookLoginResponse {
    /**
     * userinfo : {"name":"Yan Wang","head_portrait":"https://img.pulaukomik.com/cover/20191129/5de0ee79ef156.jpg","token":"1ec52ae4129a79453b68da22fa368bf4","vip":0,"vip_end_time":0,"channel":0,"book_type":"[2, 3, 4]"}
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
         * name : Yan Wang
         * head_portrait : https://img.pulaukomik.com/cover/20191129/5de0ee79ef156.jpg
         * token : 1ec52ae4129a79453b68da22fa368bf4
         * vip : 0
         * vip_end_time : 0
         * channel : 0
         * book_type : [2, 3, 4]
         */

        private String name;
        private String head_portrait;
        private String token;
        private int vip;
        private int vip_end_time;
        private int channel;
        private String book_type;

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

        public String getBook_type() {
            return book_type;
        }

        public void setBook_type(String book_type) {
            this.book_type = book_type;
        }
    }
}
