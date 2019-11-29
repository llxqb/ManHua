package com.shushan.manhua.mvp.ui.fragment.mine;


import com.shushan.manhua.entity.request.MineRequest;
import com.shushan.manhua.entity.response.MineInfoResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class MineFragmentControl {
    public interface MineView extends LoadDataView {

        void getMineInfoSuccess(MineInfoResponse mineInfoResponse);
    }

    public interface MineFragmentPresenter extends Presenter<MineView> {
        /**
         * 查询我的
         */
        void onRequestMineInfo(MineRequest mineRequest);

    }

}
