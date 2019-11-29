package com.shushan.manhua.mvp.ui.activity.mine;


import com.shushan.manhua.entity.request.PurchasedBookRequest;
import com.shushan.manhua.entity.response.PurchasedResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2017/12/13.
 */

public class PurchasedControl {
    public interface PurchasedView extends LoadDataView {
        void  getPurchasedBookSuccess(PurchasedResponse purchasedResponse);
    }

    public interface PresenterPurchased extends Presenter<PurchasedView> {

        /**
         * 已购漫画
         */
        void onRequestPurchasedBook(PurchasedBookRequest purchasedBookRequest);
    }

}
