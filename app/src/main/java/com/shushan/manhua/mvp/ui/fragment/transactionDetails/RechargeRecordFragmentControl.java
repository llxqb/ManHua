package com.shushan.manhua.mvp.ui.fragment.transactionDetails;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class RechargeRecordFragmentControl {
    public interface RechargeRecordView extends LoadDataView {
    }

    public interface RechargeRecordFragmentPresenter extends Presenter<RechargeRecordView> {

    }

}
