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
     * 请求我的书架信息
     */
    @POST("cartoon/book/nominateBook")
    Observable<String> onRecommendInfo(@Body String request);

}
