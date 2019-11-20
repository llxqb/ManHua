package com.shushan.manhua.mvp.ui.activity.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerCheckInComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.CheckInModule;
import com.shushan.manhua.entity.response.SignInResponse;
import com.shushan.manhua.mvp.ui.adapter.SignInAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 签到 领漫豆
 */
public class CheckInActivity extends BaseActivity implements CheckInControl.CheckInView {

    @BindView(R.id.sign_in_day_tv)
    TextView mSignInDayTv;
    @BindView(R.id.sign_in_recycler_view)
    RecyclerView mSignInRecyclerView;
    @BindView(R.id.task_recycler_view)
    RecyclerView mTaskRecyclerView;
    @BindView(R.id.recommend_recycler_view)
    RecyclerView mRecommendRecyclerView;
    private SignInAdapter mSignInAdapter;
    private List<SignInResponse> labelResponseList = new ArrayList<>();

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_check_in);
        StatusBarUtil.setTransparentForImageView(this, null);
        initInjectData();
    }

    @Override
    public void initView() {
        mSignInAdapter = new SignInAdapter(labelResponseList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mSignInRecyclerView.setLayoutManager(linearLayoutManager);
        mSignInRecyclerView.setAdapter(mSignInAdapter);
    }

    @Override
    public void initData() {
        for (int i = 0; i < 7; i++) {
            SignInResponse signInResponse = new SignInResponse();
            signInResponse.beansNum = "100";
            signInResponse.isSign = i < 4;
            labelResponseList.add(signInResponse);
        }
    }


    @OnClick({R.id.common_back_iv, R.id.rule_tv, R.id.sign_in_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back_iv:
                finish();
                break;
            case R.id.rule_tv:
                break;
            case R.id.sign_in_iv:
                break;
        }
    }


    private void initInjectData() {
        DaggerCheckInComponent.builder().appComponent(getAppComponent())
                .checkInModule(new CheckInModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

}
