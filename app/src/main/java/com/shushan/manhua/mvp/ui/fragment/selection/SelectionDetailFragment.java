package com.shushan.manhua.mvp.ui.fragment.selection;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerSelectionFragmentComponent;
import com.shushan.manhua.di.modules.BookDetailModule;
import com.shushan.manhua.di.modules.SelectionFragmentModule;
import com.shushan.manhua.entity.response.SelectionResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.mvp.ui.adapter.SelectionAdapter;
import com.shushan.manhua.mvp.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 漫画选集
 */

public class SelectionDetailFragment extends BaseFragment implements SelectionFragmentControl.SelectionView {

    @Inject
    SelectionFragmentControl.SelectionFragmentPresenter mPresenter;
    @BindView(R.id.sort_tv)
    TextView mSortTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private User mUser;
    private SelectionAdapter mSelectionAdapter;
    private List<SelectionResponse> selectionResponseList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_selection, container, false);
        initializeInjector();
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        mUser = mBuProcessor.getUser();
        mSelectionAdapter = new SelectionAdapter(selectionResponseList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mSelectionAdapter);

    }

    @Override
    public void initData() {
        for (int i = 0; i < 10; i++) {
            SelectionResponse selectionResponse = new SelectionResponse();
            selectionResponseList.add(selectionResponse);
        }
    }

    @OnClick({R.id.sort_tv, R.id.start_reading_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sort_tv:
                //排序
                Drawable drawable = getResources().getDrawable(R.mipmap.list_inverted_order);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mSortTv.setCompoundDrawables(null, null, drawable, null);
                break;
            case R.id.start_reading_tv:
                //开始阅读
                break;
        }
    }


    private void initializeInjector() {
        DaggerSelectionFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .bookDetailModule(new BookDetailModule((AppCompatActivity) getActivity()))
                .selectionFragmentModule(new SelectionFragmentModule(this))
                .build().inject(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
