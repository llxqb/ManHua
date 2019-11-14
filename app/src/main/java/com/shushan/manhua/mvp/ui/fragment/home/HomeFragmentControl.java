package com.shushan.manhua.mvp.ui.fragment.home;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class HomeFragmentControl {
    public interface HomeView extends LoadDataView {
//        void getUnReadInfoSuccess(UnReadNewsResponse unReadNewsResponse);
    }

    public interface homeFragmentPresenter extends Presenter<HomeView> {
//        /**
//         * 查看是否有未读消息
//         */
//        void onRequestUnReadInfo(TokenRequest tokenRequest);

    }


}
