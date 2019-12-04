package com.shushan.manhua.mvp.model;

import com.google.gson.Gson;
import com.shushan.manhua.entity.request.AddBookShelfRequest;
import com.shushan.manhua.entity.request.BarrageListRequest;
import com.shushan.manhua.entity.request.BookDetailRequest;
import com.shushan.manhua.entity.request.BookShelfInfoRequest;
import com.shushan.manhua.entity.request.BuyBarrageStyleRequest;
import com.shushan.manhua.entity.request.CommentDetailRequest;
import com.shushan.manhua.entity.request.CommentRequest;
import com.shushan.manhua.entity.request.DeleteReadingHistoryRequest;
import com.shushan.manhua.entity.request.ExchangeBarrageStyleRequest;
import com.shushan.manhua.entity.request.PublishCommentUserRequest;
import com.shushan.manhua.entity.request.ReadRecordingRequest;
import com.shushan.manhua.entity.request.ReadingHistoryRequest;
import com.shushan.manhua.entity.request.SendBarrageRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.request.DeleteBookShelfRequest;
import com.shushan.manhua.entity.request.PublishCommentRequest;
import com.shushan.manhua.entity.request.ReadingRequest;
import com.shushan.manhua.entity.request.SelectionRequest;
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
     * 加入书架
     */
    public Observable<ResponseData> onAddBookShelfRequest(AddBookShelfRequest request) {
        return mBookApi.onAddBookShelfRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 删除书架漫画
     */
    public Observable<ResponseData> onRequestDeleteBook(DeleteBookShelfRequest request) {
        return mBookApi.onRequestDeleteBook(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 请求我的书架信息
     */
    public Observable<ResponseData> onRequestBookShelfInfo(BookShelfInfoRequest request) {
        return mBookApi.onRequestBookShelfInfo(new Gson().toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 评论点赞
     */
    public Observable<ResponseData> onSupportRequest(SupportRequest request) {
        return mBookApi.onSupportRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 评论用户评论
     */
    public Observable<ResponseData> onPublishCommentUser(PublishCommentUserRequest request) {
        return mBookApi.onPublishCommentUser(mGson.toJson(request)).map(mTransform::transformCommon);
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

    /**
     * 请求漫画选集信息
     */
    public Observable<ResponseData> onRequestSelectionInfo(SelectionRequest request) {
        return mBookApi.onRequestSelectionInfo(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 章节详情
     */
    public Observable<ResponseData> onRequestReadingInfo(ReadingRequest request) {
        return mBookApi.onRequestReadingInfo(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 上传阅读记录
     */
    public Observable<ResponseData> onRequestReadRecording(ReadRecordingRequest request) {
        return mBookApi.onRequestReadRecording(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 发送弹幕
     */
    public Observable<ResponseData> sendBarrageRequest(SendBarrageRequest request) {
        return mBookApi.sendBarrageRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 兑换弹幕样式
     */
    public Observable<ResponseData> exchangeBarrageStyleRequest(ExchangeBarrageStyleRequest request) {
        return mBookApi.exchangeBarrageStyleRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 获取弹幕列表
     */
    public Observable<ResponseData> getBarrageListRequest(BarrageListRequest request) {
        return mBookApi.getBarrageListRequest(mGson.toJson(request)).map(mTransform::transformListType);
    }

    /**
     * 请求购买的弹幕样式
     */
    public Observable<ResponseData> onRequestBuyBarrageStyle(BuyBarrageStyleRequest request) {
        return mBookApi.onRequestBuyBarrageStyle(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 评论详情
     */
    public Observable<ResponseData> onRequestCommentDetail(CommentDetailRequest request) {
        return mBookApi.onRequestCommentDetail(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    /**
     * 阅读历史
     */
    public Observable<ResponseData> onRequestReadingHistory(ReadingHistoryRequest request) {
        return mBookApi.onRequestReadingHistory(mGson.toJson(request)).map(mTransform::transformListType);
    }
    /**
     * 删除阅读历史
     */
    public Observable<ResponseData> onDeleteReadingHistoryRequest(DeleteReadingHistoryRequest request) {
        return mBookApi.onDeleteReadingHistoryRequest(mGson.toJson(request)).map(mTransform::transformListType);
    }


}
