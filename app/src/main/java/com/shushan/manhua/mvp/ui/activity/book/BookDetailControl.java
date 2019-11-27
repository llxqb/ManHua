package com.shushan.manhua.mvp.ui.activity.book;


import com.shushan.manhua.entity.request.AddBookShelfRequest;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/11/14.
 */

public class BookDetailControl {
    public interface BookDetailView extends LoadDataView {
        void getBookShelfSuccess();
    }

    public interface PresenterBookDetail extends Presenter<BookDetailView> {

        /**
         * 加入书架
         */
        void onAddBookShelfRequest(AddBookShelfRequest addBookShelfRequest);
    }

}
