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
     * 检查版本更新
     */
    @POST("student/version/app_version")
    Observable<String> onRequestVersionUpdate(@Body String request);

}
