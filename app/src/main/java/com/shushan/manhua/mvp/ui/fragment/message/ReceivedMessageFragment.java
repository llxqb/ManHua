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

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerReceivedMessageFragmentComponent;
import com.shushan.manhua.di.modules.MessageModule;
import com.shushan.manhua.di.modules.ReceivedMessageFragmentModule;
import com.shushan.manhua.entity.response.ReceivedMessageResponse;
import com.shushan.manhua.mvp.ui.adapter.ReceivedMessageAdapter;
import com.shushan.manhua.mvp.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 回复我的评论
 */

public class ReceivedMessageFragment extends BaseFragment implements ReceivedMessageFragmentControl.ReceivedMessageView {

    @Inject
    ReceivedMessageFragmentControl.ReceivedMessageFragmentPresenter mPresenter;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private ReceivedMessageAdapter mReceivedMessageAdapter;
    private List<ReceivedMessageResponse> receivedMessageResponseList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_received_message, container, false);
        initializeInjector();
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        mReceivedMessageAdapter = new ReceivedMessageAdapter(receivedMessageResponseList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mReceivedMessageAdapter);
    }

    @Override
    public void initData() {
        for (int i = 0; i < 5; i++) {
            ReceivedMessageResponse receivedMessageResponse = new ReceivedMessageResponse();
            receivedMessageResponseList.add(receivedMessageResponse);
        }
    }


    private void initializeInjector() {
        DaggerReceivedMessageFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .messageModule(new MessageModule((AppCompatActivity) getActivity()))
                .receivedMessageFragmentModule(new ReceivedMessageFragmentModule(this))
                .build().inject(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
