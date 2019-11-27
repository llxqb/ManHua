package com.shushan.manhua.mvp.ui.activity.book;


import com.shushan.manhua.entity.request.BookShelfInfoRequest;
import com.shushan.manhua.entity.request.DeleteBookShelfRequest;
import com.shushan.manhua.entity.response.BookShelfResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/11/14.
 */

public class LongDeleteControl {
    public interface LongDeleteView extends LoadDataView {
        void getDeleteBookShelfSuccess();

        void getBookShelfInfoSuccess(BookShelfResponse bookShelfResponse);
    }

    public interface PresenterLongDelete extends Presenter<LongDeleteView> {

        /**
         * 删除书架漫画
         */
        void onRequestDeleteBook(DeleteBookShelfRequest deleteBookShelfRequest);
        /**
         * 查询书架漫画
         */
        void onRequestBookShelfInfo(BookShelfInfoRequest bookShelfInfoRequest);
    }

}
