package com.shushan.manhua.mvp.ui.activity.book;


import com.shushan.manhua.entity.request.BookClassificationRequest;
import com.shushan.manhua.entity.request.CommentDetailRequest;
import com.shushan.manhua.entity.request.PublishCommentUserRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.response.BookClassificationResponse;
import com.shushan.manhua.entity.response.CommentDetailResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/11/14.
 * 分类
 */

public class BookClassificationControl {
    public interface BookClassificationView extends LoadDataView {
        void getClassificationSuccess(BookClassificationResponse bookClassificationResponse);
    }

    public interface PresenterBookClassification extends Presenter<BookClassificationView> {
        /**
         * 分类  漫画/小说列表
         */
        void onRequestClassification(BookClassificationRequest bookClassificationRequest);

    }

}
