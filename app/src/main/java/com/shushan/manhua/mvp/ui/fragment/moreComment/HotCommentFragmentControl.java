package com.shushan.manhua.mvp.ui.fragment.moreComment;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class HotCommentFragmentControl {
    public interface HotCommentView extends LoadDataView {

//        void getHotCommentInfoSuccess(HotCommentInfoResponse HotCommentInfoResponse);
    }

    public interface HotCommentFragmentPresenter extends Presenter<HotCommentView> {
//        /**
//         * 查询个人信息
//         */
//        void onRequestPersonalInfo(TokenRequest tokenRequest);

    }

}
