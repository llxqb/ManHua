package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.BarrageListRequest;
import com.shushan.manhua.entity.request.BuyBarrageStyleRequest;
import com.shushan.manhua.entity.request.SelectionRequest;
import com.shushan.manhua.entity.response.BarrageListResponse;
import com.shushan.manhua.entity.response.BuyBarrageStyleResponse;
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
        onRequestSelectionInfo();
        onRequestBarrageList();
        onRequestBuyBarrageStyle();//请求购买的弹幕样式
    }

    @Override
    public void initData() {
        super.initData();
        mBarrageFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_BARRAGE);
        mTurnPageFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_TURN_PAGE);
        mNightModelFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_NIGHT_MODEL);
        if (mBarrageFlag) {
            mBarrageIv.setImageResource(R.mipmap.barrage_open);
        } else {
            mBarrageIv.setImageResource(R.mipmap.barrage_close);
        }
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
        //是否免费 0 免费 1 收费1
        if (readingInfoResponse.getCatalogue().getType() != 0) {//收费
            if (mUser.vip == 0) {//非VIP
                if (mUser.bean >= 5) {
                    onRequestReadRecording(5);//消耗漫豆
                } else {
                    //进行弹框
                    showRechargeDialog();
                }
            } else {
                if (mUser.bean >= 3) {
                    onRequestReadRecording(3);
                } else {
                    //进行弹框
                    showRechargeDialog();
                }
            }
        } else {
            onRequestReadRecording(0);
        }

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


    /**
     * 兑换弹幕样式成功
     */
    @Override
    public void getExchangeBarrageStyleSuccess() {
        showBarrageStyle();
    }

    /**
     * 请求弹幕列表
     */
    private void onRequestBarrageList() {
        BarrageListRequest barrageListRequest = new BarrageListRequest();
        barrageListRequest.token = mBuProcessor.getToken();
        barrageListRequest.book_id = mBookId;
        barrageListRequest.catalogue_id = String.valueOf(mCatalogueId);
        mPresenter.getBarrageListRequest(barrageListRequest);
    }

    /**
     * 获取弹幕列表成功
     */
    @Override
    public void getBarrageListSuccess(BarrageListResponse barrageListResponse) {
        mBarrageListResponse = barrageListResponse;
//        new BarrageTextPopupWindow(this).initPopWindow(mReadLayout);
//        BarrageTextDialog barrageTextDialog = BarrageTextDialog.newInstance();
//        DialogFactory.showDialogFragment(getSupportFragmentManager(), barrageTextDialog, BarrageTextDialog.TAG);
    }


    /**
     * 请求购买的弹幕样式
     */
    private void onRequestBuyBarrageStyle() {
        BuyBarrageStyleRequest buyBarrageStyleRequest = new BuyBarrageStyleRequest();
        buyBarrageStyleRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestBuyBarrageStyle(buyBarrageStyleRequest);
    }

    /**
     * 请求购买的弹幕样式 成功
     */
    @Override
    public void getBuyBarrageStyleSuccess(BuyBarrageStyleResponse buyBarrageStyleResponse) {
        mBuyBarrageStyleResponse = buyBarrageStyleResponse;
    }


    /**
     * 发布评论成功
     */
    @Override
    public void getPublishCommentSuccess() {

    }

    /**
     * 收费章节
     * 取消兑换阅读
     */
    @Override
    public void cancelReadingBtnOkListener() {
        finish();
    }


}
