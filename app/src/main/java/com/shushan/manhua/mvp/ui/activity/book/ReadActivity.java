package com.shushan.manhua.mvp.ui.activity.book;

import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerReadComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.ReadModule;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.views.ResizableImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 阅读页面
 */
public class ReadActivity extends BaseActivity implements ReadControl.ReadView {

    @Inject
    ReadControl.PresenterRead mPresenter;
    @BindView(R.id.common_title_tv)
    TextView mCommonTitleTv;
    @BindView(R.id.common_right_tv)
    TextView mCommonRightTv;
    @BindView(R.id.nestedScrollView)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.barrage_iv)
    ImageView mBarrageIv;
    @BindView(R.id.send_tv)
    TextView mSendTv;
    @BindView(R.id.comment_num_tv)
    TextView mCommentNumTv;
    @BindView(R.id.image)
    ResizableImageView mImage;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_read);
        initInjectData();
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.common_left_iv, R.id.common_right_tv, R.id.send_tv, R.id.bottom_directory_ll, R.id.last_chapter_iv, R.id.next_chapter_iv, R.id.barrage_ll,
            R.id.bottom_comment_ll, R.id.bottom_share_ll, R.id.bottom_setting_ll, R.id.back_top_ll, R.id.add_bookshelf_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.common_right_tv:
                //全集
                break;
            case R.id.send_tv:
                //发送
                break;
            case R.id.barrage_ll:
                //设置弹幕
//                mBarrageIv
                break;
            case R.id.last_chapter_iv:
                //上一话
                break;
            case R.id.next_chapter_iv:
                //下一话
                break;
            case R.id.bottom_directory_ll:
                //目录
                break;
            case R.id.bottom_comment_ll:
                //评论
                break;
            case R.id.bottom_share_ll:
                break;
            case R.id.bottom_setting_ll:
                break;
            case R.id.back_top_ll:
                //返回顶部
                // 让页面返回顶部
                mNestedScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        mNestedScrollView.post(new Runnable() {
                            public void run() {
                                // 滚动至顶部
                                mNestedScrollView.fullScroll(ScrollView.FOCUS_UP);
                                // 滚动到底部
                                //sc.fullScroll(ScrollView.FOCUS_DOWN);
                            }
                        });
                    }
                });
                break;
            case R.id.add_bookshelf_ll:
                //加入书架
                showToast("加入书架");
                break;
        }
    }

    private void initInjectData() {
        DaggerReadComponent.builder().appComponent(getAppComponent())
                .readModule(new ReadModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }
}
