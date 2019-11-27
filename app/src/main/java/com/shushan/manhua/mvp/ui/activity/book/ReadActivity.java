package com.shushan.manhua.mvp.ui.activity.book;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.shushan.manhua.entity.BannerBean;
import com.shushan.manhua.entity.request.ReadingRequest;
import com.shushan.manhua.entity.response.ReadingInfoResponse;
import com.shushan.manhua.mvp.ui.adapter.BannerReadingViewHolder;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * 阅读页面
 */
public class ReadActivity extends ReadBaseActivity {

    List<BannerBean> bannerList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        super.initView();
        onRequestReadingInfo();
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
        ReadingInfoResponse.CatalogueBean catalogueBean = readingInfoResponse.getCatalogue();
        mReadingPicAdapter.setNewData(catalogueBean.getCatalogue_content());
        mReadingCommentAdapter.setNewData(readingInfoResponse.getComment());
        mRecommendAdapter.setNewData(readingInfoResponse.getCommend());
        bannerList = readingInfoResponse.getBanner();
        initBanner();
    }

    private void initBanner() {
        // 设置数据
        mBanner.setDelayedTime(4000);//切换时间
        mBanner.setPages(bannerList, (MZHolderCreator<BannerReadingViewHolder>) () -> new BannerReadingViewHolder(mImageLoaderHelper));
    }
}
