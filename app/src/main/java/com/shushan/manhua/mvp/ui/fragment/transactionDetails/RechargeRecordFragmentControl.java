package com.shushan.manhua.mvp.ui.fragment.transactionDetails;


import com.shushan.manhua.entity.request.RechargeRecordRequest;
import com.shushan.manhua.entity.response.RechargeRecordResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class RechargeRecordFragmentControl {
    public interface RechargeRecordView extends LoadDataView {
        void getRechargeRecordSuccess(RechargeRecordResponse  rechargeRecordResponse);
    }

    public interface RechargeRecordFragmentPresenter extends Presenter<RechargeRecordView> {

        /**
         * 充值记录
         */
        void onRequestRechargeRecord(RechargeRecordRequest rechargeRecordRequest);
    }

}
