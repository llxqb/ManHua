package com.shushan.manhua.mvp.ui.fragment.bookshelf;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shushan.manhua.ManHuaApplication;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerBookShelfFragmentComponent;
import com.shushan.manhua.di.modules.BookShelfFragmentModule;
import com.shushan.manhua.di.modules.MainModule;
import com.shushan.manhua.entity.response.BookShelfResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.mvp.ui.adapter.BookShelfAdapter;
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
 * 书架
 */

public class BookShelfFragment extends BaseFragment implements BookShelfFragmentControl.BookShelfView {

    @BindView(R.id.recent_read_book_name_tv)
    TextView mRecentReadBookNameTv;
    @BindView(R.id.recent_read_book_to_chapter_tv)
    TextView mRecentReadBookToChapterTv;
    @BindView(R.id.bookshelf_recycler_view)
    RecyclerView mBookshelfRecyclerView;
    @BindView(R.id.recommend_recycler_view)
    RecyclerView mRecommendRecyclerView;
    Unbinder unbinder;
    private BookShelfAdapter mBookShelfAdapter;
    private List<BookShelfResponse> bookShelfResponseList = new ArrayList<>();
    private User mUser;
    @Inject
    BookShelfFragmentControl.BookShelfFragmentPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        unbinder = ButterKnife.bind(this, view);
        initializeInjector();
        mUser = mBuProcessor.getUser();
        initView();
        initData();
        return view;
    }


    @Override
    public void initView() {
        mBookShelfAdapter = new BookShelfAdapter(bookShelfResponseList);
        mBookshelfRecyclerView.setAdapter(mBookShelfAdapter);
        mBookshelfRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    @Override
    public void initData() {
        for (int i = 0; i < 7; i++) {
            BookShelfResponse bookShelfResponse = new BookShelfResponse();
            bookShelfResponse.cover = R.mipmap.book_icon;
            bookShelfResponseList.add(bookShelfResponse);
        }
        BookShelfResponse bookShelfResponse = new BookShelfResponse();
        bookShelfResponse.cover = R.mipmap.bookshelf_find_more;
        bookShelfResponseList.add(bookShelfResponse);
    }

    @OnClick({R.id.search_rl, R.id.vip_center_ll, R.id.continue_read_ll, R.id.change_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_rl:
                break;
            case R.id.vip_center_ll:
                break;
            case R.id.continue_read_ll:
                break;
            case R.id.change_tv:
                break;
        }
    }

    private void initializeInjector() {
        DaggerBookShelfFragmentComponent.builder().appComponent(((ManHuaApplication) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent())
                .mainModule(new MainModule((AppCompatActivity) getActivity()))
                .bookShelfFragmentModule(new BookShelfFragmentModule(this))
                .build().inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
