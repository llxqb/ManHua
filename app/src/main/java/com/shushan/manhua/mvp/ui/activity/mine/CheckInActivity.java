package com.shushan.manhua.mvp.ui.activity.mine;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerCheckInComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.CheckInModule;
import com.shushan.manhua.entity.RecommendBean;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.request.ReceiveTaskRequest;
import com.shushan.manhua.entity.request.RecommendRequest;
import com.shushan.manhua.entity.request.SignDataRequest;
import com.shushan.manhua.entity.request.SignRequest;
import com.shushan.manhua.entity.response.RecommendResponse;
import com.shushan.manhua.entity.response.SignDataResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.mvp.ui.adapter.RecommendAdapter;
import com.shushan.manhua.mvp.ui.adapter.SignInAdapter;
import com.shushan.manhua.mvp.ui.adapter.TaskAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 签到 领漫豆
 */
public class CheckInActivity extends BaseActivity implements CheckInControl.CheckInView {

    @Inject
    CheckInControl.PresenterCheckIn mPresenter;
    @BindView(R.id.sign_in_day_tv)
    TextView mSignInDayTv;
    @BindView(R.id.sign_in_recycler_view)
    RecyclerView mSignInRecyclerView;
    @BindView(R.id.task_recycler_view)
    RecyclerView mTaskRecyclerView;
    @BindView(R.id.recommend_recycler_view)
    RecyclerView mRecommendRecyclerView;
    private SignInAdapter mSignInAdapter;
    private List<SignDataResponse.SignBean> signBeanArrayList = new ArrayList<>();
    private TaskAdapter mTaskAdapter;
    private List<SignDataResponse.QuestBean> taskResponseList = new ArrayList<>();
    private RecommendAdapter mRecommendAdapter;
    private List<RecommendBean> recommendResponseList = new ArrayList<>();
    private SignDataResponse mSignDataResponse;
    private User mUser;
    private int taskClickPos;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_check_in);
        StatusBarUtil.setTransparentForImageView(this, null);
        initInjectData();
        mUser = mBuProcessor.getUser();
    }

    @Override
    public void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mSignInAdapter = new SignInAdapter(signBeanArrayList);
        mSignInRecyclerView.setLayoutManager(linearLayoutManager);
        mSignInRecyclerView.setAdapter(mSignInAdapter);
        //任务adapter
        mTaskAdapter = new TaskAdapter(taskResponseList, mImageLoaderHelper);
        mTaskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTaskRecyclerView.setAdapter(mTaskAdapter);
        // 推荐adapter
        mRecommendAdapter = new RecommendAdapter(recommendResponseList, mImageLoaderHelper);
        mRecommendRecyclerView.setAdapter(mRecommendAdapter);
        mRecommendRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mTaskAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SignDataResponse.QuestBean questBean = (SignDataResponse.QuestBean) adapter.getItem(position);
                taskClickPos = position;
                //1未完成 2已完成未领取 3已完成
                if (questBean.getStatus() == 2) {
                    onRequestReceiveTask(String.valueOf(questBean.getQuest_id()));
                }
            }
        });
    }

    @Override
    public void initData() {
        onRequestSignData();
        onRecommendInfo();
    }


    @OnClick({R.id.common_back_iv, R.id.rule_tv, R.id.sign_in_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back_iv:
                finish();
                break;
            case R.id.rule_tv:
                startActivitys(RuleActivity.class);
                break;
            case R.id.sign_in_iv:
                if (mSignDataResponse != null && mSignDataResponse.getUser_today_sign() == 0) {//未签到
                    onRequestSign();
                } else {
                    showToast("Sudah masuk hari ini");
                }
                break;
        }
    }

    /**
     * 请求签到数据
     */
    private void onRequestSignData() {
        SignDataRequest signDataRequest = new SignDataRequest();
        signDataRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestSignData(signDataRequest);
    }

    @Override
    public void getSignDataSuccess(SignDataResponse signDataResponse) {
        mSignDataResponse = signDataResponse;
        mSignInDayTv.setText(getString(R.string.CheckInActivity_sign_in_day) + mSignDataResponse.getContinuous_day() + "hari");
        for (int i = 0; i < signDataResponse.getContinuous_day(); i++) {
            SignDataResponse.SignBean signBean = signDataResponse.getSign().get(i);
            signBean.isSign = true;
        }
        mSignInAdapter.setNewData(signDataResponse.getSign());
        mTaskAdapter.setNewData(signDataResponse.getQuest());
        mUser.bean = signDataResponse.getBean();
        mBuProcessor.setLoginUser(mUser);
    }

    /**
     * 请求推荐数据
     */
    private void onRecommendInfo() {
        RecommendRequest recommendRequest = new RecommendRequest();
        recommendRequest.channel = mBuProcessor.getChannel();
        recommendRequest.book_type = mBuProcessor.getbookType();
        mPresenter.onRecommendInfo(recommendRequest);
    }

    @Override
    public void getRecommendInfoSuccess(RecommendResponse recommendResponse) {
        mRecommendAdapter.setNewData(recommendResponse.getData());
    }

    /**
     * 签到
     */
    private void onRequestSign() {
        SignRequest signRequest = new SignRequest();
        signRequest.token = mBuProcessor.getToken();
        int bean = mSignDataResponse.getSign().get(mSignDataResponse.getContinuous_day()).getBean();
        signRequest.bean = String.valueOf(bean);
        mPresenter.onRequestSign(signRequest);
    }

    @Override
    public void getSignSuccess() {
        mSignInAdapter.notifyItemChanged(mSignDataResponse.getContinuous_day(), "");//局部刷新
        int bean = mSignDataResponse.getSign().get(mSignDataResponse.getContinuous_day()).getBean();
        mUser.bean = mUser.bean + bean;
        mBuProcessor.setLoginUser(mUser);
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_PERSONAL_INFO));
    }

    /**
     * 任务状态为2 已完成未领取状态
     * 领取任务
     */
    private void onRequestReceiveTask(String questId) {
        ReceiveTaskRequest receiveTaskRequest = new ReceiveTaskRequest();
        receiveTaskRequest.token = mBuProcessor.getToken();
        receiveTaskRequest.quest_id = questId;
        mPresenter.onRequestReceiveTask(receiveTaskRequest);
    }

    @Override
    public void getReceiveTaskSuccess() {
//        mTaskAdapter.notifyItemChanged(taskClickPos, "");//局部刷新
        onRequestSignData();
    }


    private void initInjectData() {
        DaggerCheckInComponent.builder().appComponent(getAppComponent())
                .checkInModule(new CheckInModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
