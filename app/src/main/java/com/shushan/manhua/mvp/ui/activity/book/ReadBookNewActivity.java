package com.shushan.manhua.mvp.ui.activity.book;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.google.gson.Gson;
import com.shushan.manhua.BuildConfig;
import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerReadBookComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.ReadBookModule;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.request.AddBookShelfRequest;
import com.shushan.manhua.entity.request.ReadRecordingRequest;
import com.shushan.manhua.entity.request.ReadingBookRequest;
import com.shushan.manhua.entity.request.SelectionRequest;
import com.shushan.manhua.entity.request.ShareTaskRequest;
import com.shushan.manhua.entity.response.ReadingBookResponse;
import com.shushan.manhua.entity.response.SelectionResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.ireader.model.bean.BookChapterBean;
import com.shushan.manhua.ireader.model.bean.CollBookBean;
import com.shushan.manhua.ireader.model.local.BookRepository;
import com.shushan.manhua.ireader.model.local.ReadSettingManager;
import com.shushan.manhua.ireader.ui.adapter.CategoryAdapter;
import com.shushan.manhua.ireader.ui.dialog.ReadSettingDialog;
import com.shushan.manhua.ireader.utils.BrightnessUtils;
import com.shushan.manhua.ireader.utils.MD5Utils;
import com.shushan.manhua.ireader.utils.ScreenUtils;
import com.shushan.manhua.ireader.utils.StringUtils;
import com.shushan.manhua.ireader.utils.SystemBarUtils;
import com.shushan.manhua.ireader.widget.page.PageLoader;
import com.shushan.manhua.ireader.widget.page.PageView;
import com.shushan.manhua.ireader.widget.page.TxtChapter;
import com.shushan.manhua.mvp.ui.activity.login.LoginActivity;
import com.shushan.manhua.mvp.ui.activity.mine.BuyActivity;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.dialog.ReadUseCoinDialog;
import com.shushan.manhua.mvp.ui.dialog.SharePopupWindow;
import com.shushan.manhua.mvp.ui.dialog.ToLoginDialog;
import com.shushan.manhua.mvp.utils.LogUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 阅读小说 新版
 */

public class ReadBookNewActivity extends BaseActivity implements ReadBookControl.ReadBookView, ReadUseCoinDialog.ReadUseCoinDialogListener, ToLoginDialog.ToLoginDialogListener, SharePopupWindow.PopupWindowShareListener {
    @Inject
    ReadBookControl.PresenterReadBook mPresenter;
    private static final String TAG = "ReadActivity";
    public static final int REQUEST_MORE_SETTING = 1;
    public static final String EXTRA_COLL_BOOK = "extra_coll_book";
    //    public static final String EXTRA_COLL_CHAPTERID = "extra_is_chapterId";
    public static final String EXTRA_IS_COLLECTED = "extra_is_collected";

