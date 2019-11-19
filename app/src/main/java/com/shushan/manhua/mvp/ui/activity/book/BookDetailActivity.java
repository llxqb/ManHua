package com.shushan.manhua.mvp.ui.activity.book;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerBookDetailComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.BookDetailModule;
import com.shushan.manhua.entity.response.LabelResponse;
import com.shushan.manhua.mvp.ui.adapter.LabelAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.fragment.bookDetail.BookDetailFragment;
import com.shushan.manhua.mvp.ui.fragment.selection.SelectionDetailFragment;
import com.shushan.manhua.mvp.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 漫画书籍详情
 */
public class BookDetailActivity extends BaseActivity implements BookDetailControl.BookDetailView {

    @BindView(R.id.cover_iv)
    ImageView mCoverIv;
    @BindView(R.id.username_tv)
    TextView mUsernameTv;
    @BindView(R.id.label_recycler_view)
    RecyclerView mLabelRecyclerView;
    @BindView(R.id.xTabLayout)
    XTabLayout mXTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private List<LabelResponse> labelResponseList = new ArrayList<>();
    String[] titles;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_book_detail);
        StatusBarUtil.setTransparentForImageView(this, null);
        initInjectData();
    }

    @Override
    public void initView() {
        titles = new String[]{getResources().getString(R.string.BookDetailActivity_detail_tv), getResources().getString(R.string.BookDetailActivity_selection_tv)};
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        mXTabLayout.setupWithViewPager(mViewPager);
        LabelAdapter mLabelAdapter = new LabelAdapter(labelResponseList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mLabelRecyclerView.setLayoutManager(linearLayoutManager);
        mLabelRecyclerView.setAdapter(mLabelAdapter);
    }

    @Override
    public void initData() {
        for (int i = 0; i < 3; i++) {
            LabelResponse labelResponse = new LabelResponse();
            labelResponseList.add(labelResponse);
        }
    }


    @OnClick({R.id.common_left_iv, R.id.add_bookshelf_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.add_bookshelf_iv:
                showToast("加入书架");
                break;
        }
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<Fragment>();

        MyPageAdapter(FragmentManager fm) {
            super(fm);
            BookDetailFragment bookDetailFragment = new BookDetailFragment();
            SelectionDetailFragment selectionDetailFragment = new SelectionDetailFragment();
            fragments.add(bookDetailFragment);
            fragments.add(selectionDetailFragment);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


    private void initInjectData() {
        DaggerBookDetailComponent.builder().appComponent(getAppComponent())
                .bookDetailModule(new BookDetailModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
