package com.shushan.manhua.mvp.ui.fragment.message;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class SentMessageFragmentControl {
    public interface SentMessageView extends LoadDataView {
    }

    public interface SentMessageFragmentPresenter extends Presenter<SentMessageView> {

    }

}
