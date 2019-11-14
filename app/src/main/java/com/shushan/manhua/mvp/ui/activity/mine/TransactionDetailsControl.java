package com.shushan.manhua.mvp.ui.activity.mine;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/11/14.
 */

public class TransactionDetailsControl {
    public interface TransactionDetailsView extends LoadDataView {
    }

    public interface PresenterTransactionDetails extends Presenter<TransactionDetailsView> {

    }

}
