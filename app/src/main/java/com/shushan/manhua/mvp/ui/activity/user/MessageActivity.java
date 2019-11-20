package com.shushan.manhua.mvp.ui.activity.user;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerMessageComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.MessageModule;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.fragment.message.NoticeMessageFragment;
import com.shushan.manhua.mvp.ui.fragment.message.ReceivedMessageFragment;
import com.shushan.manhua.mvp.ui.fragment.message.SentMessageFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的消息
 */
public class MessageActivity extends BaseActivity implements MessageControl.MessageView {
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.xTabLayout)
    XTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    String[] titles;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_message);
        setStatusBar();
        initInjectData();
    }

    @Override
    public void initView() {
        titles = new String[]{getResources().getString(R.string.MessageActivity_SentMessage), getResources().getString(R.string.MessageActivity_ReceivedMessage), getResources().getString(R.string.MessageActivity_Notice)};
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initData() {
        mCommonTitleTv.setText(getResources().getString(R.string.MessageActivity_title));
    }

    @OnClick(R.id.common_left_iv)
    public void onViewClicked() {
        finish();
    }


    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<Fragment>();

        MyPageAdapter(FragmentManager fm) {
            super(fm);
            SentMessageFragment sentMessageFragment = new SentMessageFragment();
            ReceivedMessageFragment receivedMessageFragment = new ReceivedMessageFragment();
            NoticeMessageFragment noticeMessageFragment = new NoticeMessageFragment();
            fragments.add(sentMessageFragment);
            fragments.add(receivedMessageFragment);
            fragments.add(noticeMessageFragment);
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
        DaggerMessageComponent.builder().appComponent(getAppComponent())
                .messageModule(new MessageModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
