package com.shushan.manhua.mvp.ui.activity.mine;


import com.shushan.manhua.entity.request.SubmitFeedbackRequest;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2017/12/13.
 */

public class FeedbackControl {
    public interface FeedbackView extends LoadDataView {
        void getSubmitFeedbackSuccess();
    }

    public interface PresenterFeedback extends Presenter<FeedbackView> {

        /**
         * 提交辅导反馈
         */
        void onSubmitFeedbackRequest(SubmitFeedbackRequest submitFeedbackRequest);
    }

}
