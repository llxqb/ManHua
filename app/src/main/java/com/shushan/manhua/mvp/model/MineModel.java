package com.shushan.manhua.mvp.model;


import com.google.gson.Gson;
import com.shushan.manhua.entity.request.ReadingSettingRequest;
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

}
