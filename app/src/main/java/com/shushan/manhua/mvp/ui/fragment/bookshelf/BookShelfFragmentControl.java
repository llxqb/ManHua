package com.shushan.manhua.mvp.ui.fragment.bookshelf;


import com.shushan.manhua.entity.request.BookShelfInfoRequest;
import com.shushan.manhua.entity.response.BookShelfResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class BookShelfFragmentControl {
    public interface BookShelfView extends LoadDataView {
        void getBookShelfInfoSuccess(BookShelfResponse bookShelfResponse);
    }

    public interface BookShelfFragmentPresenter extends Presenter<BookShelfView> {
        /**
         * 请求书架数据
         */
        void onRequestBookShelfInfo(BookShelfInfoRequest bookShelfInfoRequest);

    }

}
