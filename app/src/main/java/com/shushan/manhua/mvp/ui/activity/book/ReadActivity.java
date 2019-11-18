package com.shushan.manhua.mvp.ui.activity.book;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerReadComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.ReadModule;
import com.shushan.manhua.entity.response.ReadingCommendResponse;
import com.shushan.manhua.entity.response.ReadingRecommendResponse;
import com.shushan.manhua.mvp.ui.adapter.ReadingCommentAdapter;
import com.shushan.manhua.mvp.ui.adapter.ReadingRecommendAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.views.ResizableImageView;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.send_message_left_iv)
    ImageView mSendMessageLeftIv;
    @BindView(R.id.send_message_right_iv)
    ImageView mSendMessageRightIv;
    @BindView(R.id.send_tv)
    TextView mSendTv;
    @BindView(R.id.comment_num_tv)
    TextView mCommentNumTv;
    @BindView(R.id.image)
    ResizableImageView mImage;
    @BindView(R.id.add_bookshelf_ll)
    LinearLayout mAddBookshelfLl;
    @BindView(R.id.read_bottom_ll)
    LinearLayout mReadBottomLl;//底部布局
    @BindView(R.id.barrage_ll)
    LinearLayout mBarrageLl;//弹幕布局
    @BindView(R.id.message_tv)
    EditText mMessageTv;
    @BindView(R.id.recommend_recycler_view)
    RecyclerView mRecommendRecyclerView;
    @BindView(R.id.comment_recycler_view)
    RecyclerView mCommentRecyclerView;
    boolean mBarrage;//是否弹幕
    private List<ReadingRecommendResponse> readingRecommendResponseList = new ArrayList<>();
    private List<ReadingCommendResponse> readingCommendResponseList = new ArrayList<>();

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_read);
        initInjectData();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        mBarrage = mSharePreferenceUtil.getBooleanData("barrage");
        if (mBarrage) {
            mBarrageIv.setImageResource(R.mipmap.barrage_open);
        } else {
            mBarrageIv.setImageResource(R.mipmap.barrage_close);
        }
        initScrollView();
        ReadingRecommendAdapter mReadingRecommendAdapter = new ReadingRecommendAdapter(readingRecommendResponseList);
        mRecommendRecyclerView.setAdapter(mReadingRecommendAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecommendRecyclerView.setLayoutManager(linearLayoutManager);
        ReadingCommentAdapter mReadingCommentAdapter = new ReadingCommentAdapter(readingCommendResponseList);
        mCommentRecyclerView.setAdapter(mReadingCommentAdapter);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initScrollView() {
        mNestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                LogUtils.e("scrollY:" + scrollY + "  Height:" + mImage.getHeight());
                //mNestedScrollView.getChildAt(0).getMeasuredHeight()- mNestedScrollView.getMeasuredHeight()
                if (scrollY >= mImage.getHeight()) {//设置隐藏功能键
                    if (mReadBottomLl.getVisibility() == View.VISIBLE) {
                        showFunction();
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        for (int i = 0; i < 5; i++) {
            ReadingRecommendResponse readingRecommendResponse = new ReadingRecommendResponse();
            readingRecommendResponseList.add(readingRecommendResponse);
        }
        for (int i = 0; i < 5; i++) {
            ReadingCommendResponse readingRecommendResponse = new ReadingCommendResponse();
            readingCommendResponseList.add(readingRecommendResponse);
        }

    }

    @OnClick({R.id.common_left_iv, R.id.common_right_tv, R.id.image, R.id.send_tv, R.id.bottom_directory_ll, R.id.last_chapter_iv, R.id.next_chapter_iv, R.id.barrage_ll, R.id.send_message_left_iv, R.id.send_message_right_iv,
            R.id.support_tv, R.id.add_bookshelf_tv, R.id.share_tv, R.id.last_chapter_ll, R.id.next_chapter_ll, R.id.bottom_comment_ll, R.id.bottom_share_ll, R.id.bottom_setting_ll, R.id.back_top_ll, R.id.add_bookshelf_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.common_right_tv: //全集
                break;
            case R.id.image: //点击图片
                showFunction();
                break;
            case R.id.send_tv: //发送
                break;
            case R.id.barrage_ll://设置弹幕
                mBarrage = mSharePreferenceUtil.getBooleanData("barrage");
                if (mBarrage) {
                    mSharePreferenceUtil.setData("barrage", false);
                    mBarrageIv.setImageResource(R.mipmap.barrage_close);
                } else {
                    mSharePreferenceUtil.setData("barrage", true);
                    mBarrageIv.setImageResource(R.mipmap.barrage_open);
                }
                break;
            case R.id.send_message_left_iv:  //切换弹幕和评论
                break;
            case R.id.send_message_right_iv://打开选择弹幕样式
                break;
            case R.id.support_tv://点赞
                break;
            case R.id.add_bookshelf_tv://加入书架
                break;
            case R.id.share_tv://分享
                break;
            case R.id.last_chapter_ll://上一篇
                break;
            case R.id.next_chapter_ll://下一篇
                break;
            case R.id.last_chapter_iv: //上一话

                break;
            case R.id.next_chapter_iv: //下一话

                break;
            case R.id.bottom_directory_ll: //目录

                break;
            case R.id.bottom_comment_ll://评论

                break;
            case R.id.bottom_share_ll://分享
                break;
            case R.id.bottom_setting_ll://设置
                break;
            case R.id.back_top_ll:// 让页面返回顶部
                mNestedScrollView.post(() -> mNestedScrollView.post(new Runnable() {
                    public void run() {
                        // 滚动至顶部
                        mNestedScrollView.fullScroll(ScrollView.FOCUS_UP);
                        // 滚动到底部
                        //sc.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                }));
                break;
            case R.id.add_bookshelf_ll://加入书架
                showToast("加入书架");
                break;
        }
    }

    private void showFunction() {
        if (mReadBottomLl.getVisibility() == View.VISIBLE) {
            mReadBottomLl.setVisibility(View.INVISIBLE);
            mBarrageLl.setVisibility(View.INVISIBLE);
            mAddBookshelfLl.setVisibility(View.INVISIBLE);
        } else {
            mReadBottomLl.setVisibility(View.VISIBLE);
            mBarrageLl.setVisibility(View.VISIBLE);
            mAddBookshelfLl.setVisibility(View.VISIBLE);
        }
    }


    private void initInjectData() {
        DaggerReadComponent.builder().appComponent(getAppComponent())
                .readModule(new ReadModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