    // 注册 Brightness 的 uri
    private final Uri BRIGHTNESS_MODE_URI =
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE);
    private final Uri BRIGHTNESS_URI =
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
    private final Uri BRIGHTNESS_ADJ_URI =
            Settings.System.getUriFor("screen_auto_brightness_adj");

    private static final int WHAT_CATEGORY = 1;
    private static final int WHAT_CHAPTER = 2;


    @BindView(R.id.read_dl_slide)
    DrawerLayout mDlSlide;
    /*************top_menu_view*******************/
    @BindView(R.id.read_abl_top_menu)
    AppBarLayout mAblTopMenu;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.share_iv)
    ImageView mShareIv;
    /***************content_view******************/
    @BindView(R.id.read_pv_page)
    PageView mPvPage;
    /***************bottom_menu_view***************************/
    @BindView(R.id.read_tv_page_tip)
    TextView mTvPageTip;

    @BindView(R.id.read_ll_bottom_menu)
    LinearLayout mLlBottomMenu;
    @BindView(R.id.read_tv_pre_chapter)
    TextView mTvPreChapter;
    @BindView(R.id.read_sb_chapter_progress)
    SeekBar mSbChapterProgress;
    @BindView(R.id.read_tv_next_chapter)
    TextView mTvNextChapter;
    @BindView(R.id.read_tv_category)
    TextView mTvCategory;
    @BindView(R.id.read_tv_night_mode)
    TextView mTvNightMode;
    /*    @BindView(R.id.read_tv_download)
        TextView mTvDownload;*/
    @BindView(R.id.read_tv_setting)
    TextView mTvSetting;
    /***************left slide*******************************/
    @BindView(R.id.book_cover_iv)
    ImageView mBookCoverIv;
    @BindView(R.id.book_name_tv)
    TextView mBookNameTv;
    @BindView(R.id.total_chapter_num_tv)
    TextView mTotalChapterNumTv;
    @BindView(R.id.sort_tv)
    TextView mSortTv;
    @BindView(R.id.read_iv_category)
    ListView mLvCategory;
    /*****************view******************/
    private ReadSettingDialog mSettingDialog;
    private PageLoader mPageLoader;
    private Animation mTopInAnim;
    private Animation mTopOutAnim;
    private Animation mBottomInAnim;
    private Animation mBottomOutAnim;
    private CategoryAdapter mCategoryAdapter;
    private CollBookBean mCollBook;
    //控制屏幕常亮
    private PowerManager.WakeLock mWakeLock;
    //---
    private User mUser;
    private int mLoginModel = 1;//1 是游客模式 2 是登录模式
    private ReadUseCoinDialog mReadUseCoinDialog;//非免费章节 弹出购买弹框


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case WHAT_CATEGORY:
                    mLvCategory.setSelection(mPageLoader.getChapterPos());
                    break;
                case WHAT_CHAPTER:
                    mPageLoader.openChapter();
                    break;
            }
        }
    };
    // 接收电池信息和时间更新的广播
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                int level = intent.getIntExtra("level", 0);
                mPageLoader.updateBattery(level);
            }
            // 监听分钟的变化
            else if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                mPageLoader.updateTime();
            }
        }
    };

    // 亮度调节监听
    // 由于亮度调节没有 Broadcast 而是直接修改 ContentProvider 的。所以需要创建一个 Observer 来监听 ContentProvider 的变化情况。
    private ContentObserver mBrightObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange);

            // 判断当前是否跟随屏幕亮度，如果不是则返回
            if (selfChange || !mSettingDialog.isBrightFollowSystem()) return;

            // 如果系统亮度改变，则修改当前 Activity 亮度
            if (BRIGHTNESS_MODE_URI.equals(uri)) {
                Log.d(TAG, "亮度模式改变");
            } else if (BRIGHTNESS_URI.equals(uri) && !BrightnessUtils.isAutoBrightness(ReadBookNewActivity.this)) {
                Log.d(TAG, "亮度模式为手动模式 值改变");
                BrightnessUtils.setBrightness(ReadBookNewActivity.this, BrightnessUtils.getScreenBrightness(ReadBookNewActivity.this));
            } else if (BRIGHTNESS_ADJ_URI.equals(uri) && BrightnessUtils.isAutoBrightness(ReadBookNewActivity.this)) {
                Log.d(TAG, "亮度模式为自动模式 值改变");
                BrightnessUtils.setDefaultBrightness(ReadBookNewActivity.this);
            } else {
                Log.d(TAG, "亮度调整 其他");
            }
        }
    };

    /***************params*****************/
    private boolean isCollected = false; // isFromSDCard
    private boolean isNightMode = false;
    private boolean isFullScreen = false;
    private boolean isRegistered = false;

    private String mBookId;
    private int mSort = 0;//sort 0: 正序  1 ：逆序
    private int mToChapterPos = -1;

    public static void startActivity(Context context, CollBookBean collBook, boolean isCollected, int sort, int toChapterPos) {
        context.startActivity(new Intent(context, ReadBookNewActivity.class)
                .putExtra(EXTRA_IS_COLLECTED, isCollected)
                .putExtra("sort", sort)
                .putExtra("toChapterPos", toChapterPos)
                .putExtra(EXTRA_COLL_BOOK, collBook));
    }

    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(ActivityConstant.PAY_SUCCESS)) {//购买成功更新数据
                mUser = mBuProcessor.getUser();
                if (mReadUseCoinDialog != null) {
                    mReadUseCoinDialog.closeDialog();
                }
                onRequestSelectionInfo();
            } else if (intent.getAction().equals(ActivityConstant.LOGIN_SUCCESS_UPDATE_DATA)) {//登录成功
                mLoginModel = mBuProcessor.getLoginModel();
            }
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.PAY_SUCCESS);
        mFilter.addAction(ActivityConstant.LOGIN_SUCCESS_UPDATE_DATA);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_read_new);
        initInjectData();
        mUser = mBuProcessor.getUser();
        mLoginModel = mBuProcessor.getLoginModel();
    }

    @Override
    public void initView() {
        if (getIntent() != null) {
            mCollBook = getIntent().getParcelableExtra(EXTRA_COLL_BOOK);
            mSort = getIntent().getIntExtra("sort", 0);
            mToChapterPos = getIntent().getIntExtra("toChapterPos", -1);
            isCollected = getIntent().getBooleanExtra(EXTRA_IS_COLLECTED, false);
            mBookId = mCollBook.get_id();
            //设置标题
            mToolBar.setTitle(mCollBook.getTitle());
        }
        isNightMode = ReadSettingManager.getInstance().isNightMode();
        isFullScreen = ReadSettingManager.getInstance().isFullScreen();
        //半透明化StatusBar
        SystemBarUtils.transparentStatusBar(this);
        logViewContentEvent();
    }

    /**
     * 查看内容
     * This function assumes logger is an instance of AppEventsLogger and has been
     * created using AppEventsLogger.newLogger() call.
     */
    public void logViewContentEvent() {
        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "阅读小说页面");
        logger.logEvent(AppEventsConstants.EVENT_NAME_VIEWED_CONTENT, params);
    }

    @Override
    public void initData() {
        initWidget();
        initClick();
        onRequestSelectionInfo();
    }


    @SuppressLint("InvalidWakeLockTag")
    protected void initWidget() {
        //获取页面加载器
        mPageLoader = mPvPage.getPageLoader(mCollBook);
        //禁止滑动展示DrawerLayout
        mDlSlide.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //侧边打开后，返回键能够起作用
        mDlSlide.setFocusableInTouchMode(false);
        mSettingDialog = new ReadSettingDialog(this, mPageLoader);

        setUpAdapter();

        //夜间模式按钮的状态
        toggleNightMode();

        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(mReceiver, intentFilter);

        //设置当前Activity的Brightness
        if (ReadSettingManager.getInstance().isBrightnessAuto()) {
            BrightnessUtils.setDefaultBrightness(this);
        } else {
            BrightnessUtils.setBrightness(this, ReadSettingManager.getInstance().getBrightness());
        }

        //初始化屏幕常亮类
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "keep bright");

        //隐藏StatusBar
        mPvPage.post(
                () -> hideSystemBar()
        );

        //初始化TopMenu
        initTopMenu();

        //初始化BottomMenu
        initBottomMenu();

        //初始化侧边栏数据
        initSlideData();
    }

    //初始化侧边栏数据
    private void initSlideData() {
        if (mCollBook != null) {
            mImageLoaderHelper.displayRoundedCornerImage(this, mCollBook.getCover(), mBookCoverIv, 4, Constant.LOADING_DEFAULT_2);
            mBookNameTv.setText(mCollBook.getTitle());
        }
//        String totalWords = "Total " + mSelectionResponse.getWords() + " bab";
//        mTotalChapterNumTv.setText(totalWords);
    }

    private void initTopMenu() {
        mAblTopMenu.setPadding(0, ScreenUtils.getStatusBarHeight(), 0, 0);
    }

    private void initBottomMenu() {
        //判断是否全屏
        if (ReadSettingManager.getInstance().isFullScreen()) {
            //还需要设置mBottomMenu的底部高度
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLlBottomMenu.getLayoutParams();
            params.bottomMargin = ScreenUtils.getNavigationBarHeight();
            mLlBottomMenu.setLayoutParams(params);
        } else {
            //设置mBottomMenu的底部距离
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLlBottomMenu.getLayoutParams();
            params.bottomMargin = 0;
            mLlBottomMenu.setLayoutParams(params);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "onWindowFocusChanged: " + mAblTopMenu.getMeasuredHeight());
    }

    private void toggleNightMode() {
        if (isNightMode) {
            mTvNightMode.setText(StringUtils.getString(R.string.nb_mode_morning));
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_read_menu_morning);
            mTvNightMode.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        } else {
            mTvNightMode.setText(StringUtils.getString(R.string.nb_mode_night));
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_read_menu_night);
            mTvNightMode.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        }
    }

    private void setUpAdapter() {
        mCategoryAdapter = new CategoryAdapter();
        mLvCategory.setAdapter(mCategoryAdapter);
        mLvCategory.setFastScrollEnabled(true);
    }

    // 注册亮度观察者
    private void registerBrightObserver() {
        if (mBrightObserver != null) {
            if (!isRegistered) {
                final ContentResolver cr = getContentResolver();
                cr.unregisterContentObserver(mBrightObserver);
                cr.registerContentObserver(BRIGHTNESS_MODE_URI, false, mBrightObserver);
                cr.registerContentObserver(BRIGHTNESS_URI, false, mBrightObserver);
                cr.registerContentObserver(BRIGHTNESS_ADJ_URI, false, mBrightObserver);
                isRegistered = true;
            }
        }
    }

    //解注册
    private void unregisterBrightObserver() {
        try {
            if (mBrightObserver != null) {
                if (isRegistered) {
                    getContentResolver().unregisterContentObserver(mBrightObserver);
                    isRegistered = false;
                }
            }
        } catch (Throwable ignored) {
        }
    }

    /**
     * 请求书籍信息
     */
    private void onRequestBookInfo(String chapterId) {
        ReadingBookRequest readingBookRequest = new ReadingBookRequest();
        readingBookRequest.token = mBuProcessor.getToken();
        readingBookRequest.book_id = mBookId;
        readingBookRequest.catalogue_id = chapterId;
        mPresenter.onRequestBookInfo(readingBookRequest);
    }

    private int readChapterNum = 0;//阅读章节数量

    @Override
    public void getReadingBookInfoSuccess(ReadingBookResponse readingBookResponse) {
        LogUtils.e("readingBookResponse:" + new Gson().toJson(readingBookResponse));
        if (readingBookResponse.getCatalogue().getCatalogue_id() == 0) {
            showRechargeDialog(); //进行弹框
        } else {
            onRequestReadRecording(String.valueOf(readingBookResponse.getCatalogue().getCatalogue_id()), String.valueOf(readingBookResponse.getCatalogue().getType()));//阅读上报
        }
    }


    @Override
    public void getReadingBookInfoFail() {
    }


    /**
     * 加入书架
     */
    private void onAddBookShelfRequest() {
        AddBookShelfRequest addBookShelfRequest = new AddBookShelfRequest();
        addBookShelfRequest.token = mBuProcessor.getToken();
        addBookShelfRequest.book_id = mBookId;
        mPresenter.onAddBookShelfRequest(addBookShelfRequest);
    }

    @Override
    public void getAddBookShelfSuccess() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_BOOKSHELF));
    }

    /**
     * 阅读上报
     * type : 1 免费 2 收费
     */
    public void onRequestReadRecording(String chapterId, String type) {
        ReadRecordingRequest readRecordingRequest = new ReadRecordingRequest();
        readRecordingRequest.token = mBuProcessor.getToken();
        readRecordingRequest.book_id = mBookId;
        readRecordingRequest.catalogue_id = chapterId;
        readRecordingRequest.type = type;
        readRecordingRequest.bean = "0";//漫豆 为0
        mPresenter.onRequestReadRecording(readRecordingRequest);
    }

    @Override
    public void getReadRecordingSuccess() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_PERSONAL_INFO));
    }

    /**
     * 非免费章节 显示使用漫豆弹框
     */
    public void showRechargeDialog() {
        mReadUseCoinDialog = ReadUseCoinDialog.newInstance();
        mReadUseCoinDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), mReadUseCoinDialog, ReadUseCoinDialog.TAG);
    }

    /**
     * 请求书籍选集信息
     */
    private void onRequestSelectionInfo() {
        SelectionRequest selectionRequest = new SelectionRequest();
        selectionRequest.token = mBuProcessor.getToken();
        selectionRequest.book_id = mBookId;
        selectionRequest.orderby = "asc";
        selectionRequest.page = "1";
        selectionRequest.pagesize = "2000";//
        mPresenter.onRequestSelectionInfo(selectionRequest);
    }

    @Override
    public void getSelectionInfoSuccess(SelectionResponse selectionResponse) {
        List<BookChapterBean> bookChapters = new ArrayList<>();
        for (SelectionResponse.AnthologyBean anthologyBean : selectionResponse.getAnthology()) {
            BookChapterBean bookChapterBean = new BookChapterBean();
            bookChapterBean.setId(MD5Utils.strToMd5By16(mBookId));
            bookChapterBean.setBookId(mBookId);
            bookChapterBean.setChapterId(String.valueOf(anthologyBean.getCatalogue_id()));
            if (anthologyBean.getType() == 0) {//|| (mUser.vip == 1 && mReadingBookResponse.getCatalogue().getVip_cost() == 0)
                bookChapterBean.setUnreadble(false);
            } else {
                bookChapterBean.setUnreadble(true);
            }
            bookChapterBean.setLink(anthologyBean.getNovel_url());
            bookChapterBean.setTitle(anthologyBean.getCatalogue_name());
            bookChapters.add(bookChapterBean);
        }
        mPageLoader.getCollBook().setBookChapters(bookChapters);
        mPageLoader.refreshChapterList(mToChapterPos == -1);
        // 如果是目录更新的情况，那么就需要存储更新数据
        if (mCollBook.isUpdate() && isCollected) {
            BookRepository.getInstance()
                    .saveBookChaptersWithAsync(bookChapters);
        }
        if (mToChapterPos != -1) {//跳到指定章节
            if (mSort == 0) {
                mPageLoader.skipToChapter(mToChapterPos);
            } else {
                mPageLoader.skipToChapter(bookChapters.size() - mToChapterPos);
            }
        }
        onAddBookShelfRequest();//自动加入书架
    }


    /**
     * 去登陆dialog
     */
    private void showLoginDialog() {
        ToLoginDialog toLoginDialog = ToLoginDialog.newInstance();
        toLoginDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), toLoginDialog, ToLoginDialog.TAG);
    }

    @Override
    public void goLoginListener() {
        startActivitys(LoginActivity.class);
    }

    protected void initClick() {
        mShareIv.setOnClickListener(v -> new SharePopupWindow(ReadBookNewActivity.this, ReadBookNewActivity.this).initPopWindow(mDlSlide));

        mPageLoader.setOnPageChangeListener(
                new PageLoader.OnPageChangeListener() {

                    @Override
                    public void onChapterChange(int pos, boolean isSizeChange) {
                        mCategoryAdapter.setChapter(pos);
                        if (!isSizeChange) {
                            readChapterNum++;
                            if (readChapterNum == 4 && mLoginModel == 1) {
                                showLoginDialog();
                            }
                        }
                    }

                    @Override
                    public void requestChapters(List<TxtChapter> requestChapters) {
                        mPresenter.loadChapter(mBookId, requestChapters, mBuProcessor.getToken());
                        mHandler.sendEmptyMessage(WHAT_CATEGORY);
                        //隐藏提示
                        mTvPageTip.setVisibility(GONE);
                    }

                    @Override
                    public void onCategoryFinish(List<TxtChapter> chapters) {
                        for (TxtChapter chapter : chapters) {
                            chapter.setTitle(StringUtils.convertCC(chapter.getTitle(), mPvPage.getContext()));
                        }
                        mCategoryAdapter.refreshItems(chapters);
                    }

                    @Override
                    public void onPageCountChange(int count) {
                        mSbChapterProgress.setMax(Math.max(0, count - 1));
                        mSbChapterProgress.setProgress(0);
                        // 如果处于错误状态，那么就冻结使用
                        if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING
                                || mPageLoader.getPageStatus() == PageLoader.STATUS_ERROR) {
                            mSbChapterProgress.setEnabled(false);
                        } else {
                            mSbChapterProgress.setEnabled(true);
                        }
                    }

                    @Override
                    public void onPageChange(int pos) {
                        mSbChapterProgress.post(
                                () -> mSbChapterProgress.setProgress(pos)
                        );
                    }

                    @Override
                    public void onPageUnReadble(TxtChapter txtChapter) {
                        LogUtils.e("txtChapter:" + new Gson().toJson(txtChapter));
                        if (txtChapter.isUnreadble()) {//不能读
                            toggleMenu(false);
                            //如果是非免费章节请求章节信息
                            onRequestBookInfo(txtChapter.getChapterId());
                        } else {
                            onRequestReadRecording(txtChapter.getChapterId(), "1");//阅读上报
                        }
                    }
                }
        );

        mSbChapterProgress.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (mLlBottomMenu.getVisibility() == VISIBLE) {
                            //显示标题
                            mTvPageTip.setText((progress + 1) + "/" + (mSbChapterProgress.getMax() + 1));
                            mTvPageTip.setVisibility(VISIBLE);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        //进行切换
                        int pagePos = mSbChapterProgress.getProgress();
                        if (pagePos != mPageLoader.getPagePos()) {
                            mPageLoader.skipToPage(pagePos);
                        }
                        //隐藏提示
                        mTvPageTip.setVisibility(GONE);
                    }
                }
        );

        mPvPage.setTouchListener(new PageView.TouchListener() {
            @Override
            public boolean onTouch() {
                return !hideReadMenu();
            }

            @Override
            public void center() {
                toggleMenu(true);
            }

            @Override
            public void prePage() {
            }

            @Override
            public void nextPage() {
            }

            @Override
            public void cancel() {
            }
        });

        mLvCategory.setOnItemClickListener(
                (parent, view, position, id) -> {
                    mDlSlide.closeDrawer(Gravity.START);
                    mPageLoader.skipToChapter(position);
                }
        );

        mTvCategory.setOnClickListener(
                (v) -> {
                    //移动到指定位置
                    if (mCategoryAdapter.getCount() > 0) {
                        mLvCategory.setSelection(mPageLoader.getChapterPos());
                    }
                    //切换菜单
                    toggleMenu(true);
                    //打开侧滑动栏
                    mDlSlide.openDrawer(Gravity.START);
                }
        );
        mTvSetting.setOnClickListener(
                (v) -> {
                    toggleMenu(false);
                    mSettingDialog.show();
                }
        );

        //上一章
        mTvPreChapter.setOnClickListener(
                (v) -> {
                    if (mPageLoader.skipPreChapter()) {
                        mCategoryAdapter.setChapter(mPageLoader.getChapterPos());
                    }
                }
        );

        mTvNextChapter.setOnClickListener(
                (v) -> {
                    if (mPageLoader.skipNextChapter()) {
                        mCategoryAdapter.setChapter(mPageLoader.getChapterPos());
                    }
                }
        );

        mTvNightMode.setOnClickListener(
                (v) -> {
                    isNightMode = !isNightMode;
                    mPageLoader.setNightMode(isNightMode);
                    toggleNightMode();
                }
        );

