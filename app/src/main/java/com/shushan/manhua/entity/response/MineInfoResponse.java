package com.shushan.manhua.entity.response;

public class MineInfoResponse {


    /**
     * unread_message : 0
     * userinfo : {"id":3,"name":"立刘","head_portrait":"https://img.pulaukomik.com/cover/20191126/5ddce6620baca.jpg","sex":0,"birthday":0,"token":"e90e10bc4b4747b3476010ebb8226a8c","deviceId":"00000000-3512-abd9-3512-abd900000000","platform":"android","type":0,"bean":0,"valid_bean":13,"vip":0,"vip_end_time":0,"last_month_bean":0,"channel":1,"book_type":{}}
     * sign_count : 2
     * vip_get_count : 0
     */

    private int unread_message;
    private UserinfoBean userinfo;
    private int sign_count;
    private int vip_get_count;

    public int getUnread_message() {
        return unread_message;
    }

    public void setUnread_message(int unread_message) {
        this.unread_message = unread_message;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public int getSign_count() {
        return sign_count;
    }

    public void setSign_count(int sign_count) {
        this.sign_count = sign_count;
    }

    public int getVip_get_count() {
        return vip_get_count;
    }

    public void setVip_get_count(int vip_get_count) {
        this.vip_get_count = vip_get_count;
    }

    public static class UserinfoBean {
        /**
         * id : 3
         * name : 立刘
         * head_portrait : https://img.pulaukomik.com/cover/20191126/5ddce6620baca.jpg
         * sex : 0
         * birthday : 0
         * token : e90e10bc4b4747b3476010ebb8226a8c
         * deviceId : 00000000-3512-abd9-3512-abd900000000
         * platform : android
         * type : 0
         * bean : 0
         * valid_bean : 13
         * vip : 0
         * vip_end_time : 0
         * last_month_bean : 0
         * channel : 1
         * book_type : {}
         */

        private int id;
        private String name;
        private String head_portrait;
        private int sex;
        private int birthday;
        private String token;
        private String deviceId;
        private String platform;
        private int type;
        private int bean;
        private int valid_bean;
        private int vip;
        private int vip_end_time;
        private int last_month_bean;
        private int channel;
        private BookTypeBean book_type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getBirthday() {
            return birthday;
        }

        public void setBirthday(int birthday) {
            this.birthday = birthday;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getPlatform() {
            return platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
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

        public int getValid_bean() {
            return valid_bean;
        }

        public void setValid_bean(int valid_bean) {
            this.valid_bean = valid_bean;
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

        public int getLast_month_bean() {
            return last_month_bean;
        }

        public void setLast_month_bean(int last_month_bean) {
            this.last_month_bean = last_month_bean;
        }

        public int getChannel() {
            return channel;
        }

        public void setChannel(int channel) {
            this.channel = channel;
        }

        public BookTypeBean getBook_type() {
            return book_type;
        }

        public void setBook_type(BookTypeBean book_type) {
            this.book_type = book_type;
        }

        public static class BookTypeBean {
        }
    }
}
