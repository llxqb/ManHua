package com.shushan.manhua.mvp.ui.fragment.message;

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
import android.widget.TextView;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerNoticeMessageFragmentComponent;
import com.shushan.manhua.di.modules.MessageModule;
import com.shushan.manhua.di.modules.NoticeMessageFragmentModule;
import com.shushan.manhua.entity.request.MessageRequest;
import com.shushan.manhua.entity.response.MessageResponse;
import com.shushan.manhua.mvp.ui.adapter.NoticeMessageAdapter;
import com.shushan.manhua.mvp.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 通知
 */

public class NoticeMessageFragment extends BaseFragment implements NoticeMessageFragmentControl.NoticeMessageView {

    @Inject
    NoticeMessageFragmentControl.NoticeMessageFragmentPresenter mPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private NoticeMessageAdapter mNoticeMessageAdapter;
    private List<MessageResponse.DataBean> noticeMessageResponseList = new ArrayList<>();
    private View mEmptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice_message, container, false);
        initializeInjector();
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        initEmptyView();
        mNoticeMessageAdapter = new NoticeMessageAdapter(noticeMessageResponseList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mNoticeMessageAdapter);
    }

    @Override
    public void initData() {
//        onRequestMessageInfo();
    }


    private void onRequestMessageInfo() {
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.token = mBuProcessor.getToken();
        messageRequest.type = "3";
        mPresenter.onRequestMessageInfo(messageRequest);
    }


    @Override
    public void getMessageInfoSuccess(MessageResponse messageResponse) {
        if (messageResponse.getData().isEmpty()) {
            mNoticeMessageAdapter.setEmptyView(mEmptyView);
        } else {
            mNoticeMessageAdapter.setNewData(messageResponse.getData());
        }
    }


    private void initEmptyView() {
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, (ViewGroup) mRecyclerView.getParent(), false);
        ImageView emptyIv = mEmptyView.findViewById(R.id.empty_iv);
        TextView emptyTv = mEmptyView.findViewById(R.id.empty_tv);
        emptyIv.setImageResource(R.mipmap.default_page_information);
        emptyTv.setText(getResources().getString(R.string.MessageActivity_empty_tv));
    }


    private void initializeInjector() {
        DaggerNoticeMessageFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .messageModule(new MessageModule((AppCompatActivity) getActivity()))
                .noticeMessageFragmentModule(new NoticeMessageFragmentModule(this))
                .build().inject(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
