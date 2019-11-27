package com.shushan.manhua.mvp.ui.fragment.moreComment;


import com.shushan.manhua.entity.CommentListBean;
import com.shushan.manhua.entity.request.CommentRequest;
import com.shushan.manhua.entity.request.PublishCommentRequest;
import com.shushan.manhua.entity.request.UploadImage;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class HotCommentFragmentControl {
    public interface HotCommentView extends LoadDataView {
        void getCommentInfoSuccess(CommentListBean commentListBean);

        void getUploadPicSuccess(String picPath);

        void getPublishCommentSuccess();
    }

    public interface HotCommentFragmentPresenter extends Presenter<HotCommentView> {
        /**
         * 查询评论列表
         */
        void onRequestCommentInfo(CommentRequest commentRequest);

        /**
         * 上传图片
         */
        void uploadImageRequest(UploadImage uploadPicRequest);
        /**
         * 发布评论
         */
        void onRequestPublishComment(PublishCommentRequest publishCommentRequest);

    }

}
