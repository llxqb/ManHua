package com.shushan.manhua.mvp.ui.fragment.bookDetail;


import com.shushan.manhua.entity.request.BookDetailRequest;
import com.shushan.manhua.entity.response.BookDetailInfoResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class BookDetailFragmentControl {
    public interface BookDetailView extends LoadDataView {

        void getBookDetailInfoSuccess(BookDetailInfoResponse bookDetailInfoResponse);

    }

    public interface BookDetailFragmentPresenter extends Presenter<BookDetailView> {
        /**
         * 查询书籍详情
         */
        void onRequestBookDetailInfo(BookDetailRequest bookDetailRequest);

    }

}
