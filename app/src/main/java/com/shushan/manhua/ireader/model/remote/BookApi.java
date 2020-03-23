package com.shushan.manhua.ireader.model.remote;


import com.shushan.manhua.ireader.model.bean.packages.BookChapterPackage;
import com.shushan.manhua.ireader.model.bean.packages.ChapterInfoPackage;
import com.shushan.manhua.ireader.model.bean.packages.RecommendBookPackage;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by newbiechen on 17-4-20.
 */

public interface BookApi {

    /**
     * 推荐书籍
     * @param gender
     * @return
     */
    @GET("/book/recommend")
    Single<RecommendBookPackage> getRecommendBookPackage(@Query("gender") String gender);


    /**
     * 获取书籍的章节总列表
     * @param bookId
     * @param view 默认参数为:chapters
     * @return
     */
    @GET("/mix-atoc/{bookId}")
    Single<BookChapterPackage> getBookChapterPackage(@Path("bookId") String bookId, @Query("view") String view);

    /**
     * 章节的内容
     * 这里采用的是同步请求。
     * @return
     */
    @POST("/cartoon/book/catalogueTxt")
    Single<ChapterInfoPackage> getChapterInfoPackage(@Body String request);


}
