package com.shushan.manhua.mvp.ui.fragment.transactionDetails;


import com.shushan.manhua.entity.request.RechargeRecordRequest;
import com.shushan.manhua.entity.response.ExpensesRecordResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class ExpensesRecordFragmentControl {
    public interface ExpensesRecordView extends LoadDataView {
        void getExpensesRecordSuccess(ExpensesRecordResponse expensesRecordResponse);
    }

    public interface ExpensesRecordFragmentPresenter extends Presenter<ExpensesRecordView> {
        /**
         * 消费记录
         */
        void onRequestRechargeRecord(RechargeRecordRequest rechargeRecordRequest);
    }

}
