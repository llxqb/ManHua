package com.shushan.manhua.ireader.model.remote;


import com.google.gson.Gson;
import com.shushan.manhua.entity.request.BookContentRequest;
import com.shushan.manhua.ireader.model.bean.BookChapterBean;
import com.shushan.manhua.ireader.model.bean.ChapterInfoBean;
import com.shushan.manhua.ireader.model.bean.CollBookBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;

/**
 * Created by newbiechen on 17-4-20.
 */

public class RemoteRepository {
    private static final String TAG = "RemoteRepository";

    private static RemoteRepository sInstance;
    private Retrofit mRetrofit;
    private BookApi mBookApi;

    private RemoteRepository() {
        mRetrofit = RemoteHelper.getInstance()
                .getRetrofit();

        mBookApi = mRetrofit.create(BookApi.class);
    }

    public static RemoteRepository getInstance() {
        if (sInstance == null) {
            synchronized (RemoteHelper.class) {
                if (sInstance == null) {
                    sInstance = new RemoteRepository();
                }
            }
        }
        return sInstance;
    }

    public Single<List<CollBookBean>> getRecommendBooks(String gender) {
        return mBookApi.getRecommendBookPackage(gender)
                .map(bean -> bean.getBooks());
    }

    public Single<List<BookChapterBean>> getBookChapters(String bookId) {
        return mBookApi.getBookChapterPackage(bookId, "chapter")
                .map(bean -> {
                    if (bean.getMixToc() == null) {
                        return new ArrayList<BookChapterBean>(1);
                    } else {
                        return bean.getMixToc().getChapters();
                    }
                });
    }

    /**
     * 注意这里用的是同步请求
     *
     * @return
     */
    public Single<ChapterInfoBean> getChapterInfo(BookContentRequest bookContentRequest) {
        return mBookApi.getChapterInfoPackage(new Gson().toJson(bookContentRequest))
                .map(bean -> bean.getChapter());
    }

}
