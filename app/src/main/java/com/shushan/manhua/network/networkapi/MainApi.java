package com.shushan.manhua.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 * MainApi
 */

public interface MainApi {
    /**
     * 请求漫画类型
     */
    @POST("cartoon/book/bookType")
    Observable<String> onRequestManHuaType();

    /**
     * 请求漫画类型
     */
    @POST("cartoon/login/visitor")
    Observable<String> onLoginTouristModeRequest(@Body String request);

    /**
     * 设置阅读偏好
     */
    @POST("cartoon/user/readPreference")
    Observable<String> onReadingSettingRequest(@Body String request);

    /**
     * 查询开关，应对过审
     */
    @POST("cartoon/user/search_switch")
    Observable<String> onRequestPaySwitch(@Body String request);

    /**
     * 请求首页信息
     */
    @POST("cartoon")
    Observable<String> onRequestHomeInfo(@Body String request);

    /**
     * 请求我的书架信息
     */
    @POST("cartoon/user/bookrack")
    Observable<String> onRequestBookShelfInfo(@Body String request);

    /**
     * 请求推荐书籍信息
     */
    @POST("cartoon/book/nominateBook")
    Observable<String> onRecommendInfo(@Body String request);

    /**
     * 查询我的
     */
    @POST("cartoon/user")
    Observable<String> onRequestMineInfo(@Body String request);

    /**
     * 查询是否有未读消息
     */
    @POST("cartoon/user/unReadMessage")
    Observable<String> onRequestUnReadMessage(@Body String request);

}
