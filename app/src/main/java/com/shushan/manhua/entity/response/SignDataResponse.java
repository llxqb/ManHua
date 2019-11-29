package com.shushan.manhua.entity.response;

import java.util.List;

public class SignDataResponse {

    /**
     * bean : 0
     * continuous_day : 0  连续签到天数
     * user_today_sign : 0   1已签到0未签到
     * quest : [{"quest_id":1,"quest_name":"阅读3话漫画","bean":3,"num":0,"status":1},{"quest_id":2,"quest_name":"评论1部漫画","bean":3,"status":2},{"quest_id":3,"quest_name":"加入书架一部漫画","bean":3,"status":1},{"quest_id":4,"quest_name":"分享给好友","bean":3,"status":1}]
     * sign : [{"id":1,"sign_name":"1天","bean":10},{"id":2,"sign_name":"2天","bean":20},{"id":3,"sign_name":"3天","bean":30},{"id":4,"sign_name":"4天","bean":40},{"id":5,"sign_name":"5天","bean":50},{"id":6,"sign_name":"6天","bean":60},{"id":7,"sign_name":"7天","bean":70}]
     */

    private int bean;
    private int continuous_day;
    private int user_today_sign;
    private List<QuestBean> quest;
    private List<SignBean> sign;

    public int getBean() {
        return bean;
    }

    public void setBean(int bean) {
        this.bean = bean;
    }

    public int getContinuous_day() {
        return continuous_day;
    }

    public void setContinuous_day(int continuous_day) {
        this.continuous_day = continuous_day;
    }

    public int getUser_today_sign() {
        return user_today_sign;
    }

    public void setUser_today_sign(int user_today_sign) {
        this.user_today_sign = user_today_sign;
    }

    public List<QuestBean> getQuest() {
        return quest;
    }

    public void setQuest(List<QuestBean> quest) {
        this.quest = quest;
    }

    public List<SignBean> getSign() {
        return sign;
    }

    public void setSign(List<SignBean> sign) {
        this.sign = sign;
    }

    public static class QuestBean {
        /**
         * quest_id : 1
         * quest_name : 阅读3话漫画
         * bean : 3
         * num : 0    今天看了多少话
         * status : 1   1未完成2已完成未领取3已领取
         */

        private int quest_id;
        private String quest_name;
        private int bean;
        private int num;
        private int status;

        public int getQuest_id() {
            return quest_id;
        }

        public void setQuest_id(int quest_id) {
            this.quest_id = quest_id;
        }

        public String getQuest_name() {
            return quest_name;
        }

        public void setQuest_name(String quest_name) {
            this.quest_name = quest_name;
        }

        public int getBean() {
            return bean;
        }

        public void setBean(int bean) {
            this.bean = bean;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class SignBean {
        /**
         * id : 1
         * sign_name : 1天
         * bean : 10
         */

        private int id;
        private String sign_name;
        private int bean;
        public boolean isSign;//是否有签到

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSign_name() {
            return sign_name;
        }

        public void setSign_name(String sign_name) {
            this.sign_name = sign_name;
        }

        public int getBean() {
            return bean;
        }

        public void setBean(int bean) {
            this.bean = bean;
        }
    }
}
