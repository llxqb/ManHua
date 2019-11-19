package com.shushan.manhua.mvp.ui.fragment.selection;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class SelectionFragmentControl {
    public interface SelectionView extends LoadDataView {

//        void getSelectionInfoSuccess(SelectionInfoResponse SelectionInfoResponse);
    }

    public interface SelectionFragmentPresenter extends Presenter<SelectionView> {
//        /**
//         * 查询个人信息
//         */
//        void onRequestPersonalInfo(TokenRequest tokenRequest);

    }

}
