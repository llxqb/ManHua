package com.shushan.manhua.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 * MainApi
 */

public interface LoginApi {
//    /**
//     * 检查版本更新
//     */
//    @POST("student/version/app_version")
//    Observable<String> onRequestVersionUpdate(@Body String request);
    /**
     * 登录
     */
    @POST("cartoon/login")
    Observable<String> onRequestLogin(@Body String request);
    /**
     * facebook登录
     */
    @POST("cartoon/login/facebook")
    Observable<String> onRequestLoginFacebook(@Body String request);

}
