package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.MemberCenterRequest;
import com.shushan.manhua.entity.response.MemberCenterResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.MineModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class MemberCenterPresenterImpl implements MemberCenterControl.PresenterMemberCenter {

    private MemberCenterControl.MemberCenterView mMemberCenterView;
    private final MineModel mMineModel;
    private final Context mContext;

    @Inject
    public MemberCenterPresenterImpl(Context context, MineModel model, MemberCenterControl.MemberCenterView view) {
        mContext = context;
        mMineModel = model;
        mMemberCenterView = view;
    }


    /**
     * 请求会员中心
     */
    @Override
    public void onRequestMemberCenter(MemberCenterRequest memberCenterRequest) {
        mMemberCenterView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestMemberCenter(memberCenterRequest).compose(mMemberCenterView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::memberCenterRequestSuccess, throwable -> mMemberCenterView.showErrMessage(throwable),
                        () -> mMemberCenterView.dismissLoading());
        mMemberCenterView.addSubscription(disposable);
    }

    /**
     * 请求会员中心数据 成功
     */
    private void memberCenterRequestSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(MemberCenterResponse.class);
            if (responseData.parsedData != null) {
                MemberCenterResponse response = (MemberCenterResponse) responseData.parsedData;
                mMemberCenterView.getMemberCenterResponse(response);
            }
        } else {
            mMemberCenterView.showToast(responseData.errorMsg);
        }
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
