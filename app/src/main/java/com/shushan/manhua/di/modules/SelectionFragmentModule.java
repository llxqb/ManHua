package com.shushan.manhua.di.modules;



import com.shushan.manhua.di.scopes.PerActivity;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.ui.fragment.selection.SelectionFragmentControl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by li.liu on 16/3/20.
 */
@Module
public class SelectionFragmentModule {

    private SelectionFragmentControl.SelectionView mFragmentView;

    public SelectionFragmentModule(LoadDataView view) {
        if (view instanceof SelectionFragmentControl.SelectionView) {
            mFragmentView = (SelectionFragmentControl.SelectionView) view;
        }
    }

    /**
     * 与 FourFragment    @Inject
     * FourFragmentControl.FourFragmentPresenter mPresenter; homeFragmentPresenter
     * 对应起来
     */
    @Provides
    @PerActivity
    SelectionFragmentControl.SelectionView selectionFragmentView() {
        return this.mFragmentView;
    }


}
