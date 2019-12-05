package com.shushan.manhua.mvp.ui.activity.mine;


import com.shushan.manhua.entity.request.CreateOrderRequest;
import com.shushan.manhua.entity.request.MemberCenterRequest;
import com.shushan.manhua.entity.request.PayFinishAHDIRequest;
import com.shushan.manhua.entity.request.PayFinishByUniPinRequest;
import com.shushan.manhua.entity.request.PayFinishUploadRequest;
import com.shushan.manhua.entity.request.ReceiovedBeanByVipRequest;
import com.shushan.manhua.entity.request.RequestOrderAHDIRequest;
import com.shushan.manhua.entity.request.RequestOrderUniPinPayRequest;
import com.shushan.manhua.entity.response.CreateOrderAHDIResponse;
import com.shushan.manhua.entity.response.CreateOrderByUniPinResponse;
import com.shushan.manhua.entity.response.CreateOrderResponse;
import com.shushan.manhua.entity.response.MemberCenterResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2017/12/13.
 */

public class MemberCenterControl {
    public interface MemberCenterView extends LoadDataView {
        void  getMemberCenterResponse(MemberCenterResponse memberCenterResponse);

        void getReceivedBeanByVipSuccess();

        void getCreateOrderGoogleSuccess(CreateOrderResponse createOrderResponse);

        void createOrderAHDISuccess(CreateOrderAHDIResponse createOrderAHDIResponse);

        void createOrderByUniPinSuccess(CreateOrderByUniPinResponse createOrderByUniPinResponse);

        void getPayFinishGoogleUploadSuccess();
        void getPayFinishGoogleUploadFail(String error);
        void getPayFinishGoogleUploadThowable();

        void getPayFinishAHDIUploadSuccess();
        void getPayFinishAHDIUploadFail(String error);
        void getPayFinishAHDIUploadThowable();

        void getPayFinishUploadByUniPinSuccess();
        void getPayFinishUploadByUniPinFail(String error);
        void getPayFinishUploadByUniPinThowable();
    }

    public interface PresenterMemberCenter extends Presenter<MemberCenterView> {

        /**
         * 请求会员中心
         */
        void onRequestMemberCenter(MemberCenterRequest memberCenterRequest);

        /**
         * VIP每日领取漫豆
         */
        void onRequestReceivedBeanByVip(ReceiovedBeanByVipRequest receiovedBeanByVipRequest);
        /**
         *  Google 创建订单
         */
        void onRequestCreateOrder(CreateOrderRequest createOrderRequest);

        /**
         * Google 支付成功上报
         */
        void onPayFinishUpload(PayFinishUploadRequest payFinishUpload);

        /**
         * AHDI支付创建订单
         */
        void onRequestCreateOrderAHDI(RequestOrderAHDIRequest requestOrderAHDIRequest);
        /**
         * AHDI支付上报（查询是否已经支付完成）
         */
        void onPayFinishAHDIUpload(PayFinishAHDIRequest payFinishAHDIRequest);

        /**
         * UniPin支付创建订单
         */
        void onRequestCreateOrderByUniPin(RequestOrderUniPinPayRequest requestOrderUniPinPayRequest);

        /**
         * UniPin支付上报，不管支付成功或者失败返回RechargeActivity进行上报
         */
        void onPayFinishUploadByUniPin(PayFinishByUniPinRequest payFinishByUniPinRequest);
    }

}
