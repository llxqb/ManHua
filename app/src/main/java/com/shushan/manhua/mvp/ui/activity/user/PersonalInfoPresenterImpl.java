package com.shushan.manhua.mvp.ui.activity.user;

import android.content.Context;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.request.PersonalInfoRequest;
import com.shushan.manhua.entity.request.UpdatePersonalInfoRequest;
import com.shushan.manhua.entity.request.UploadImage;
import com.shushan.manhua.entity.response.PersonalInfoResponse;
import com.shushan.manhua.help.RetryWithDelay;
import com.shushan.manhua.mvp.model.ResponseData;
import com.shushan.manhua.mvp.model.UserModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;


/**
 * Created by li.liu on 2017/4/27.
 * PresenterLoginImpl
 */

public class PersonalInfoPresenterImpl implements PersonalInfoControl.PresenterPersonalInfo {

    private PersonalInfoControl.PersonalInfoView mPersonalInfoView;
    private final UserModel mUserModel;
    private final Context mContext;

    @Inject
    public PersonalInfoPresenterImpl(Context context, UserModel model, PersonalInfoControl.PersonalInfoView view) {
        mContext = context;
        mUserModel = model;
        mPersonalInfoView = view;
    }


    /**
     * 查询个人基本信息
     */
    @Override
    public void onRequestPersonalInfo(PersonalInfoRequest personalInfoRequest) {
        mPersonalInfoView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mUserModel.onRequestPersonalInfo(personalInfoRequest).compose(mPersonalInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::requestPersonalInfoSuccess, throwable -> mPersonalInfoView.showErrMessage(throwable),
                        () -> mPersonalInfoView.dismissLoading());
        mPersonalInfoView.addSubscription(disposable);
    }

    /**
     * 查询个人基本信息 成功
     */
    private void requestPersonalInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 0) {
            responseData.parseData(PersonalInfoResponse.class);
            if (responseData.parsedData != null) {
                PersonalInfoResponse response = (PersonalInfoResponse) responseData.parsedData;
                mPersonalInfoView.getPersonalInfoResponse(response);
            }
        } else {
            mPersonalInfoView.showToast(responseData.errorMsg);
        }
    }


    /**
     * 上传图片
     */
    @Override
    public void uploadImageRequest(UploadImage uploadPicRequest) {
        mPersonalInfoView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mUserModel.uploadImageRequest(uploadPicRequest).compose(mPersonalInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::uploadPicSuccess, throwable -> mPersonalInfoView.showErrMessage(throwable),
                        () -> mPersonalInfoView.dismissLoading());
        mPersonalInfoView.addSubscription(disposable);
    }

    /**
     * 上传图片 成功
     */
    private void uploadPicSuccess(ResponseData responseData) {
        mPersonalInfoView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mPersonalInfoView.getUploadPicSuccess(responseData.result);
        } else {
            mPersonalInfoView.showToast(responseData.errorMsg);
        }
    }
    /**
     * 更新个人基本信息
     */
    @Override
    public void updatePersonalInfoRequest(UpdatePersonalInfoRequest updatePersonalInfoRequest) {
        mPersonalInfoView.showLoading(mContext.getResources().getString(R.string.loading));
        Disposable disposable = mUserModel.updatePersonalInfoRequest(updatePersonalInfoRequest).compose(mPersonalInfoView.applySchedulers()).retryWhen(new RetryWithDelay(3, 3000))
                .subscribe(this::updatePersonalInfoRequestSuccess, throwable -> mPersonalInfoView.showErrMessage(throwable),
                        () -> mPersonalInfoView.dismissLoading());
        mPersonalInfoView.addSubscription(disposable);
    }

    /**
     * 更新个人基本信息 成功
     */
    private void updatePersonalInfoRequestSuccess(ResponseData responseData) {
        mPersonalInfoView.judgeToken(responseData.resultCode);
        if (responseData.resultCode == 0) {
            mPersonalInfoView.getUpdatePersonalInfoSuccess();
        } else {
            mPersonalInfoView.showToast(responseData.errorMsg);
        }
    }



    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }


}
