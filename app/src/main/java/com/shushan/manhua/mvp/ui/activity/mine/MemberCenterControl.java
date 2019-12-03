package com.shushan.manhua.mvp.ui.activity.mine;


import com.shushan.manhua.entity.request.CreateOrderRequest;
import com.shushan.manhua.entity.request.MemberCenterRequest;
import com.shushan.manhua.entity.request.ReceiovedBeanByVipRequest;
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
         * 创建订单
         */
        void onRequestCreateOrder(CreateOrderRequest createOrderRequest);
    }

}
