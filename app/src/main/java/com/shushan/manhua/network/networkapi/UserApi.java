package com.shushan.manhua.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 * MainApi
 */

public interface UserApi {
    /**
     * 请求消息列表
     */
    @POST("cartoon/comment/commentLog")
    Observable<String> onRequestMessageInfo(@Body String request);

}
