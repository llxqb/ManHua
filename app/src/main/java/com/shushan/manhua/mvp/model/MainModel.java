package com.shushan.manhua.mvp.model;

import com.google.gson.Gson;
import com.shushan.manhua.entity.request.BookShelfInfoRequest;
import com.shushan.manhua.entity.request.HomeInfoRequest;
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




}
