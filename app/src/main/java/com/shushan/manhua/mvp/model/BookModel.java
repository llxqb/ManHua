package com.shushan.manhua.mvp.model;

import com.google.gson.Gson;
import com.shushan.manhua.entity.request.BookDetailRequest;
import com.shushan.manhua.entity.request.CommentRequest;
import com.shushan.manhua.entity.request.CommentSuggestRequest;
import com.shushan.manhua.entity.request.PublishCommentRequest;
import com.shushan.manhua.entity.request.UploadImage;
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
     * 评论点赞
     */
    public Observable<ResponseData> onCommentSuggestRequest(CommentSuggestRequest request) {
        return mBookApi.onCommentSuggestRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 查询评论列表
     */
    public Observable<ResponseData> onRequestCommentInfo(CommentRequest request) {
        return mBookApi.onRequestCommentInfo(mGson.toJson(request)).map(mTransform::transformListType);
    }

    /**
     * 上传图片
     */
    public Observable<ResponseData> uploadImageRequest(UploadImage request) {
        return mBookApi.uploadImageRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }
    /**
     * 发布评论
     */
    public Observable<ResponseData> onRequestPublishComment(PublishCommentRequest request) {
        return mBookApi.onRequestPublishComment(mGson.toJson(request)).map(mTransform::transformCommon);
    }


}
