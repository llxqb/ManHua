package com.shushan.manhua.mvp.model;


import com.google.gson.Gson;
import com.shushan.manhua.entity.request.PurchasedBookRequest;
import com.shushan.manhua.entity.request.ReadingSettingRequest;
import com.shushan.manhua.entity.request.ReceiveTaskRequest;
import com.shushan.manhua.entity.request.RecommendRequest;
import com.shushan.manhua.entity.request.SignDataRequest;
import com.shushan.manhua.entity.request.SignRequest;
import com.shushan.manhua.entity.request.SubmitFeedbackRequest;
import com.shushan.manhua.network.networkapi.MineApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by li.liu on 2019/09/18.
 * 我的相关
 */

public class MineModel {
    private final MineApi mMineApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public MineModel(MineApi api, Gson gson, ModelTransform transform) {
        mMineApi = api;
        mGson = gson;
        mTransform = transform;
    }

    /**
     * 请求漫画类型
     */
    public Observable<ResponseData> onRequestManHuaType() {
        return mMineApi.onRequestManHuaType().map(mTransform::transformListType);
    }

    /**
     * 设置阅读偏好
     */
    public Observable<ResponseData> onReadingSettingRequest(ReadingSettingRequest request) {
        return mMineApi.onReadingSettingRequest(new Gson().toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 请求签到数据
     */
    public Observable<ResponseData> onRequestSignData(SignDataRequest request) {
        return mMineApi.onRequestSignData(new Gson().toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 请求推荐数据
     */
    public Observable<ResponseData> onRecommendInfo(RecommendRequest request) {
        return mMineApi.onRecommendInfo(new Gson().toJson(request)).map(mTransform::transformListType);
    }

    /**
     * 签到
     */
    public Observable<ResponseData> onRequestSign(SignRequest request) {
        return mMineApi.onRequestSign(new Gson().toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 领取任务
     */
    public Observable<ResponseData> onRequestReceiveTask(ReceiveTaskRequest request) {
        return mMineApi.onRequestReceiveTask(new Gson().toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 已购漫画
     */
    public Observable<ResponseData> onRequestPurchasedBook(PurchasedBookRequest request) {
        return mMineApi.onRequestPurchasedBook(new Gson().toJson(request)).map(mTransform::transformListType);
    }

    /**
     * 提交辅导反馈
     */
    public Observable<ResponseData> onSubmitFeedbackRequest(SubmitFeedbackRequest request) {
        return mMineApi.onSubmitFeedbackRequest(new Gson().toJson(request)).map(mTransform::transformListType);
    }

}
