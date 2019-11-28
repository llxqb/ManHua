package com.shushan.manhua.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 * MainApi
 */

public interface MineApi {
    /**
     * 请求漫画类型
     */
    @POST("cartoon/book/bookType")
    Observable<String> onRequestManHuaType();

    /**
     * 设置阅读偏好
     */
    @POST("cartoon/user/readPreference")
    Observable<String> onReadingSettingRequest(@Body String request);

}
