package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Context;

import com.google.gson.Gson;
import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.PurchasedBookRequest;
import com.shushan.manhua.entity.response.PurchasedResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.MineModel;
import com.shushan.manhua.mvp.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class PurchasedPresenterImpl implements PurchasedControl.PresenterPurchased {

    private PurchasedControl.PurchasedView mPurchasedView;
    private final MineModel mMineModel;
    private final Context mContext;

    @Inject
    public PurchasedPresenterImpl(Context context, MineModel model, PurchasedControl.PurchasedView view) {
        mContext = context;
        mMineModel = model;
        mPurchasedView = view;
    }


    /**
     * 已购漫画
     */
    @Override
    public void onRequestPurchasedBook(PurchasedBookRequest purchasedBookRequest) {
        mPurchasedView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mMineModel.onRequestPurchasedBook(purchasedBookRequest).compose(mPurchasedView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::purchasedBookSuccess, throwable -> mPurchasedView.showErrMessage(throwable),
                        () -> mPurchasedView.dismissLoading());
        mPurchasedView.addSubscription(disposable);
    }

    /**
     * 已购漫画 成功
     */
    private void purchasedBookSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
//            responseData.parseData(PurchasedResponse.class);
//            if (responseData.parsedData != null) {
//                PurchasedResponse response = (PurchasedResponse) responseData.parsedData;
//                mPurchasedView.getPurchasedBookSuccess(response);
//            }
            PurchasedResponse response = new Gson().fromJson(responseData.mJsonObject.toString(), PurchasedResponse.class);
            mPurchasedView.getPurchasedBookSuccess(response);
        } else {
            mPurchasedView.showToast(responseData.errorMsg);
        }
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
