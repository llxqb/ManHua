package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.ReadRecordingRequest;
import com.shushan.manhua.entity.request.ReadingRequest;
import com.shushan.manhua.entity.request.SelectionRequest;
import com.shushan.manhua.entity.response.ReadingInfoResponse;
import com.shushan.manhua.entity.response.SelectionResponse;

/**
 * 阅读页面
 */
public class ReadActivity extends ReadBaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        super.initView();
        onRequestReadingInfo();
        onRequestSelectionInfo();

    }

    @Override
    public void initData() {
        super.initData();
    }

    /**
     * 章节详情
     */
    private void onRequestReadingInfo() {
        ReadingRequest readingRequest = new ReadingRequest();
        readingRequest.token = mBuProcessor.getToken();
        readingRequest.book_id = mBookId;
        readingRequest.catalogue_id = "1";//String.valueOf(mCatalogueId)
        mPresenter.onRequestReadingInfo(readingRequest);
    }

    @Override
    public void getReadingInfoSuccess(ReadingInfoResponse readingInfoResponse) {
        mReadingInfoResponse = readingInfoResponse;
        ReadingInfoResponse.CatalogueBean catalogueBean = readingInfoResponse.getCatalogue();
        mReadingPicAdapter.setNewData(catalogueBean.getCatalogue_content());
        mReadingCommentAdapter.setNewData(readingInfoResponse.getComment());
        mRecommendAdapter.setNewData(readingInfoResponse.getCommend());
        bannerList = readingInfoResponse.getBanner();
        initBanner();
        String supportValue = getString(R.string.ReadActivity_support) + " " + (mReadingInfoResponse.getCatalogue().getLike());
        mSupportTv.setText(supportValue);
        if (catalogueBean.getIs_like() == 0) {
            setNoSupportState();
        } else {
            setSupportState();
        }
        //是否免费
        if (readingInfoResponse.getCatalogue().getType() != 0) {

        }

    }

    /**
     * 上传阅读记录
     */
    private void onRequestReadRecording() {
        ReadRecordingRequest readRecordingRequest = new ReadRecordingRequest();
        readRecordingRequest.token = mBuProcessor.getToken();
        readRecordingRequest.book_id = mBookId;
        readRecordingRequest.catalogue_id = String.valueOf(mCatalogueId);
        readRecordingRequest.type = String.valueOf(mReadingInfoResponse.getCatalogue().getType());
//        readRecordingRequest.bean =  TODO VIP he fei VIP
        mPresenter.onRequestReadRecording(readRecordingRequest);
    }

    /**
     * 点赞成功
     */
    @Override
    public void getSupportSuccess() {
        String supportValue = getString(R.string.ReadActivity_support) + " " + (mReadingInfoResponse.getCatalogue().getLike() + 1);
        mSupportTv.setText(supportValue);
        setSupportState();
    }

    /**
     * 加入书架成功
     */
    @Override
    public void getAddBookShelfSuccess() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_BOOKSHELF));
    }


    /**
     * 请求漫画选集信息
     */
    private void onRequestSelectionInfo() {
        SelectionRequest selectionRequest = new SelectionRequest();
        selectionRequest.token = mBuProcessor.getToken();
        selectionRequest.book_id = mBookId;
        selectionRequest.orderby = "asc";
        selectionRequest.page = String.valueOf(page);
        selectionRequest.pagesize = String.valueOf(Constant.PAGESIZE);
        mPresenter.onRequestSelectionInfo(selectionRequest);
    }

    /**
     * 请求漫画选集信息成功
     */
    @Override
    public void getSelectionInfoSuccess(SelectionResponse selectionResponse) {
        mSelectionResponse = selectionResponse;
    }

}
