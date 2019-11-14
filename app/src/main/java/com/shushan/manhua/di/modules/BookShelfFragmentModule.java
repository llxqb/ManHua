package com.shushan.manhua.di.modules;



import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.ui.fragment.bookshelf.BookShelfFragmentControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class BookShelfFragmentModule {

    private BookShelfFragmentControl.BookShelfView mMineFragmentView;

    public BookShelfFragmentModule(LoadDataView view) {
        if (view instanceof BookShelfFragmentControl.BookShelfView) {
            mMineFragmentView = (BookShelfFragmentControl.BookShelfView) view;
        }
    }

    /**
     * 与 FourFragment    @Inject
     * FourFragmentControl.FourFragmentPresenter mPresenter; homeFragmentPresenter
     * 对应起来
     */
    @Provides
    @PerActivity
    BookShelfFragmentControl.BookShelfView mineFragmentView() {
        return this.mMineFragmentView;
    }


}
