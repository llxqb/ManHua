package com.shushan.manhua.mvp.ui.fragment.mine;


import com.shushan.manhua.entity.request.MineRequest;
import com.shushan.manhua.entity.request.ScoreFinishRequest;
import com.shushan.manhua.entity.request.UnReadMessageRequest;
import com.shushan.manhua.entity.response.MineInfoResponse;
import com.shushan.manhua.entity.response.UnReadMessageResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/05/28.
 */

public class MineFragmentControl {
    public interface MineView extends LoadDataView {

        void getMineInfoSuccess(MineInfoResponse mineInfoResponse);

        void getUnReadMessageSuccess(UnReadMessageResponse unReadMessageResponse);
    }

    public interface MineFragmentPresenter extends Presenter<MineView> {
        /**
         * 查询我的
         */
        void onRequestMineInfo(MineRequest mineRequest);

        /**
         * 查询是否有未读消息
         */
        void onRequestUnReadMessage(UnReadMessageRequest unReadMessageRequest);

        /**
         * 评分完成
         */
        void onRequestScoreFinish(ScoreFinishRequest scoreFinishRequest);
    }

}
