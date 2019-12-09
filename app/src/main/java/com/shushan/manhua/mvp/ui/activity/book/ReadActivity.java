package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ScrollView;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.BarrageListRequest;
import com.shushan.manhua.entity.response.BarrageListResponse;
import com.shushan.manhua.entity.response.ReadingInfoResponse;

/**
 * 阅读页面
 */
public class ReadActivity extends ReadBaseActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        super.initView();
        onRequestBarrageList();
        onRequestBuyBarrageStyle();//请求购买的弹幕样式
    }

    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(ActivityConstant.PAY_SUCCESS)) {//购买成功更新数据
                mUser = mBuProcessor.getUser();
                if (mReadUseCoinDialog != null) {
                    mReadUseCoinDialog.closeDialog();
                    setIsRecharge();//重新判断
                }
                onRequestBuyBarrageStyle();
            } else if (intent.getAction().equals(ActivityConstant.LOGIN_SUCCESS_UPDATE_DATA)) {//登录成功
                mLoginModel = mBuProcessor.getLoginModel();
            } else if (intent.getAction().equals(ActivityConstant.UPDATE_BARRAGE_SETTING)) {//更新了弹幕设置
                mBarrageFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_BARRAGE);
                mTurnPageFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_TURN_PAGE);
                mNightModelFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_NIGHT_MODEL);
                if (mBarrageFlag) {
                    mBarrageIv.setImageResource(R.mipmap.barrage_open);
                } else {
                    mBarrageIv.setImageResource(R.mipmap.barrage_close);
                }
                sheClickHiddenLayout(mTurnPageFlag);//可以点击上下翻页
                if (mReadSettingPopupWindow != null) {
                    mReadSettingPopupWindow.updateSetting();
                }
            }
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.PAY_SUCCESS);
        mFilter.addAction(ActivityConstant.LOGIN_SUCCESS_UPDATE_DATA);
        mFilter.addAction(ActivityConstant.UPDATE_BARRAGE_SETTING);
    }


    @Override
    public void initData() {
        super.initData();
        mTurnPageFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_TURN_PAGE, true);
        mNightModelFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_NIGHT_MODEL, false);
        mBarrageFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_BARRAGE, true);
        mTransparency = mSharePreferenceUtil.getIntData(Constant.TRANSPARENCY, 80);
        mPlaySpeed = mSharePreferenceUtil.getIntData(Constant.PLAY_SPEED, 80);
        if (mBarrageFlag) {
            mBarrageIv.setImageResource(R.mipmap.barrage_open);
        } else {
            mBarrageIv.setImageResource(R.mipmap.barrage_close);
        }
        sheClickHiddenLayout(false);//默认不可点击上下翻页
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
        setIsRecharge();
        mCommonTitleTv.setText(catalogueBean.getCatalogue_name());
        mNestedScrollView.post(() -> mNestedScrollView.post(() -> {
            picRvHeight = mPicRecyclerView.getHeight();
            mNestedScrollView.fullScroll(ScrollView.FOCUS_UP);  // 滚动至顶部
        }));
    }

    private void setIsRecharge() {
        //是否免费 0 免费 1 收费1
        if (mReadingInfoResponse.getCatalogue().getType() != 0) {//收费
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
     * 收费章节
     * 取消兑换阅读
     */
    @Override
    public void cancelReadingBtnOkListener() {
        finish();
    }

    /**
     * 切换章节
     */
    @Override
    public void clickChapterBtnListener(int chapterId) {
        mCatalogueId = chapterId;
        onRequestReadingInfo();
    }


}
