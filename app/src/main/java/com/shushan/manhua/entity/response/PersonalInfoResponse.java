package com.shushan.manhua.entity.response;

public class PersonalInfoResponse {
    /**
     * bean : 0
     * vip : 0
     * vip_end_time : 0
     * name : 立刘
     * head_portrait : https://img.pulaukomik.com/cover/20191129/5de0ba8c5bfae.jpg
     * sex : 0
     * birthday : 0
     */

    private int bean;
    private int vip;
    private int vip_end_time;
    private String name;
    private String head_portrait;
    private int sex;
    private int birthday;

    public int getBean() {
        return bean;
    }

    public void setBean(int bean) {
        this.bean = bean;
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
}
