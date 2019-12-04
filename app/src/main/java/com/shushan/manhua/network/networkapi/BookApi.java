package com.shushan.manhua.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by li.liu on 2017/4/27.
 * MainApi
 */

public interface BookApi {
    /**
     * 查询书籍详情
     */
    @POST("cartoon/book/bookDetail")
    Observable<String> onRequestBookDetailInfo(@Body String request);

    /**
     * 加入书架
     */
    @POST("cartoon/book/JoinBookrack")
    Observable<String> onAddBookShelfRequest(@Body String request);

    /**
     * 加入书架
     */
    @POST("cartoon/book/delBookrack")
    Observable<String> onRequestDeleteBook(@Body String request);

    /**
     * 请求我的书架信息
     */
    @POST("cartoon/user/bookrack")
    Observable<String> onRequestBookShelfInfo(@Body String request);

    /**
     * 点赞
     */
    @POST("cartoon/comment/like")
    Observable<String> onSupportRequest(@Body String request);

    /**
     * 评论用户评论
     */
    @POST("cartoon/comment/review")
    Observable<String> onPublishCommentUser(@Body String request);


    /**
     * 查询评论列表
     */
    @POST("cartoon/comment/list")
    Observable<String> onRequestCommentInfo(@Body String request);

    /**
     * 上传图片
     */
    @POST("cartoon/upload")
    Observable<String> uploadImageRequest(@Body String request);

    /**
     * 发布评论
     */
    @POST("cartoon/comment")
    Observable<String> onRequestPublishComment(@Body String request);

    /**
     * 请求漫画选集信息
     */
    @POST("cartoon/book/anthology")
    Observable<String> onRequestSelectionInfo(@Body String request);

    /**
     * 请求漫画选集信息
     */
    @POST("cartoon/book/read")
    Observable<String> onRequestReadingInfo(@Body String request);

    /**
     * 上传阅读记录
     */
    @POST("cartoon/book/history")
    Observable<String> onRequestReadRecording(@Body String request);

    /**
     * 发送弹幕
     */
    @POST("cartoon/book/hairBarrage")
    Observable<String> sendBarrageRequest(@Body String request);

    /**
     * 兑换弹幕样式
     */
    @POST("cartoon/book/exchangeStyle")
    Observable<String> exchangeBarrageStyleRequest(@Body String request);

    /**
     * 获取弹幕列表
     */
    @POST("cartoon/book/barrage")
    Observable<String> getBarrageListRequest(@Body String request);

    /**
     * 请求购买的弹幕样式
     */
    @POST("cartoon/book/checkStyle")
    Observable<String> onRequestBuyBarrageStyle(@Body String request);

    /**
     * 评论详情
     */
    @POST("cartoon/comment/detail")
    Observable<String> onRequestCommentDetail(@Body String request);

}
