package com.shushan.manhua.mvp.ui.fragment.message;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class NoticeMessageFragmentControl {
    public interface NoticeMessageView extends LoadDataView {
    }

    public interface NoticeMessageFragmentPresenter extends Presenter<NoticeMessageView> {

    }

}
