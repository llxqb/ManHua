package com.shushan.manhua.mvp.ui.activity.book;


import com.shushan.manhua.entity.request.RankingRequest;
import com.shushan.manhua.entity.response.RankingResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/11/14.
 * 排行榜
 */

public class RankingControl {
    public interface RankingView extends LoadDataView {
        void getRankingSuccess(RankingResponse rankingResponse);
    }

    public interface PresenterRanking extends Presenter<RankingView> {
        /**
         * 排行榜数据
         */
        void onRequestRanking(RankingRequest rankingRequest);

    }

}
