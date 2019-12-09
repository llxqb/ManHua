package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.androidkun.xtablayout.XTabLayout;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerMoreCommentComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.MoreCommentModule;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.fragment.moreComment.HotCommentFragment;
import com.shushan.manhua.mvp.ui.fragment.moreComment.LatestCommentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 书籍评论
 * 查看更多评论
 */
public class MoreCommentActivity extends BaseActivity implements MoreCommentControl.MoreCommentView {

    @BindView(R.id.xTabLayout)
    XTabLayout mXTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    String[] titles;
    String mBookId;

    public static void start(Context context, String bookId) {//漫画id
        Intent intent = new Intent(context, MoreCommentActivity.class);
        intent.putExtra("bookId", bookId);
        context.startActivity(intent);
    }


    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_more_comment);
        setStatusBar();
        initInjectData();
    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            mBookId = getIntent().getStringExtra("bookId");
            titles = new String[]{getString(R.string.MoreCommentActivity_latest_tv), getResources().getString(R.string.MoreCommentActivity_hot_tv)};
            mViewPager.setOffscreenPageLimit(2);
            mViewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
            mXTabLayout.setupWithViewPager(mViewPager);
        }
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.common_back_iv, R.id.comment_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_back_iv:
                finish();
                break;
            case R.id.comment_iv://发表评论

                break;
        }
    }


    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<Fragment>();

        MyPageAdapter(FragmentManager fm) {
            super(fm);
            Bundle bundle = new Bundle();
            bundle.putString("bookId", mBookId);
            LatestCommentFragment latestCommentFragment = new LatestCommentFragment();
            HotCommentFragment hotCommentFragment = new HotCommentFragment();
            latestCommentFragment.setArguments(bundle);
            hotCommentFragment.setArguments(bundle);
            fragments.add(latestCommentFragment);
            fragments.add(hotCommentFragment);
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
        DaggerMoreCommentComponent.builder().appComponent(getAppComponent())
                .moreCommentModule(new MoreCommentModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
