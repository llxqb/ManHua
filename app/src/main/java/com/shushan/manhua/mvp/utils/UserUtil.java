package com.shushan.manhua.mvp.utils;

import com.shushan.manhua.entity.response.LoginTouristModeResponse;
import com.shushan.manhua.entity.response.MineInfoResponse;
import com.shushan.manhua.entity.user.User;

public class UserUtil {

    /**
     * 更新用户信息
     */
    public static User tranLoginUser(MineInfoResponse.UserinfoBean userInfoBean, String bookType) {
        User user = new User();
        user.token = userInfoBean.getToken();
        user.name = userInfoBean.getName();
        user.avatar = userInfoBean.getHead_portrait();
        user.bean = userInfoBean.getBean();
        user.channel = userInfoBean.getChannel();
        user.vip = userInfoBean.getVip();
        user.bookType = bookType;
        return user;
    }

    /**
     * 游客模式
     * 更新用户信息
     */
    public static User tranLoginUser(LoginTouristModeResponse.UserinfoBean userInfoBean) {
        User user = new User();
        user.token = userInfoBean.getToken();
        user.name = userInfoBean.getName();
        user.avatar = userInfoBean.getHead_portrait();
        user.channel = userInfoBean.getChannel();
        return user;
    }
}
