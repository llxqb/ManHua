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

    /**
     * 请求签到数据
     */
    @POST("cartoon/sign")
    Observable<String> onRequestSignData(@Body String request);

    /**
     * 请求推荐书籍信息
     */
    @POST("cartoon/book/nominateBook")
    Observable<String> onRecommendInfo(@Body String request);

    /**
     * 请求推荐书籍信息
     */
    @POST("cartoon/sign/checkin")
    Observable<String> onRequestSign(@Body String request);

    /**
     * 领取任务
     */
    @POST("cartoon/sign/getquest")
    Observable<String> onRequestReceiveTask(@Body String request);

    /**
     * 已购漫画
     */
    @POST("cartoon/user/buyCatalogue")
    Observable<String> onRequestPurchasedBook(@Body String request);
    /**
     * 提交辅导反馈
     */
    @POST("cartoon/user/feedback")
    Observable<String> onSubmitFeedbackRequest(@Body String request);

}
