package com.shushan.manhua.mvp.model;

import com.google.gson.Gson;
import com.shushan.manhua.entity.request.MessageRequest;
import com.shushan.manhua.network.networkapi.UserApi;

import javax.inject.Inject;

import io.reactivex.Observable;

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
    /**
     * 请求消息列表
     */
    public Observable<ResponseData> onRequestMessageInfo(MessageRequest request) {
        return mUserApi.onRequestMessageInfo(mGson.toJson(request)).map(mTransform::transformListType);
    }

}
