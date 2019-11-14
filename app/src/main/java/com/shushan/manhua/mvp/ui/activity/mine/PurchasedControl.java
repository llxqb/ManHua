package com.shushan.manhua.mvp.ui.activity.mine;


import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2017/12/13.
 */

public class PurchasedControl {
    public interface PurchasedView extends LoadDataView {
    }

    public interface PresenterPurchased extends Presenter<PurchasedView> {

    }

}
