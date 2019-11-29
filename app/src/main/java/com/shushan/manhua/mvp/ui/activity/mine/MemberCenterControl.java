package com.shushan.manhua.mvp.ui.activity.mine;


import com.shushan.manhua.entity.request.MemberCenterRequest;
import com.shushan.manhua.entity.response.MemberCenterResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2017/12/13.
 */

public class MemberCenterControl {
    public interface MemberCenterView extends LoadDataView {
        void  getMemberCenterResponse(MemberCenterResponse memberCenterResponse);
    }

    public interface PresenterMemberCenter extends Presenter<MemberCenterView> {

        /**
         * 请求会员中心
         */
        void onRequestMemberCenter(MemberCenterRequest memberCenterRequest);
    }

}
