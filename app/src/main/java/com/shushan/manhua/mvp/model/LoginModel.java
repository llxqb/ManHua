package com.shushan.manhua.mvp.model;

import com.google.gson.Gson;
import com.shushan.manhua.network.networkapi.LoginApi;

import javax.inject.Inject;

/**
 * Created by li.liu on 2019/9/28.
 * MainModel
 */

public class LoginModel {
    private final LoginApi mLoginApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public LoginModel(LoginApi api, Gson gson, ModelTransform transform) {
        mLoginApi = api;
        mGson = gson;
        mTransform = transform;
    }
//    /**
//     * 检查版本更新
//     */
//    public Observable<ResponseData> onRequestVersionUpdate(VersionUpdateRequest request) {
//        return mLoginApi.onRequestVersionUpdate(mGson.toJson(request)).map(mTransform::transformCommon);
//    }



}
