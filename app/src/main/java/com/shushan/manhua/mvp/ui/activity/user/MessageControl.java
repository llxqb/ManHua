package com.shushan.manhua.mvp.ui.activity.user;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/11/14.
 */

public class MessageControl {
    public interface MessageView extends LoadDataView {
    }

    public interface PresenterMessage extends Presenter<MessageView> {

    }

}
