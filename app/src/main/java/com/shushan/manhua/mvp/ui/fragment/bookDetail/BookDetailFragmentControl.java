package com.shushan.manhua.mvp.ui.fragment.bookDetail;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class BookDetailFragmentControl {
    public interface BookDetailView extends LoadDataView {

//        void getBookDetailInfoSuccess(BookDetailInfoResponse BookDetailInfoResponse);
    }

    public interface BookDetailFragmentPresenter extends Presenter<BookDetailView> {
//        /**
//         * 查询个人信息
//         */
//        void onRequestPersonalInfo(TokenRequest tokenRequest);

    }

}
