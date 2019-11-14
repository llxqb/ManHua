package com.shushan.manhua.mvp.model;

import com.google.gson.Gson;
import com.shushan.manhua.network.networkapi.BookApi;

import javax.inject.Inject;

/**
 * Created by li.liu on 2019/9/28.
 * MainModel
 */

public class BookModel {
    private final BookApi mBookApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public BookModel(BookApi api, Gson gson, ModelTransform transform) {
        mBookApi = api;
        mGson = gson;
        mTransform = transform;
    }
//    /**
//     * 检查版本更新
//     */
//    public Observable<ResponseData> onRequestVersionUpdate(VersionUpdateRequest request) {
//        return mBookApi.onRequestVersionUpdate(mGson.toJson(request)).map(mTransform::transformCommon);
//    }



}
