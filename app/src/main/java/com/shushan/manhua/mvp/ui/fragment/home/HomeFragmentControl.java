package com.shushan.manhua.mvp.ui.fragment.home;


import com.shushan.manhua.entity.request.HomeInfoRequest;
import com.shushan.manhua.entity.response.HomeResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class HomeFragmentControl {
    public interface HomeView extends LoadDataView {
        void  getHomeInfoSuccess(HomeResponse homeResponse);
    }

    public interface homeFragmentPresenter extends Presenter<HomeView> {
        /**
         * 请求首页信息
         */
        void onRequestHomeInfo(HomeInfoRequest homeInfoRequest);

    }


}
