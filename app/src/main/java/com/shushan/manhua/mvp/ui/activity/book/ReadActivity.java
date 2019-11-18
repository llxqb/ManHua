package com.shushan.manhua.mvp.ui.activity.book;

import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerReadComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.ReadModule;
import com.shushan.manhua.entity.response.ChapterResponse;
import com.shushan.manhua.entity.response.ReadingCommendResponse;
import com.shushan.manhua.entity.response.ReadingRecommendResponse;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.mvp.ui.activity.setting.SettingActivity;
import com.shushan.manhua.mvp.ui.adapter.ReadingCommentAdapter;
import com.shushan.manhua.mvp.ui.adapter.ReadingRecommendAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.dialog.ReadBeansExchangeDialog;
import com.shushan.manhua.mvp.ui.dialog.ReadContentsPopupWindow;
import com.shushan.manhua.mvp.ui.dialog.ReadOpenVipDialog;
import com.shushan.manhua.mvp.ui.dialog.ReadSettingPopupWindow;
import com.shushan.manhua.mvp.ui.dialog.ReadUseCoinDialog;
import com.shushan.manhua.mvp.views.ResizableImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 阅读页面
 */
public class ReadActivity extends BaseActivity implements ReadControl.ReadView, ReadUseCoinDialog.ReadUseCoinDialogListener, ReadBeansExchangeDialog.ReadBeansExchangeDialogListener,
        ReadOpenVipDialog.ReadOpenVipDialogListener, ReadSettingPopupWindow.ReadSettingPopupWindowListener {
    @Inject
    ReadControl.PresenterRead mPresenter;
    @BindView(R.id.read_layout)
    RelativeLayout mReadLayout;
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
    private List<ReadingRecommendResponse> readingRecommendResponseList = new ArrayList<>();//推荐list
    private List<ReadingCommendResponse> readingCommendResponseList = new ArrayList<>();//评论
    private List<ChapterResponse> chapterResponseList = new ArrayList<>();//章节list

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
        for (int i = 0; i < 20; i++) {
            ChapterResponse chapterResponse = new ChapterResponse();
            chapterResponse.title = "title"+i;
            chapterResponseList.add(chapterResponse);
        }
    }

    /**
     * 显示去充值弹框
     */
    private void showRechargeDialog() {
        ReadUseCoinDialog readUseCoinDialog = ReadUseCoinDialog.newInstance();
        readUseCoinDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), readUseCoinDialog, ReadUseCoinDialog.TAG);
    }

    /**
     * 显示漫豆兑换弹幕弹框
     */
    private void showBeansExchangeDialog() {
        ReadBeansExchangeDialog readBeansExchangeDialog = ReadBeansExchangeDialog.newInstance();
        readBeansExchangeDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), readBeansExchangeDialog, ReadBeansExchangeDialog.TAG);
    }

    /**
     * 显示开通会员弹框
     */
    private void showOpenVipDialog() {
        ReadOpenVipDialog readOpenVipDialog = ReadOpenVipDialog.newInstance();
        readOpenVipDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), readOpenVipDialog, ReadOpenVipDialog.TAG);
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
                new ReadContentsPopupWindow(this, chapterResponseList ).initPopWindow(mReadLayout);
                break;
            case R.id.bottom_comment_ll://评论

                break;
            case R.id.bottom_share_ll://分享
                break;
            case R.id.bottom_setting_ll://设置
                new ReadSettingPopupWindow(this, this, mSharePreferenceUtil).initPopWindow(mReadLayout);
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

    /**
     * 去充值弹框  去充值
     */
    @Override
    public void readUseCoinDialogBtnOkListener() {

    }

    /**
     * 漫豆兑换弹幕弹框  去兑换
     */
    @Override
    public void readBeansExchangeDialogBtnOkListener() {

    }

    /**
     * 显示开通会员弹框  开通会员
     */
    @Override
    public void readOpenVipDialogBtnOkListener() {

    }

    /**
     * 点击翻页开关
     */
    @Override
    public void pageTurningBtnListener() {

    }

    /**
     * 夜间模式开关
     */
    @Override
    public void nightModelBtnListener(boolean nightModel) {
        if (nightModel) {
            //设置白天模式
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 120);//屏幕亮度值范围必须位于：0～255
        } else {
            //设置夜间模式
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 25);//屏幕亮度值范围必须位于：0～255
        }
    }

    /**
     * 关闭弹幕开关
     */
    @Override
    public void barrageSwitchBtnListener() {

    }

    /**
     * 更多设置
     */
    @Override
    public void clickMoreBtnListener() {
        startActivitys(SettingActivity.class);
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
