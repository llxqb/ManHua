package com.shushan.manhua.mvp.ui.activity.mine;


import com.shushan.manhua.entity.request.ReceiveTaskRequest;
import com.shushan.manhua.entity.request.RecommendRequest;
import com.shushan.manhua.entity.request.SignDataRequest;
import com.shushan.manhua.entity.request.SignRequest;
import com.shushan.manhua.entity.response.RecommendResponse;
import com.shushan.manhua.entity.response.SignDataResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2017/12/13.
 */

public class CheckInControl {
    public interface CheckInView extends LoadDataView {
        void getSignDataSuccess(SignDataResponse signDataResponse);

        void getRecommendInfoSuccess(RecommendResponse recommendResponse);

        void getSignSuccess();

        void getReceiveTaskSuccess();
    }

    public interface PresenterCheckIn extends Presenter<CheckInView> {
        /**
         * 请求签到数据
         */
        void onRequestSignData(SignDataRequest signDataRequest);

        /**
         * 请求推荐数据
         */
        void onRecommendInfo(RecommendRequest recommendRequest);

        /**
         * 签到
         */
        void onRequestSign(SignRequest signRequest);
        /**
         * 领取任务
         */
        void onRequestReceiveTask(ReceiveTaskRequest receiveTaskRequest);
    }

}
