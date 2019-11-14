package com.shushan.manhua.mvp.ui.fragment.message;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class ReceivedMessageFragmentControl {
    public interface ReceivedMessageView extends LoadDataView {
    }

    public interface ReceivedMessageFragmentPresenter extends Presenter<ReceivedMessageView> {

    }

}
