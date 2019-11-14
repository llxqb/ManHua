package com.shushan.manhua.mvp.ui.fragment.bookshelf;

import android.content.Context;

import com.shushan.manhua.mvp.model.MainModel;

import javax.inject.Inject;

/**
 * Created by li.liu on 2019/5/28.
 * HomePresenterImpl
 */

public class BookShelfFragmentPresenterImpl implements BookShelfFragmentControl.BookShelfFragmentPresenter {

    private BookShelfFragmentControl.BookShelfView mBookShelfView;
    private final MainModel mMainModel;
    private final Context mContext;

    @Inject
    public BookShelfFragmentPresenterImpl(Context context, MainModel model, BookShelfFragmentControl.BookShelfView bookShelfView) {
        mContext = context;
        mMainModel = model;
        mBookShelfView = bookShelfView;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mBookShelfView = null;
    }
}
