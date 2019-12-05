package com.shushan.manhua.mvp.model;

import com.google.gson.Gson;
import com.shushan.manhua.entity.request.DeleteMessageRequest;
import com.shushan.manhua.entity.request.MessageRequest;
import com.shushan.manhua.entity.request.PersonalInfoRequest;
import com.shushan.manhua.entity.request.UpdatePersonalInfoRequest;
import com.shushan.manhua.entity.request.UploadImage;
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
    /**
     * 删除评论/回复
     */
    public Observable<ResponseData> onDeleteMessageRequest(DeleteMessageRequest request) {
        return mUserApi.onDeleteMessageRequest(mGson.toJson(request)).map(mTransform::transformListType);
    }

    /**
     * 查询个人基本信息
     */
    public Observable<ResponseData> onRequestPersonalInfo(PersonalInfoRequest request) {
        return mUserApi.onRequestPersonalInfo(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 上传图片
     */
    public Observable<ResponseData> uploadImageRequest(UploadImage request) {
        return mUserApi.uploadImageRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 更新个人基本信息
     */
    public Observable<ResponseData> updatePersonalInfoRequest(UpdatePersonalInfoRequest request) {
        return mUserApi.updatePersonalInfoRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
