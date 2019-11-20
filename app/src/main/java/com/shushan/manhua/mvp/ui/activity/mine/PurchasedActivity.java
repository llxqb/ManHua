package com.shushan.manhua.mvp.ui.activity.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerPurchasedComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.PurchasedModule;
import com.shushan.manhua.entity.response.PurchasedResponse;
import com.shushan.manhua.mvp.ui.adapter.PurchasedAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 已购漫画
 */
public class PurchasedActivity extends BaseActivity implements PurchasedControl.PurchasedView {

    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.purchased_recycler_view)
    RecyclerView mPurchasedRecyclerView;
    private PurchasedAdapter mPurchasedAdapter;
    private List<PurchasedResponse> purchasedResponseList = new ArrayList<>();

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_purchased);
        setStatusBar();
        initInjectData();
    }

    @Override
    public void initView() {
        mCommonTitleTv.setText(getResources().getString(R.string.PurchasedActivity_title));
        mPurchasedAdapter = new PurchasedAdapter(purchasedResponseList);
        mPurchasedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPurchasedRecyclerView.setAdapter(mPurchasedAdapter);
    }

    @Override
    public void initData() {
        for (int i = 0; i < 10; i++) {
            PurchasedResponse purchasedResponse = new PurchasedResponse();
            purchasedResponseList.add(purchasedResponse);
        }
    }


    @OnClick(R.id.common_left_iv)
    public void onViewClicked() {
        finish();
    }

    private void initInjectData() {
        DaggerPurchasedComponent.builder().appComponent(getAppComponent())
                .purchasedModule(new PurchasedModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
