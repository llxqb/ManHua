package com.shushan.manhua.mvp.ui.activity.user;


import com.shushan.manhua.entity.request.PersonalInfoRequest;
import com.shushan.manhua.entity.request.UpdatePersonalInfoRequest;
import com.shushan.manhua.entity.request.UploadImage;
import com.shushan.manhua.entity.response.PersonalInfoResponse;
import com.shushan.manhua.mvp.presenter.LoadDataView;
import com.shushan.manhua.mvp.presenter.Presenter;

/**
 * Created by li.liu on 2019/11/14.
 */

public class PersonalInfoControl {
    public interface PersonalInfoView extends LoadDataView {
        void getPersonalInfoResponse(PersonalInfoResponse personalInfoResponse);

        void getUploadPicSuccess(String picPath);

        void getUpdatePersonalInfoSuccess();
    }

    public interface PresenterPersonalInfo extends Presenter<PersonalInfoView> {

        /**
         * 查询个人基本信息
         */
        void onRequestPersonalInfo(PersonalInfoRequest personalInfoRequest);

        /**
         * 上传图片
         */
        void uploadImageRequest(UploadImage uploadPicRequest);

        /**
         * 更新个人基本信息
         */
        void updatePersonalInfoRequest(UpdatePersonalInfoRequest updatePersonalInfoRequest);
    }

}
