package com.shushan.manhua.mvp.ui.fragment.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerMineFragmentComponent;
import com.shushan.manhua.di.modules.MainModule;
import com.shushan.manhua.di.modules.MineFragmentModule;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.MineRequest;
import com.shushan.manhua.entity.response.MineInfoResponse;
import com.shushan.manhua.entity.response.MineReadingResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.mvp.ui.activity.book.ReadingHistoryActivity;
import com.shushan.manhua.mvp.ui.activity.mine.BuyActivity;
import com.shushan.manhua.mvp.ui.activity.mine.CheckInActivity;
import com.shushan.manhua.mvp.ui.activity.mine.FeedbackActivity;
import com.shushan.manhua.mvp.ui.activity.mine.MemberCenterActivity;
import com.shushan.manhua.mvp.ui.activity.mine.PurchasedActivity;
import com.shushan.manhua.mvp.ui.activity.mine.ReadingSettingActivity;
import com.shushan.manhua.mvp.ui.activity.setting.SettingActivity;
import com.shushan.manhua.mvp.ui.activity.user.MessageActivity;
import com.shushan.manhua.mvp.ui.activity.user.PersonalInfoActivity;
import com.shushan.manhua.mvp.ui.adapter.MineReadingAdapter;
import com.shushan.manhua.mvp.ui.base.BaseFragment;
import com.shushan.manhua.mvp.utils.LogUtils;
import com.shushan.manhua.mvp.utils.StatusBarUtil;
import com.shushan.manhua.mvp.utils.UserUtil;
import com.shushan.manhua.mvp.views.CircleImageView;
import com.shushan.manhua.mvp.views.ResizableImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 我的
 */

