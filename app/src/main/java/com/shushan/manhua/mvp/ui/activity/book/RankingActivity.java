package com.shushan.manhua.mvp.ui.activity.book;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerRankingComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.RankingModule;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.RankingRequest;
import com.shushan.manhua.entity.response.RankingResponse;
import com.shushan.manhua.mvp.ui.adapter.RankingAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 排行榜
 */
public class RankingActivity extends BaseActivity implements RankingControl.RankingView, BaseQuickAdapter.RequestLoadMoreListener {

    @Inject
    RankingControl.PresenterRanking mPresenter;
    @BindView(R.id.ranking_recycler_view)
    RecyclerView rankingRecyclerView;
    private RankingAdapter mRankingAdapter;
    private List<RankingResponse.ListBean> mRankingList = new ArrayList();
    private RankingResponse mRankingResponse;
    private int page = 1;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_ranking);
        StatusBarUtil.setTransparentForImageView(this, null);
        initInjectData();
    }

    @Override
    public void initView() {
        mRankingAdapter = new RankingAdapter(mRankingList, mImageLoaderHelper);
        rankingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rankingRecyclerView.setAdapter(mRankingAdapter);
        mRankingAdapter.setOnLoadMoreListener(this, rankingRecyclerView);
        mRankingAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            RankingResponse.ListBean listBean = (RankingResponse.ListBean) adapter.getItem(position);
            BookDetailActivity.start(RankingActivity.this, String.valueOf(listBean.getBook_id()));
        });
    }

    @Override
    public void initData() {
        onRequestRanking();
    }

    private void onRequestRanking() {
        RankingRequest rankingRequest = new RankingRequest();
        rankingRequest.token = mBuProcessor.getToken();
//        rankingRequest.channel = //1男频2女频
        rankingRequest.page = String.valueOf(page);
        rankingRequest.pagesize = String.valueOf(Constant.PAGESIZE);
        mPresenter.onRequestRanking(rankingRequest);
    }

    boolean isReqState = false;//加载更多 正在请求状态

    @Override
    public void onLoadMoreRequested() {
        if (!isReqState) {
            if (!mRankingList.isEmpty()) {
                if (page == 1 && mRankingList.size() < Constant.PAGESIZE) {
                    mRankingAdapter.loadMoreEnd(true);
                } else {
                    if (mRankingList.size() < Constant.PAGESIZE) {
                        mRankingAdapter.loadMoreEnd();
                    } else {
                        //等于10条
                        page++;
                        onRequestRanking();
                        isReqState = true;
                        mRankingAdapter.loadMoreComplete();
                    }
                }
            } else {
                mRankingAdapter.loadMoreEnd();
            }
        }
    }

    List<RankingResponse.ListBean> tempListBean = new ArrayList<>();

    @Override
    public void getRankingSuccess(RankingResponse rankingResponse) {
        isReqState = false;
        mRankingList = rankingResponse.getList();
        mRankingResponse = rankingResponse;
        for (int i = 0; i < mRankingList.size(); i++) {
            RankingResponse.ListBean listBean = mRankingList.get(i);
            if (i >= 3) {
                tempListBean.add(listBean);
            }
        }
        mRankingAdapter.setNewData(tempListBean);//去掉1、2、3
        initRecyclerViewHeader();
    }


    private void initRecyclerViewHeader() {
        //添加头布局
        View headerView = LayoutInflater.from(this).inflate(R.layout.ranking_top_layout, null);
        ImageView mBackIv = headerView.findViewById(R.id.back_iv);
        //第二名
        RelativeLayout ranking2Rl = headerView.findViewById(R.id.ranking_2_rl);
        ImageView ranking2Iv = headerView.findViewById(R.id.ranking_2_iv);
        TextView updateToChapter2Tv = headerView.findViewById(R.id.update_to_chapter_2_tv);
        TextView bookName2Tv = headerView.findViewById(R.id.book_name_2_tv);
        TextView author2Tv = headerView.findViewById(R.id.author_2_tv);
        TextView ranking2Label1Tv = headerView.findViewById(R.id.ranking2_label_1_tv);
        TextView ranking2Label2Tv = headerView.findViewById(R.id.ranking2_label_2_tv);
        //第一名
        RelativeLayout ranking1Rl = headerView.findViewById(R.id.ranking_1_rl);
        ImageView ranking1Iv = headerView.findViewById(R.id.ranking_1_iv);
        TextView updateToChapter1Tv = headerView.findViewById(R.id.update_to_chapter_1_tv);
        TextView bookName1Tv = headerView.findViewById(R.id.book_name_1_tv);
        TextView author1Tv = headerView.findViewById(R.id.author_1_tv);
        TextView rankingLabel1Tv = headerView.findViewById(R.id.ranking_label_1_tv);
        TextView rankingLabel2Tv = headerView.findViewById(R.id.ranking_label_2_tv);
        //第三名
        RelativeLayout ranking3Rl = headerView.findViewById(R.id.ranking_3_rl);
        ImageView ranking3Iv = headerView.findViewById(R.id.ranking_3_iv);
        TextView updateToChapter3Tv = headerView.findViewById(R.id.update_to_chapter_3_tv);
        TextView bookName3Tv = headerView.findViewById(R.id.book_name_3_tv);
        TextView author3Tv = headerView.findViewById(R.id.author_3_tv);
        TextView ranking3Label1Tv = headerView.findViewById(R.id.ranking3_label_1_tv);
        TextView ranking3Label2Tv = headerView.findViewById(R.id.ranking3_label_2_tv);
        mRankingAdapter.addHeaderView(headerView);

        //排行榜第一名
        if (mRankingList.size() > 0) {
            RankingResponse.ListBean listBean1 = mRankingList.get(0);
            bookName1Tv.setText(listBean1.getBook_name());//书名
            mImageLoaderHelper.displayRoundedCornerImage(RankingActivity.this, listBean1.getOblong_cover(), ranking1Iv, 5, Constant.LOADING_DEFAULT_4);
            String latestChapterValue = "Perbarui ke " + listBean1.getLastBookCatalogue();
            String authorValue = "Penulis：" + listBean1.getAuthor();
            updateToChapter1Tv.setText(latestChapterValue);
            author1Tv.setText(authorValue);
            if (listBean1.getLabel().size() > 0) {//1个标签
                rankingLabel1Tv.setVisibility(View.VISIBLE);
                rankingLabel1Tv.setText(listBean1.getLabel().get(0));
            }
            if (listBean1.getLabel().size() > 1) {//2个标签
                rankingLabel2Tv.setVisibility(View.VISIBLE);
                rankingLabel2Tv.setText(listBean1.getLabel().get(1));
            }
        }
        //排行榜第二名
        if (mRankingList.size() > 1) {
            RankingResponse.ListBean listBean2 = mRankingList.get(1);
            bookName2Tv.setText(listBean2.getBook_name());//书名
            mImageLoaderHelper.displayRoundedCornerImage(RankingActivity.this, listBean2.getOblong_cover(), ranking2Iv, 5, Constant.LOADING_DEFAULT_4);
            String latestChapterValue = "Perbarui ke " + listBean2.getLastBookCatalogue();
            String authorValue = "Penulis：" + listBean2.getAuthor();
            updateToChapter2Tv.setText(latestChapterValue);
            author2Tv.setText(authorValue);
            if (listBean2.getLabel().size() > 0) {//1个标签
                ranking2Label1Tv.setVisibility(View.VISIBLE);
                ranking2Label1Tv.setText(listBean2.getLabel().get(0));
            }
//            if (listBean2.getLabel().size() > 1) {//2个标签
//                ranking2Label2Tv.setVisibility(View.VISIBLE);
//                ranking2Label2Tv.setText(listBean2.getLabel().get(1));
//            }
        }
        //排行榜第三名
        if (mRankingList.size() > 2) {
            RankingResponse.ListBean listBean3 = mRankingList.get(2);
            bookName3Tv.setText(listBean3.getBook_name());//书名
            mImageLoaderHelper.displayRoundedCornerImage(RankingActivity.this, listBean3.getOblong_cover(), ranking3Iv, 5, Constant.LOADING_DEFAULT_4);
            String latestChapterValue = "Perbarui ke " + listBean3.getLastBookCatalogue();
            String authorValue = "Penulis：" + listBean3.getAuthor();
            updateToChapter3Tv.setText(latestChapterValue);
            author3Tv.setText(authorValue);
            if (listBean3.getLabel().size() > 0) {//1个标签
                ranking3Label1Tv.setVisibility(View.VISIBLE);
                ranking3Label1Tv.setText(listBean3.getLabel().get(0));
            }
//            if (listBean3.getLabel().size() > 1) {//2个标签
//                ranking3Label2Tv.setVisibility(View.VISIBLE);
//                ranking3Label2Tv.setText(listBean3.getLabel().get(1));
//            }
        }

        mBackIv.setOnClickListener(v -> finish());

        ranking1Rl.setOnClickListener(v -> {
            if (mRankingList.size() > 0) {
                BookDetailActivity.start(RankingActivity.this, String.valueOf(mRankingResponse.getList().get(0).getBook_id()));
            }
        });
        ranking2Rl.setOnClickListener(v -> {
            if (mRankingList.size() > 1) {
                BookDetailActivity.start(RankingActivity.this, String.valueOf(mRankingResponse.getList().get(1).getBook_id()));
            }
        });
        ranking3Rl.setOnClickListener(v -> {
            if (mRankingList.size() > 2) {
                BookDetailActivity.start(RankingActivity.this, String.valueOf(mRankingResponse.getList().get(2).getBook_id()));
            }
        });
    }

    private void initInjectData() {
        DaggerRankingComponent.builder().appComponent(getAppComponent())
                .rankingModule(new RankingModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
