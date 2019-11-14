package com.shushan.manhua.mvp.ui.activity.mine;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2017/12/13.
 */

public class FeedbackControl {
    public interface FeedbackView extends LoadDataView {
    }

    public interface PresenterFeedback extends Presenter<FeedbackView> {

    }

}
