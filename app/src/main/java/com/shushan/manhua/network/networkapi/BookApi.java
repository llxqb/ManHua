package com.shushan.manhua.network.networkapi;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by helei on 2017/4/27.
 * MainApi
 */

public interface BookApi {
    /**
     * 查询书籍详情
     */
    @POST("cartoon/book/bookDetail")
    Observable<String> onRequestBookDetailInfo(@Body String request);


    /**
     * 查询评论列表
     */
    @POST("cartoon/comment/list")
    Observable<String> onRequestCommentInfo(@Body String request);

}
