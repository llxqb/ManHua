package com.shushan.manhua.mvp.ui.fragment.bookshelf;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class BookShelfFragmentControl {
    public interface BookShelfView extends LoadDataView {

//        void getMineInfoSuccess(MineInfoResponse mineInfoResponse);
    }

    public interface BookShelfFragmentPresenter extends Presenter<BookShelfView> {
//        /**
//         * 查询个人信息
//         */
//        void onRequestPersonalInfo(TokenRequest tokenRequest);
        /**
         * 查询我的（包含购买的服务信息）
         */
//        void onRequestMineInfo(TokenRequest tokenRequest);

    }

}
