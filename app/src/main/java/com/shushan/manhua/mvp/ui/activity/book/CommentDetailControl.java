package com.shushan.manhua.mvp.ui.activity.book;


import com.shushan.manhua.entity.request.CommentDetailRequest;
import com.shushan.manhua.entity.request.PublishCommentUserRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.response.CommentDetailResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/11/14.
 */

public class CommentDetailControl {
    public interface CommentDetailView extends LoadDataView {
        void getCommentDetailSuccess(CommentDetailResponse commentDetailResponse);

        void getSupportSuccess();

        void getCommentUserSuccess();
    }

    public interface PresenterCommentDetail extends Presenter<CommentDetailView> {
        /**
         * 评论详情
         */
        void onRequestCommentDetail(CommentDetailRequest commentDetailRequest);

        /**
         * 评论点赞
         */
        void onSupportRequest(SupportRequest supportRequest);

        /**
         * 评论用户评论
         */
        void onPublishCommentUser(PublishCommentUserRequest publishCommentUserRequest);
    }

}
