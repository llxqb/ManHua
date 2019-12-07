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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerSelectionFragmentComponent;
import com.shushan.manhua.di.modules.BookDetailModule;
import com.shushan.manhua.di.modules.SelectionFragmentModule;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.SelectionRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.response.SelectionResponse;
import com.shushan.manhua.mvp.ui.activity.book.ReadBaseActivity;
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

public class SelectionDetailFragment extends BaseFragment implements SelectionFragmentControl.SelectionView, BaseQuickAdapter.RequestLoadMoreListener {

    @Inject
    SelectionFragmentControl.SelectionFragmentPresenter mPresenter;
    @BindView(R.id.sort_tv)
    TextView mSortTv;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Unbinder unbinder;
    private SelectionAdapter mSelectionAdapter;
    private List<SelectionResponse.AnthologyBean> selectionResponseList = new ArrayList<>();
    private String mBookId;
    private int page = 1;
    private int sort = 0;//sort 0: 正序  1 ：逆序
    private SelectionResponse mSelectionResponse;
    private SelectionResponse.AnthologyBean dataBean;
    private int clickPos;
//    private int mLoginModel;//1 是游客模式 2 是登录模式


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
            onRequestSelectionInfo();
        }
        mSelectionAdapter = new SelectionAdapter(selectionResponseList, mImageLoaderHelper);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mSelectionAdapter);
        mSelectionAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mSelectionAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            dataBean = (SelectionResponse.AnthologyBean) adapter.getItem(position);
            clickPos = position;
            if (view.getId() == R.id.support_tv) {
                onCommentSuggestRequest();
            } else if (view.getId() == R.id.item_selection_layout) {
                ReadBaseActivity.start(getActivity(), mBookId, dataBean.getCatalogue_id());
            }
        });
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.sort_tv})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.sort_tv) {
            if (sort == 1) {//进行正序排序
                sort = 0;
                Drawable drawable = getResources().getDrawable(R.mipmap.list_positive_sequence);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mSortTv.setCompoundDrawables(null, null, drawable, null);
                mSortTv.setText(getString(R.string.SelectionDetailFragment_sort_positive));
            } else {//进行逆序排序
                sort = 1;
                Drawable drawable = getResources().getDrawable(R.mipmap.list_inverted_order);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mSortTv.setCompoundDrawables(null, null, drawable, null);
                mSortTv.setText(getString(R.string.SelectionDetailFragment_sort_negative));
            }
            initSortList(mSelectionAdapter.getData(), 1);
        }
    }

    /**
     * 对列表正序 逆序排序
     * isAllSort : 0 是 对一页排序  1 是对所有数据排序
     */
    private void initSortList(List<SelectionResponse.AnthologyBean> selectionResponseList, int isAllSort) {
        if (selectionResponseList != null) {
            Comparator<SelectionResponse.AnthologyBean> comparator = (dataBean1, dataBean2) -> {
                // 按getRecommend从大到小排序
                if (sort == 1) {
                    return dataBean2.getCatalogue_id() - dataBean1.getCatalogue_id();
                } else {
                    return dataBean1.getCatalogue_id() - dataBean2.getCatalogue_id();
                }
            };
            //这里就会自动根据规则进行排序
            Collections.sort(selectionResponseList, comparator);
            //加载更多这样设置
            if (page == 1 || isAllSort == 1) {
                mSelectionAdapter.setNewData(selectionResponseList);
            } else {
                mSelectionAdapter.addData(selectionResponseList);
                mSelectionAdapter.loadMoreComplete();
            }
        }
    }

    /**
     * 请求漫画选集信息
     */
    private void onRequestSelectionInfo() {
        SelectionRequest selectionRequest = new SelectionRequest();
        selectionRequest.token = mBuProcessor.getToken();
        selectionRequest.book_id = mBookId;
        if (sort == 0) {
            selectionRequest.orderby = "asc";
        } else {
            selectionRequest.orderby = "desc";
        }
        selectionRequest.page = String.valueOf(page);
        selectionRequest.pagesize = String.valueOf(Constant.PAGESIZE);
        mPresenter.onRequestSelectionInfo(selectionRequest);
    }

    boolean isReqState = false;//加载更多 正在请求状态

    @Override
    public void onLoadMoreRequested() {
        if (!isReqState) {
            if (!selectionResponseList.isEmpty()) {
                if (page == 1 && selectionResponseList.size() < Constant.PAGESIZE) {
                    mSelectionAdapter.loadMoreEnd(true);
                } else {
                    if (selectionResponseList.size() < Constant.PAGESIZE) {
                        mSelectionAdapter.loadMoreEnd();
                    } else {
                        //等于10条
                        page++;
                        onRequestSelectionInfo();
                        isReqState = true;
                        mSelectionAdapter.loadMoreComplete();
                    }
                }
            } else {
                mSelectionAdapter.loadMoreEnd();
            }
        }
    }

    @Override
    public void getSelectionInfoSuccess(SelectionResponse selectionResponse) {
        isReqState = false;
        selectionResponseList = selectionResponse.getAnthology();
        mSelectionResponse = selectionResponse;
        initSortList(selectionResponseList, 0);
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
