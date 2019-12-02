package com.shushan.manhua.mvp.ui.activity.mine;


import com.shushan.manhua.entity.constants.VoucherCenterResponse;
import com.shushan.manhua.entity.request.CreateOrderRequest;
import com.shushan.manhua.entity.request.VoucherCenterRequest;
import com.shushan.manhua.entity.response.CreateOrderResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2017/12/13.
 */

public class BuyControl {
    public interface BuyView extends LoadDataView {
        void getVoucherCenterSuccess(VoucherCenterResponse voucherCenterResponse);

        void getCreateOrderGoogleSuccess(CreateOrderResponse createOrderResponse);
    }

    public interface PresenterBuy extends Presenter<BuyView> {

        /**
         * 充值中心
         */
        void onRequestVoucherCenter(VoucherCenterRequest voucherCenterRequest);

        /**
         * 创建订单
         */
        void onRequestCreateOrder(CreateOrderRequest createOrderRequest);
    }

}
