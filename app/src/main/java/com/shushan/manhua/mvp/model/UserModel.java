package com.shushan.manhua.mvp.model;

import com.google.gson.Gson;
import com.shushan.manhua.network.networkapi.UserApi;

import javax.inject.Inject;

/**
 * Created by li.liu on 2019/9/28.
 * MainModel
 */

public class UserModel {
    private final UserApi mUserApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public UserModel(UserApi api, Gson gson, ModelTransform transform) {
        mUserApi = api;
        mGson = gson;
        mTransform = transform;
    }
//    /**
//     * 检查版本更新
//     */
//    public Observable<ResponseData> onRequestVersionUpdate(VersionUpdateRequest request) {
//        return mUserApi.onRequestVersionUpdate(mGson.toJson(request)).map(mTransform::transformCommon);
//    }



}
