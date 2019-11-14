package com.shushan.manhua.mvp.model;

import com.google.gson.Gson;
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
//    /**
//     * 检查版本更新
//     */
//    public Observable<ResponseData> onRequestVersionUpdate(VersionUpdateRequest request) {
//        return mMainApi.onRequestVersionUpdate(mGson.toJson(request)).map(mTransform::transformCommon);
//    }



}
