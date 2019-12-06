package com.shushan.manhua.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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

    /**
     * 请求会员中心
     */
    @POST("cartoon/user/center")
    Observable<String> onRequestMemberCenter(@Body String request);

    /**
     * VIP每日领取漫豆
     */
    @POST("cartoon/user/vipGetBean")
    Observable<String> onRequestReceivedBeanByVip(@Body String request);

    /**
     * 充值中心
     */
    @POST("cartoon/user/voucherCenter")
    Observable<String> onRequestVoucherCenter(@Body String request);

    /**
     * Google 创建订单
     */
    @POST("cartoon/order")
    Observable<String> onRequestCreateOrder(@Body String request);

    /**
     * 充值记录/消费记录
     */
    @POST("cartoon/user/dealLog")
    Observable<String> onRequestRechargeRecord(@Body String request);


    /**
     * 创建订单--AHDI支付
     */
    @POST("cartoon/ahdipay")
    Observable<String> onRequestCreateOrderAHDI(@Body String request);

    /**
     * 创建订单--UniPin支付
     */
    @POST("cartoon/unipinpay")
    Observable<String> onRequestCreateOrderByUniPin(@Body String request);

//    /**
//     * Google支付成功上报
//     * 多参数表单提交
//     */
//    @POST("cartoon/google")
//    Observable<String> onRequestPaySuccess(@Body String request);
    @FormUrlEncoded
    @POST("cartoon/google")
    Observable<String> onRequestPaySuccess(@Field("INAPP_PURCHASE_DATA") String data, @Field("INAPP_DATA_SIGNATURE") String signature, @Field("ord_no") String order_no);

    /**
     * AHDI支付成功上报
     */
    @POST("cartoon/Ahdipay/report")
    Observable<String> onPayFinishAHDIUpload(@Body String request);

    /**
     * UniPin支付上报
     */
    @POST("cartoon/unipinpay/inquiry")
    Observable<String> onPayFinishUploadByUniPin(@Body String request);
}
