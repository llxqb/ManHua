package com.shushan.manhua.mvp.ui.fragment.selection;

import android.annotation.SuppressLint;
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
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.SelectionRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.response.SelectionResponse;
import com.shushan.manhua.mvp.ui.activity.book.ReadActivity;
import com.shushan.manhua.mvp.ui.adapter.SelectionAdapter;
import com.shushan.manhua.mvp.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    @SuppressLint("StaticFieldLeak")
    static SelectionDetailFragment mSelectionDetailFragment;
    @BindView(R.id.sort_tv)
    TextView mSortTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private SelectionAdapter mSelectionAdapter;
    private List<SelectionResponse.AnthologyBean> selectionResponseList = new ArrayList<>();
    private String mBookId;
    private String mBookCover;
    private int page = 1;
    private int sort = 0;//sort 0: 正序  1 ：逆序
    private SelectionResponse mSelectionResponse;
    private SelectionResponse.AnthologyBean dataBean;
    private int clickPos;


    public static SelectionDetailFragment getInstance(String bookId, String bookCover) {
        if (mSelectionDetailFragment == null) {
            mSelectionDetailFragment = new SelectionDetailFragment();
        }
        Bundle bd = new Bundle();
        bd.putString("bookId", bookId);
        bd.putString("bookCover", bookCover);
        mSelectionDetailFragment.setArguments(bd);
        return mSelectionDetailFragment;
    }


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
        if (getArguments() != null) {
            mBookId = getArguments().getString("bookId");
            mBookCover = getArguments().getString("bookCover");
            onRequestSelectionInfo();
        }
        mSelectionAdapter = new SelectionAdapter(selectionResponseList, mImageLoaderHelper);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mSelectionAdapter);
        mSelectionAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            dataBean = (SelectionResponse.AnthologyBean) adapter.getItem(position);
            clickPos = position;
            if (view.getId() == R.id.support_tv) {
                onCommentSuggestRequest();
            } else if (view.getId() == R.id.item_selection_layout) {
                ReadActivity.start(getActivity(), mBookId, dataBean.getCatalogue_id(), mBookCover);//阅读页面
            }
        });
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.sort_tv, R.id.start_reading_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sort_tv:
                if (sort == 1) {//进行正序排序
                    sort = 0;
                    Drawable drawable = getResources().getDrawable(R.mipmap.list_inverted_order);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mSortTv.setCompoundDrawables(null, null, drawable, null);
                } else {//进行逆序排序
                    sort = 1;
                    Drawable drawable = getResources().getDrawable(R.mipmap.list_positive_sequence);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mSortTv.setCompoundDrawables(null, null, drawable, null);
                }
                initSortList();
                break;
            case R.id.start_reading_tv:
                ReadActivity.start(getActivity(), mBookId, 1, mBookCover);//阅读页面 章节默认第一章节？
                break;
        }
    }

    /**
     * 对列表正序 逆序排序
     */
    private void initSortList() {
        if (mSelectionResponse != null) {
            Comparator<SelectionResponse.AnthologyBean> comparator = (dataBean1, dataBean2) -> {
                // 按getRecommend从大到小排序
                if (sort == 1) {
                    return dataBean2.getCatalogue_id() - dataBean1.getCatalogue_id();
                } else {
                    return dataBean1.getCatalogue_id() - dataBean2.getCatalogue_id();
                }
            };
            //这里就会自动根据规则进行排序
            Collections.sort(mSelectionResponse.getAnthology(), comparator);
            mSelectionAdapter.setNewData(mSelectionResponse.getAnthology());
        }
    }

    /**
     * 请求漫画选集信息
     */
    private void onRequestSelectionInfo() {
        SelectionRequest selectionRequest = new SelectionRequest();
        selectionRequest.token = mBuProcessor.getToken();
        selectionRequest.book_id = mBookId;
        selectionRequest.orderby = "asc";
        selectionRequest.page = String.valueOf(page);
        selectionRequest.pagesize = String.valueOf(Constant.PAGESIZE);
        mPresenter.onRequestSelectionInfo(selectionRequest);
    }

    @Override
    public void getSelectionInfoSuccess(SelectionResponse selectionResponse) {
        mSelectionResponse = selectionResponse;
        initSortList();
    }

    /**
     * 评论点赞
     */
    private void onCommentSuggestRequest() {
        SupportRequest commentSuggestRequest = new SupportRequest();
        commentSuggestRequest.token = mBuProcessor.getToken();
        commentSuggestRequest.type = "2";
        commentSuggestRequest.relation_id = String.valueOf(dataBean.getCatalogue_id());
        mPresenter.onCommentSuggestRequest(commentSuggestRequest);
    }

    @Override
    public void getSuggestSuccess() {
        mSelectionAdapter.notifyItemChanged(clickPos, dataBean.getLike());//局部刷新
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
