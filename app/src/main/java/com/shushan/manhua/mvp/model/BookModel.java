package com.shushan.manhua.mvp.model;

import com.google.gson.Gson;
import com.shushan.manhua.entity.request.BookDetailRequest;
import com.shushan.manhua.entity.request.CommentRequest;
import com.shushan.manhua.network.networkapi.BookApi;

import javax.inject.Inject;

import io.reactivex.Observable;

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

    /**
     * 查询书籍详情
     */
    public Observable<ResponseData> onRequestBookDetailInfo(BookDetailRequest request) {
        return mBookApi.onRequestBookDetailInfo(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 查询评论列表
     */
    public Observable<ResponseData> onRequestCommentInfo(CommentRequest request) {
        return mBookApi.onRequestCommentInfo(mGson.toJson(request)).map(mTransform::transformListType);
    }


}
