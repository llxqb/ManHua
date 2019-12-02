package com.shushan.manhua.entity.constants;

import java.util.List;

public class VoucherCenterResponse {
    /**
     * bean : 0
     * expire_time : 1577807999   到期时间
     * expire_bean : 0            快过期漫豆数量
     * beaninfo : [{"beans_id":1,"beans":500,"presenter_beans":250,"price":"4.99"},{"beans_id":2,"beans":100,"presenter_beans":50,"price":"0.99"},{"beans_id":3,"beans":1000,"presenter_beans":500,"price":"9.99"},{"beans_id":4,"beans":2000,"presenter_beans":1000,"price":"19.99"},{"beans_id":5,"beans":3000,"presenter_beans":1500,"price":"29.99"},{"beans_id":6,"beans":5000,"presenter_beans":2500,"price":"49.99"}]
     */

    private int bean;
    private int expire_time;
    private int expire_bean;
    private List<BeaninfoBean> beaninfo;

    public int getBean() {
        return bean;
    }

    public void setBean(int bean) {
        this.bean = bean;
    }

    public int getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(int expire_time) {
        this.expire_time = expire_time;
    }

    public int getExpire_bean() {
        return expire_bean;
    }

    public void setExpire_bean(int expire_bean) {
        this.expire_bean = expire_bean;
    }

    public List<BeaninfoBean> getBeaninfo() {
        return beaninfo;
    }

    public void setBeaninfo(List<BeaninfoBean> beaninfo) {
        this.beaninfo = beaninfo;
    }

    public static class BeaninfoBean {
        /**
         * beans_id : 1
         * beans : 500
         * presenter_beans : 250  赠送数量
         * price : 4.99
         */

        private int beans_id;
        private int beans;
        private int presenter_beans;
        private String price;
        public boolean isCheck;

        public int getBeans_id() {
            return beans_id;
        }

        public void setBeans_id(int beans_id) {
            this.beans_id = beans_id;
        }

        public int getBeans() {
            return beans;
        }

        public void setBeans(int beans) {
            this.beans = beans;
        }

        public int getPresenter_beans() {
            return presenter_beans;
        }

        public void setPresenter_beans(int presenter_beans) {
            this.presenter_beans = presenter_beans;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
