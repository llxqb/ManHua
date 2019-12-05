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

    /**
     * 删除评论/回复
     */
    @POST("cartoon/comment/delComment")
    Observable<String> onDeleteMessageRequest(@Body String request);

    /**
     * 请求个人信息
     */
    @POST("cartoon/user/userInfo")
    Observable<String> onRequestPersonalInfo(@Body String request);

    /**
     * 上传图片
     */
    @POST("cartoon/upload")
    Observable<String> uploadImageRequest(@Body String request);

    /**
     * 更新个人基本信息
     */
    @POST("cartoon/user/setUserInfo")
    Observable<String> updatePersonalInfoRequest(@Body String request);
}
