package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.VoucherCenterResponse;
import com.shushan.manhua.entity.request.VoucherCenterRequest;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.MineModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class BuyPresenterImpl implements BuyControl.PresenterBuy {

    private BuyControl.BuyView mBuyView;
    private final MineModel mMineModel;
    private final Context mContext;

    @Inject
    public BuyPresenterImpl(Context context, MineModel model, BuyControl.BuyView view) {
        mContext = context;
        mMineModel = model;
        mBuyView = view;
    }


    /**
     * 充值中心
     */
    @Override
    public void onRequestVoucherCenter(VoucherCenterRequest voucherCenterRequest) {
        mBuyView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestVoucherCenter(voucherCenterRequest).compose(mBuyView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestVoucherCenterSuccess, throwable -> mBuyView.showErrMessage(throwable),
                        () -> mBuyView.dismissLoading());
        mBuyView.addSubscription(disposable);
    }

    /**
     * 充值中心 成功
     */
    private void requestVoucherCenterSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(VoucherCenterResponse.class);
            if (responseData.parsedData != null) {
                VoucherCenterResponse response = (VoucherCenterResponse) responseData.parsedData;
                mBuyView.getVoucherCenterSuccess(response);
            }
        } else {
            mBuyView.showToast(responseData.errorMsg);
        }
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
