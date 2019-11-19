package com.shushan.manhua.mvp.ui.fragment.mine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerMineFragmentComponent;
import com.shushan.manhua.di.modules.MainModule;
import com.shushan.manhua.di.modules.MineFragmentModule;
import com.shushan.manhua.entity.response.MineReadingResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.mvp.ui.activity.mine.MemberCenterActivity;
import com.shushan.manhua.mvp.ui.adapter.MineReadingAdapter;
import com.shushan.manhua.mvp.ui.base.BaseFragment;
import com.shushan.manhua.mvp.utils.StatusBarUtil;
import com.shushan.manhua.mvp.views.CircleImageView;

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

public class MineFragment extends BaseFragment implements MineFragmentControl.MineView {

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
    @BindView(R.id.check_in_num_tv)
    TextView mCheckInNumTv;
    @BindView(R.id.vip_check_in_num_tv)
    TextView mVipCheckInNumTv;
    @BindView(R.id.reading_recycler_view)
    RecyclerView mReadingRecyclerView;
    Unbinder unbinder;
    private User mUser;


    @Inject
    MineFragmentControl.MineFragmentPresenter mPresenter;

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
    public void initView() {
        mUser = mBuProcessor.getUser();
    }

    @Override
    public void initData() {
        initRecycler();
    }

    private void initRecycler() {
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
            switch (position){
                case 0://阅读偏好
                    showToast("阅读偏好");
                    break;
                case 1://阅读记录
                    showToast("阅读记录");
                    break;
                case 2://已购漫画
                    showToast("已购漫画");
                    break;
            }
        });
    }

    @OnClick({R.id.avatar_iv, R.id.setting_iv, R.id.message_ll, R.id.recharge_tv, R.id.become_vip_tv, R.id.check_in_beans_ll, R.id.vip_check_in_beans_ll, R.id.feedback_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.avatar_iv:
                startActivitys(MemberCenterActivity.class);
                break;
            case R.id.setting_iv:
                break;
            case R.id.message_ll:
                break;
            case R.id.recharge_tv:
                break;
            case R.id.become_vip_tv:
                break;
            case R.id.check_in_beans_ll:
                break;
            case R.id.vip_check_in_beans_ll:
                break;
            case R.id.feedback_tv:
                break;
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
