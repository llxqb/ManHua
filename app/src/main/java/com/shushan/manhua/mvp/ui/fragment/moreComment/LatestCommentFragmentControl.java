package com.shushan.manhua.mvp.ui.fragment.moreComment;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class LatestCommentFragmentControl {
    public interface LatestCommentView extends LoadDataView {

//        void getLatestCommentInfoSuccess(LatestCommentInfoResponse LatestCommentInfoResponse);
    }

    public interface LatestCommentFragmentPresenter extends Presenter<LatestCommentView> {
//        /**
//         * 查询个人信息
//         */
//        void onRequestPersonalInfo(TokenRequest tokenRequest);

    }

}
