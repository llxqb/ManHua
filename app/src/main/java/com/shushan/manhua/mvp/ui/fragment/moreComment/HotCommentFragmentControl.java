package com.shushan.manhua.mvp.ui.fragment.moreComment;


import com.shushan.manhua.entity.CommentListBean;
import com.shushan.manhua.entity.request.CommentRequest;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class HotCommentFragmentControl {
    public interface HotCommentView extends LoadDataView {

        void getCommentInfoSuccess(CommentListBean commentListBean);

    }

    public interface HotCommentFragmentPresenter extends Presenter<HotCommentView> {
        /**
         * 查询评论列表
         */
        void onRequestCommentInfo(CommentRequest commentRequest);

    }

}
