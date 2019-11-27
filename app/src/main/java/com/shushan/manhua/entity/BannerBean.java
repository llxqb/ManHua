package com.shushan.manhua.entity;

public class BannerBean {
    /**
     * banner_name : 测试banner图
     * banner_pic : http://www.baidu.com
     * banner_cate : 1
     * banner_url : http://www.baidu.com
     * custom :
     */

    private String banner_name;
    private String banner_pic;
    private int banner_cate;
    private String banner_url;
    private String custom;

    public String getBanner_name() {
        return banner_name;
    }

    public void setBanner_name(String banner_name) {
        this.banner_name = banner_name;
    }

    public String getBanner_pic() {
        return banner_pic;
    }

    public void setBanner_pic(String banner_pic) {
        this.banner_pic = banner_pic;
    }

    public int getBanner_cate() {
        return banner_cate;
    }

    public void setBanner_cate(int banner_cate) {
        this.banner_cate = banner_cate;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }
}
