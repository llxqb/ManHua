package com.shushan.manhua.mvp.ui.activity.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerPurchasedComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.PurchasedModule;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.PurchasedBookRequest;
import com.shushan.manhua.entity.response.PurchasedResponse;
import com.shushan.manhua.mvp.ui.activity.book.BookDetailActivity;
import com.shushan.manhua.mvp.ui.adapter.PurchasedAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 已购漫画
 */
public class PurchasedActivity extends BaseActivity implements PurchasedControl.PurchasedView, BaseQuickAdapter.RequestLoadMoreListener {

    @Inject
    PurchasedControl.PresenterPurchased mPresenter;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.purchased_recycler_view)
    RecyclerView mPurchasedRecyclerView;
    private PurchasedAdapter mPurchasedAdapter;
    private List<PurchasedResponse.DataBean> purchasedResponseList = new ArrayList<>();
    private View mEmptyView;
    private int page = 1;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_purchased);
        setStatusBar();
        initInjectData();
    }

    @Override
    public void initView() {
        initEmptyView();
        mCommonTitleTv.setText(getResources().getString(R.string.PurchasedActivity_title));
        mPurchasedAdapter = new PurchasedAdapter(purchasedResponseList, mImageLoaderHelper);
        mPurchasedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPurchasedRecyclerView.setAdapter(mPurchasedAdapter);
        mPurchasedAdapter.setOnLoadMoreListener(this, mPurchasedRecyclerView);
        mPurchasedAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PurchasedResponse.DataBean dataBean = (PurchasedResponse.DataBean) adapter.getItem(position);
            BookDetailActivity.start(PurchasedActivity.this, String.valueOf(dataBean.getBook_id()));
        });
    }

    @Override
    public void initData() {
        onRequestPurchasedBook();
    }


    @OnClick(R.id.common_left_iv)
    public void onViewClicked() {
        finish();
    }

    /**
     * 已购漫画
     */
    private void onRequestPurchasedBook() {
        PurchasedBookRequest purchasedBookRequest = new PurchasedBookRequest();
        purchasedBookRequest.token = mBuProcessor.getToken();
        purchasedBookRequest.page = String.valueOf(page);
        purchasedBookRequest.pagesize = String.valueOf(Constant.PAGESIZE);
        mPresenter.onRequestPurchasedBook(purchasedBookRequest);
    }


    @Override
    public void onLoadMoreRequested() {
        page++;
        onRequestPurchasedBook();
    }


    @Override
    public void getPurchasedBookSuccess(PurchasedResponse purchasedResponse) {
        purchasedResponseList = purchasedResponse.getData();
        if (!purchasedResponseList.isEmpty()) {
            if (page == 1) {
                mPurchasedAdapter.setNewData(purchasedResponseList);
                if (purchasedResponseList.size() == Constant.PAGESIZE) {
                    mPurchasedAdapter.loadMoreComplete();
                } else {
                    mPurchasedAdapter.loadMoreEnd(true);
                }
            } else {
                mPurchasedAdapter.addData(purchasedResponseList);
                if (purchasedResponseList.size() == Constant.PAGESIZE) {
                    mPurchasedAdapter.loadMoreComplete();
                } else {
                    mPurchasedAdapter.loadMoreEnd();
                }
            }
        } else {
            if (page == 1) {
                mPurchasedAdapter.setNewData(null);
                mPurchasedAdapter.setEmptyView(mEmptyView);
            } else {
                mPurchasedAdapter.loadMoreEnd();
            }
        }
    }

    private void initEmptyView() {
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, (ViewGroup) mPurchasedRecyclerView.getParent(), false);
        ImageView emptyIv = mEmptyView.findViewById(R.id.empty_iv);
        TextView emptyTv = mEmptyView.findViewById(R.id.empty_tv);
        emptyIv.setImageResource(R.mipmap.default_page_cartoon);
        emptyTv.setText(getResources().getString(R.string.PurchasedActivity_empty_tv));
    }

    private void initInjectData() {
        DaggerPurchasedComponent.builder().appComponent(getAppComponent())
                .purchasedModule(new PurchasedModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