public class MineFragment extends BaseFragment implements MineFragmentControl.MineView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    MineFragmentControl.MineFragmentPresenter mPresenter;
    @BindView(R.id.swipe_ly)
    SwipeRefreshLayout mSwipeLy;
    @BindView(R.id.avatar_iv)
    CircleImageView mAvatarIv;
    @BindView(R.id.username_tv)
    TextView mUsernameTv;
    @BindView(R.id.vip_icon)
    ImageView mVipIcon;
    @BindView(R.id.message_read_tv)
    TextView mMessageReadTv;
    @BindView(R.id.message_ll)
    LinearLayout mMessageLl;
    @BindView(R.id.beans_num_tv)
    TextView mBeansNumTv;
    @BindView(R.id.not_vip_layout)
    ConstraintLayout mNotVipLayout;
    @BindView(R.id.top_iv)
    ResizableImageView mTopIv;
    @BindView(R.id.check_in_num_tv)
    TextView mCheckInNumTv;
    @BindView(R.id.vip_check_in_num_tv)
    TextView mVipCheckInNumTv;
    @BindView(R.id.reading_recycler_view)
    RecyclerView mReadingRecyclerView;
    Unbinder unbinder;
    private User mUser;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        StatusBarUtil.setTransparentForImageViewInFragment(getActivity(), null);
        initializeInjector();
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(ActivityConstant.UPDATE_PERSONAL_INFO)) {
                onRequestMineInfo();
            }
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.UPDATE_PERSONAL_INFO);
    }


    @Override
    public void initView() {
        mSwipeLy.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        mSwipeLy.setOnRefreshListener(this);
        initRecyclerView();
    }

    @Override
    public void initData() {
        onRequestMineInfo();
    }

    private void initRecyclerView() {
        List<MineReadingResponse> mineReadingResponseList = new ArrayList<>();
        String[] readName = {getResources().getString(R.string.MineFragment_reading_hint_title), getResources().getString(R.string.MineFragment_reading_record), getResources().getString(R.string.MineFragment_buy_book)};
        int[] readIcon = {R.mipmap.my_reading_preference, R.mipmap.my_reading_record, R.mipmap.my_purchased_comics};
        for (int i = 0; i < readName.length; i++) {
            MineReadingResponse mineReadingResponse = new MineReadingResponse();
            mineReadingResponse.cover = readIcon[i];
            mineReadingResponse.name = readName[i];
            mineReadingResponseList.add(mineReadingResponse);
        }
        MineReadingAdapter mMineReadingAdapter = new MineReadingAdapter(mineReadingResponseList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mReadingRecyclerView.setLayoutManager(linearLayoutManager);
        mReadingRecyclerView.setAdapter(mMineReadingAdapter);
        mMineReadingAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (position) {
                case 0://阅读偏好
                    startActivitys(ReadingSettingActivity.class);
                    break;
                case 1://阅读记录
                    startActivitys(ReadingHistoryActivity.class);
                    break;
                case 2://已购漫画
                    startActivitys(PurchasedActivity.class);
                    break;
            }
        });
    }

    @OnClick({R.id.avatar_iv, R.id.setting_iv, R.id.message_ll, R.id.recharge_tv, R.id.vip_icon, R.id.become_vip_tv, R.id.check_in_beans_ll, R.id.vip_check_in_beans_ll, R.id.feedback_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.avatar_iv:
                startActivitys(PersonalInfoActivity.class);
                break;
            case R.id.setting_iv:
                startActivitys(SettingActivity.class);
                break;
            case R.id.message_ll://我的消息
                startActivitys(MessageActivity.class);
                break;
            case R.id.recharge_tv://充值中心
                startActivitys(BuyActivity.class);
                break;
            case R.id.vip_icon://立即开通
            case R.id.become_vip_tv://立即开通
                startActivitys(MemberCenterActivity.class);
                break;
            case R.id.check_in_beans_ll:
                startActivitys(CheckInActivity.class);
                break;
            case R.id.vip_check_in_beans_ll:
                startActivitys(MemberCenterActivity.class);
                break;
            case R.id.feedback_tv:
                startActivitys(FeedbackActivity.class);
                break;
        }
    }

    @Override
    public void onRefresh() {
        onRequestMineInfo();
    }

    /**
     * 请求我的
     */
    private void onRequestMineInfo() {
        MineRequest mineRequest = new MineRequest();
        mineRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestMineInfo(mineRequest);
    }

    @Override
    public void getMineInfoSuccess(MineInfoResponse mineInfoResponse) {
        if (mSwipeLy.isRefreshing()) {
            mSwipeLy.setRefreshing(false);
        }
        MineInfoResponse.UserinfoBean userinfoBean = mineInfoResponse.getUserinfo();
        if (mineInfoResponse.getUnread_message() == 0) {  //未读消息
            mMessageReadTv.setVisibility(View.GONE);
        } else {
            mMessageReadTv.setVisibility(View.VISIBLE);
        }
        setData(userinfoBean);
    }

    private void setData(MineInfoResponse.UserinfoBean userinfoBean) {
        if (userinfoBean != null) {
            User user = UserUtil.tranLoginUser(userinfoBean);
            mBuProcessor.setLoginUser(user);
            mUser = mBuProcessor.getUser();
            LogUtils.e("Mine: mUser:" + new Gson().toJson(mUser));
            mImageLoaderHelper.displayImage(getActivity(), userinfoBean.getHead_portrait(), mAvatarIv, Constant.LOADING_AVATOR);
            mUsernameTv.setText(userinfoBean.getName());
            if (userinfoBean.getVip() == 0) {//是否是VIP
                mVipIcon.setImageResource(R.mipmap.vip_gray);
                mNotVipLayout.setVisibility(View.VISIBLE);
                mTopIv.setImageResource(R.mipmap.my_background);
            } else {
                mVipIcon.setImageResource(R.mipmap.recharge_vip);
                mNotVipLayout.setVisibility(View.GONE);
                mTopIv.setImageResource(R.mipmap.my_background2);
            }
            mBeansNumTv.setText(String.valueOf(userinfoBean.getBean()));
        }
    }

    private void initializeInjector() {
        DaggerMineFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .mainModule(new MainModule((AppCompatActivity) getActivity()))
                .mineFragmentModule(new MineFragmentModule(this))
                .build().inject(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
