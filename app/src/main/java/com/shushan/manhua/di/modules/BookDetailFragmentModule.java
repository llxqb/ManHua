package com.shushan.manhua.di.modules;



import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.ui.fragment.bookDetail.BookDetailFragmentControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class BookDetailFragmentModule {

    private BookDetailFragmentControl.BookDetailView mFragmentView;

    public BookDetailFragmentModule(LoadDataView view) {
        if (view instanceof BookDetailFragmentControl.BookDetailView) {
            mFragmentView = (BookDetailFragmentControl.BookDetailView) view;
        }
    }

    /**
     * 与 FourFragment    @Inject
     * FourFragmentControl.FourFragmentPresenter mPresenter; homeFragmentPresenter
     * 对应起来
     */
    @Provides
    @PerActivity
    BookDetailFragmentControl.BookDetailView bookDetailFragmentView() {
        return this.mFragmentView;
    }


}