//        mTvCommunity.setOnClickListener(
//                (v) -> {
//                    Intent intent = new Intent(this, CommunityActivity.class);
//                    startActivity(intent);
//                }
//        );

        mSettingDialog.setOnDismissListener(
                dialog -> hideSystemBar()
        );
    }

    /**
     * 隐藏阅读界面的菜单显示
     *
     * @return 是否隐藏成功
     */
    private boolean hideReadMenu() {
        hideSystemBar();
        if (mAblTopMenu.getVisibility() == VISIBLE) {
            toggleMenu(true);
            return true;
        } else if (mSettingDialog.isShowing()) {
            mSettingDialog.dismiss();
            return true;
        }
        return false;
    }

    private void showSystemBar() {
        //显示
        SystemBarUtils.showUnStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.showUnStableNavBar(this);
        }
    }

    private void hideSystemBar() {
        //隐藏
        SystemBarUtils.hideStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.hideStableNavBar(this);
        }
    }

    /**
     * 切换菜单栏的可视状态
     * 默认是隐藏的
     */
    private void toggleMenu(boolean hideStatusBar) {
        initMenuAnim();

        if (mAblTopMenu.getVisibility() == View.VISIBLE) {
            //关闭
            mAblTopMenu.startAnimation(mTopOutAnim);
            mLlBottomMenu.startAnimation(mBottomOutAnim);
            mAblTopMenu.setVisibility(GONE);
            mLlBottomMenu.setVisibility(GONE);
            mTvPageTip.setVisibility(GONE);

            if (hideStatusBar) {
                hideSystemBar();
            }
        } else {
            mAblTopMenu.setVisibility(View.VISIBLE);
            mLlBottomMenu.setVisibility(View.VISIBLE);
            mAblTopMenu.startAnimation(mTopInAnim);
            mLlBottomMenu.startAnimation(mBottomInAnim);

            showSystemBar();
        }
    }

    //初始化菜单动画
    private void initMenuAnim() {
        if (mTopInAnim != null) return;
        mTopInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_in);
        mTopOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_out);
        mBottomInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_in);
        mBottomOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_out);
        //退出的速度要快
        mTopOutAnim.setDuration(200);
        mBottomOutAnim.setDuration(200);
    }


    /***************************view************************************/

    @Override
    public void finishChapter() {
        if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING) {
            mHandler.sendEmptyMessage(WHAT_CHAPTER);
        }
        // 当完成章节的时候，刷新列表
        mCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void errorChapter() {
        if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING) {
            mPageLoader.chapterError();
        }
    }

    @Override
    public void onBackPressed() {
        if (mAblTopMenu.getVisibility() == View.VISIBLE) {
            // 非全屏下才收缩，全屏下直接退出
            if (!ReadSettingManager.getInstance().isFullScreen()) {
                toggleMenu(true);
                return;
            }
        } else if (mSettingDialog.isShowing()) {
            mSettingDialog.dismiss();
            return;
        } else if (mDlSlide.isDrawerOpen(Gravity.START)) {
            mDlSlide.closeDrawer(Gravity.START);
            return;
        }

        exit();
    }

    // 退出
    private void exit() {
        // 退出
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBrightObserver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWakeLock.acquire();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWakeLock.release();
        if (isCollected) {
            mPageLoader.saveRecord();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterBrightObserver();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);

        mHandler.removeMessages(WHAT_CATEGORY);
        mHandler.removeMessages(WHAT_CHAPTER);

        mPageLoader.closeBook();
        mPageLoader = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isVolumeTurnPage = ReadSettingManager
                .getInstance().isVolumeTurnPage();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (isVolumeTurnPage) {
                    return mPageLoader.skipToPrePage();
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (isVolumeTurnPage) {
                    return mPageLoader.skipToNextPage();
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SystemBarUtils.hideStableStatusBar(this);
        if (requestCode == REQUEST_MORE_SETTING) {
            boolean fullScreen = ReadSettingManager.getInstance().isFullScreen();
            if (isFullScreen != fullScreen) {
                isFullScreen = fullScreen;
                // 刷新BottomMenu
                initBottomMenu();
            }

            // 设置显示状态
            if (isFullScreen) {
                SystemBarUtils.hideStableNavBar(this);
            } else {
                SystemBarUtils.showStableNavBar(this);
            }
        }
    }


    /**
     * 去充值弹框  去充值
     */
    @Override
    public void readUseCoinDialogBtnOkListener() {
        //这里 只会跳到购买
        //去购买漫豆
        startActivitys(BuyActivity.class);
    }

    /**
     * 收费章节
     * 取消兑换阅读
     */
    @Override
    public void cancelReadingBtnOkListener() {
        finish();
    }

    @Override
    public void shareFacebookBtnListener() {
        shareFacebook();
    }

    @Override
    public void shareWhatsAppBtnListener() {
        shareWhatsApp();
    }

    private void shareFacebook() {
        //分享到facebook
        SnsPlatform snsPlatform = SHARE_MEDIA.FACEBOOK.toSnsPlatform();
        //分享链接
        UMWeb web = new UMWeb(BuildConfig.MAN_HUA_BASE_URL + "/download.html ");
//        web.setTitle("download");
        web.setThumb(new UMImage(this, R.mipmap.logo));
        new ShareAction(this)
                .withMedia(web)
                .setPlatform(snsPlatform.mPlatform)
                .setCallback(null).share();
        onShareTaskRequest();
    }

    private void shareWhatsApp() {
        SnsPlatform snsPlatform = SHARE_MEDIA.WHATSAPP.toSnsPlatform();
        //分享链接
        UMWeb web = new UMWeb(BuildConfig.MAN_HUA_BASE_URL + "/download.html ");
//        web.setTitle("download");
        web.setThumb(new UMImage(this, R.mipmap.logo));
        new ShareAction(this)
                .withMedia(web)
                .setPlatform(snsPlatform.mPlatform)
                .setCallback(null).share();
        onShareTaskRequest();
    }

    /**
     * 分享成功 上传
     */
    private void onShareTaskRequest() {
        ShareTaskRequest shareTaskRequest = new ShareTaskRequest();
        shareTaskRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestShareTask(shareTaskRequest);
    }

    private void initInjectData() {
        DaggerReadBookComponent.builder().appComponent(getAppComponent())
                .readBookModule(new ReadBookModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
