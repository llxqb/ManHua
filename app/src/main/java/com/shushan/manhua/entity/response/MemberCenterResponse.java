package com.shushan.manhua.entity.response;

import java.util.List;

public class MemberCenterResponse {

    /**
     * vipget_state : 0
     * userinfo : {"id":19,"name":"bee T","head_portrait":"https://img.pulaukomik.com/book/20191202/5de4bffdc40da.png","sex":0,"birthday":0,"token":"58fe5932d4eb0275c8e9623e37b32baf","deviceId":"00000000-3512-abd9-3512-abd900000000","platform":"android","type":1,"bean":3,"valid_bean":3,"vip":0,"vip_end_time":0,"last_month_bean":0,"channel":1,"book_type":[2,3,6]}
     * vipinfo : [{"vipinfo_id":1,"name":"谷歌订阅/App Store订阅","original_price":"29.99","price":"9.99","bean":200,"indate":30,"type":1,"yn_price":0},{"vipinfo_id":2,"name":"1个月","original_price":"29.99","price":"19.99","bean":400,"indate":30,"type":2,"yn_price":290000},{"vipinfo_id":3,"name":"3个月","original_price":"89.99","price":"49.99","bean":1400,"indate":90,"type":2,"yn_price":706000},{"vipinfo_id":4,"name":"12个月","original_price":"359.99","price":"99.99","bean":6000,"indate":365,"type":2,"yn_price":1412000}]
     */

    private int vipget_state;
    private UserinfoBean userinfo;
    private List<VipinfoBean> vipinfo;

    public int getVipget_state() {
        return vipget_state;
    }

    public void setVipget_state(int vipget_state) {
        this.vipget_state = vipget_state;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public List<VipinfoBean> getVipinfo() {
        return vipinfo;
    }

    public void setVipinfo(List<VipinfoBean> vipinfo) {
        this.vipinfo = vipinfo;
    }

    public static class UserinfoBean {
        /**
         * id : 19
         * name : bee T
         * head_portrait : https://img.pulaukomik.com/book/20191202/5de4bffdc40da.png
         * sex : 0
         * birthday : 0
         * token : 58fe5932d4eb0275c8e9623e37b32baf
         * deviceId : 00000000-3512-abd9-3512-abd900000000
         * platform : android
         * type : 1
         * bean : 3
         * valid_bean : 3
         * vip : 0
         * vip_end_time : 0
         * last_month_bean : 0
         * channel : 1
         * book_type : [2,3,6]
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
        private List<Integer> book_type;

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

        public List<Integer> getBook_type() {
            return book_type;
        }

        public void setBook_type(List<Integer> book_type) {
            this.book_type = book_type;
        }
    }

    public static class VipinfoBean {
        /**
         * vipinfo_id : 1
         * name : 谷歌订阅/App Store订阅
         * original_price : 29.99
         * price : 9.99
         * bean : 200
         * indate : 30  有效天数
         * type : 1      1谷歌订阅2非订阅
         * yn_price : 0
         */

        private int vipinfo_id;
        private String name;
        private String original_price;
        private String price;
        private int bean;
        private int indate;
        private int type;
        private int yn_price;
        public boolean isCheck;

        public int getVipinfo_id() {
            return vipinfo_id;
        }

        public void setVipinfo_id(int vipinfo_id) {
            this.vipinfo_id = vipinfo_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getBean() {
            return bean;
        }

        public void setBean(int bean) {
            this.bean = bean;
        }

        public int getIndate() {
            return indate;
        }

        public void setIndate(int indate) {
            this.indate = indate;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getYn_price() {
            return yn_price;
        }

        public void setYn_price(int yn_price) {
            this.yn_price = yn_price;
        }
    }
}
