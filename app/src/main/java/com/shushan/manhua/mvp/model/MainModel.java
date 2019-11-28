package com.shushan.manhua.mvp.model;

import com.google.gson.Gson;
import com.shushan.manhua.entity.request.BookShelfInfoRequest;
import com.shushan.manhua.entity.request.HomeInfoRequest;
import com.shushan.manhua.entity.request.LoginTouristModeRequest;
import com.shushan.manhua.entity.request.ReadingSettingRequest;
import com.shushan.manhua.entity.request.RecommendRequest;
import com.shushan.manhua.network.networkapi.MainApi;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by li.liu on 2019/9/28.
 * MainModel
 */

public class MainModel {
    private final MainApi mMainApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public MainModel(MainApi api, Gson gson, ModelTransform transform) {
        mMainApi = api;
        mGson = gson;
        mTransform = transform;
    }

    /**
     * 请求漫画类型
     */
    public Observable<ResponseData> onRequestManHuaType() {
        return mMainApi.onRequestManHuaType().map(mTransform::transformListType);
    }
    /**
     * 游客模式注册登陆
     */
    public Observable<ResponseData> onLoginTouristModeRequest(LoginTouristModeRequest request) {
        return mMainApi.onLoginTouristModeRequest(new Gson().toJson(request)).map(mTransform::transformCommon);
    }
    /**
     * 设置阅读偏好
     */
    public Observable<ResponseData> onReadingSettingRequest(ReadingSettingRequest request) {
        return mMainApi.onReadingSettingRequest(new Gson().toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 请求首页信息
     */
    public Observable<ResponseData> onRequestHomeInfo(HomeInfoRequest request) {
        return mMainApi.onRequestHomeInfo(new Gson().toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 请求我的书架信息
     */
    public Observable<ResponseData> onRequestBookShelfInfo(BookShelfInfoRequest request) {
        return mMainApi.onRequestBookShelfInfo(new Gson().toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 书架请求推荐数据
     */
    public Observable<ResponseData> onRecommendInfo(RecommendRequest request) {
        return mMainApi.onRecommendInfo(new Gson().toJson(request)).map(mTransform::transformListType);
    }


}
