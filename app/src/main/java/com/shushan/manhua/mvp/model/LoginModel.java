package com.shushan.manhua.mvp.model;

import com.google.gson.Gson;
import com.shushan.manhua.entity.request.FacebookLoginRequest;
import com.shushan.manhua.entity.request.LoginRequest;
import com.shushan.manhua.network.networkapi.LoginApi;

import javax.inject.Inject;

import io.reactivex.Observable;

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
    public Observable<ResponseData> onRequestLogin(LoginRequest request) {
        return mLoginApi.onRequestLogin(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * facebook登录
     */
    public Observable<ResponseData> onRequestLoginFacebook(FacebookLoginRequest request) {
        return mLoginApi.onRequestLoginFacebook(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
