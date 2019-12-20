package com.shushan.manhua.mvp.ui.activity.book;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

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
import com.shushan.manhua.listener.DownloadListener;
import com.shushan.manhua.mvp.ui.activity.login.LoginActivity;
import com.shushan.manhua.mvp.ui.activity.mine.BuyActivity;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.bean.TxtChar;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.bean.TxtMsg;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces.ICenterAreaClickListener;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces.ILoadListener;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces.ISliderListener;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces.ITextSelectListener;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.main.TxtConfig;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.main.TxtReaderView;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.dialog.ChapterListPopupWindow;
import com.shushan.manhua.mvp.ui.dialog.ExitReadingBookPopupWindow;
import com.shushan.manhua.mvp.ui.dialog.ReadUseCoinDialog;
import com.shushan.manhua.mvp.ui.dialog.SharePopupWindow;
import com.shushan.manhua.mvp.utils.BrightnessTools;
import com.shushan.manhua.mvp.utils.DownloadUtil;
import com.shushan.manhua.mvp.utils.FileUtils;
import com.shushan.manhua.mvp.utils.LogUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 阅读小说
 */
public class ReadBookActivity extends BaseActivity implements ReadBookControl.ReadBookView, ChapterListPopupWindow.ChapterListPopupWindowListener,
        ReadUseCoinDialog.ReadUseCoinDialogListener, SharePopupWindow.PopupWindowShareListener, ExitReadingBookPopupWindow.PopupWindowListener {

    @Inject
    ReadBookControl.PresenterReadBook mPresenter;
    protected Handler mHandler;
    @BindView(R.id.read_book_layout)
    RelativeLayout mReadBookLayout;
    @BindView(R.id.activity_hwtxtplay_chapter_msg)
    View mChapterMsgView;
    @BindView(R.id.chapter_name)
    TextView mChapterMsgName;
    @BindView(R.id.charpter_progress)
    TextView mChapterMsgProgress;
    @BindView(R.id.activity_hwtxtplay_top)
    RelativeLayout mTopDecoration;
    @BindView(R.id.activity_hwtxtplay_bottom)
    ConstraintLayout mBottomDecoration;
    @BindView(R.id.activity_hwtxtplay_readerView)
    TxtReaderView mTxtReaderView;
    @BindView(R.id.activity_hwtxtplay_chaptername)
    TextView mChapterNameText;
    @BindView(R.id.activity_hwtxtplay_chapter_menutext)
    TextView mChapterMenuText;
    @BindView(R.id.chapter_seekBar)
    SeekBar mChapterSeekBar;
    @BindView(R.id.pre_chapter_tv)
    TextView mPreChapterTv;
    @BindView(R.id.next_chapter_tv)
    TextView mNextChapterTv;
    @BindView(R.id.directory_tv)
    TextView mDirectoryTv;
    @BindView(R.id.read_model_tv)
    TextView mReadModelTv;
    @BindView(R.id.activity_hwtxtplay_setting_text)
    TextView mSettingText;
    @BindView(R.id.activity_hwtxtplay_menu_top)
    View mTopMenu;
    @BindView(R.id.activity_hwtxtplay_menu_bottom)
    View mBottomMenu;
    @BindView(R.id.activity_hwtxtplay_cover)
    View mCoverView;
    @BindView(R.id.activity_hwtxtplay_Clipboar)
    RelativeLayout ClipboardView;
    @BindView(R.id.activity_hwtxtplay_selected_text)
    TextView mSelectedText;

    //menu 里面控件
    @BindView(R.id.txtreadr_menu_title)
    TextView mMenuTitle;
    @BindView(R.id.txtreadr_menu_brightness_system)
    TextView mMenuBrightnessSystem;
    @BindView(R.id.txtreadr_menu_chapter_pre)
    TextView mMenuPreChapter;
    @BindView(R.id.txtreadr_menu_chapter_next)
    TextView mMenuNextChapter;
    @BindView(R.id.txtreadr_menu_brightness_seekbar)
    SeekBar mMenuSeekBar;
    @BindView(R.id.txtreadr_menu_textsize_del)
    ImageView mMenuTextSizeDel;
    @BindView(R.id.txtreadr_menu_textsize)
    TextView mMenuTextSize;
    @BindView(R.id.txtreadr_menu_textsize_add)
    ImageView mMenuTextSizeAdd;
    @BindView(R.id.txtreadr_menu_textsetting1_bold)
    LinearLayout mMenuBoldSelectedLayout;
    @BindView(R.id.txtreadr_menu_textsetting1_normal)
    LinearLayout mMenuNormalSelectedLayout;
    @BindView(R.id.txtreadr_menu_textsetting2_cover)
    LinearLayout mMenuCoverSelectedLayout;
    @BindView(R.id.txtreadr_menu_textsetting2_shear)
    LinearLayout mMenuShearSelectedLayout;
    @BindView(R.id.txtreadr_menu_textsetting2_translate)
    LinearLayout mMenuTranslateSelectedLayout;
    @BindView(R.id.hwtxtreader_menu_style1)
    View mMenuStyle1;
    @BindView(R.id.hwtxtreader_menu_style2)
    View mMenuStyle2;
    @BindView(R.id.hwtxtreader_menu_style3)
    View mMenuStyle3;
    @BindView(R.id.hwtxtreader_menu_style4)
    View mMenuStyle4;
    @BindView(R.id.hwtxtreader_menu_style5)
    View mMenuStyle5;
    @BindView(R.id.txtreadr_menu_page_model1_tv)
    TextView mMenuPageModel1Tv;
    @BindView(R.id.txtreadr_menu_page_model2_tv)
    TextView mMenuPageModel2Tv;
    private String mBookId;
    private int mCatalogueId;// 当前章节id
    private ReadingBookResponse mReadingBookResponse;
    private SelectionResponse mSelectionResponse;
    private ChapterListPopupWindow mChapterListPopupWindow;
    private User mUser;
    private ReadUseCoinDialog mReadUseCoinDialog;//非免费章节 弹出购买弹框
    public int mLoginModel;//1 是游客模式 2 是登录模式

    public static void start(Context context, String bookId, int catalogueId) {
        Intent intent = new Intent(context, ReadBookActivity.class);
        intent.putExtra("bookId", bookId);
        intent.putExtra("catalogueId", catalogueId);
        context.startActivity(intent);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_read_book);
        initInjectData();
        mUser = mBuProcessor.getUser();
        mLoginModel = mBuProcessor.getLoginModel();
    }

    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(ActivityConstant.PAY_SUCCESS)) {//购买成功更新数据
                mUser = mBuProcessor.getUser();
                if (mReadUseCoinDialog != null) {
                    mReadUseCoinDialog.closeDialog();
                    setIsRecharge();//重新判断
                }
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
    public void initView() {
        if (getIntent() != null) {
            mBookId = getIntent().getStringExtra("bookId");
            mCatalogueId = getIntent().getIntExtra("catalogueId", 1);
            onRequestBookInfo();
        }
    }

    @Override
    public void initData() {
        init();
        registerListener();
        boolean lingSystem = mSharePreferenceUtil.getBooleanData(Constant.LING_SYSTEM, true);
        int lingValue = mSharePreferenceUtil.getIntData(Constant.SET_LING, BrightnessTools.getScreenBrightness(this));
        int readPageModel = mSharePreferenceUtil.getIntData(Constant.READ_PAGE_MODEL, 0);
        boolean nightModelFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_NIGHT_MODEL, false);////夜间模式
        if (lingSystem) {
            mMenuBrightnessSystem.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.novel_settings_oval_choose), null, null, null);
        } else {
            mMenuBrightnessSystem.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.novel_settings_oval), null, null, null);
        }
        mMenuSeekBar.setProgress(lingValue);
        if (readPageModel == 0) {
            mMenuPageModel1Tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.novel_settings_oval_choose), null, null, null);
            mMenuPageModel2Tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.novel_settings_oval), null, null, null);
        } else {
            mMenuPageModel1Tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.novel_settings_oval), null, null, null);
            mMenuPageModel2Tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.novel_settings_oval_choose), null, null, null);
        }
        if (nightModelFlag) {
            mReadModelTv.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.night_mode_choose), null, null);
        } else {
            mReadModelTv.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.night_mode), null, null);
        }
    }

    /**
     * 请求书籍信息
     */
    private void onRequestBookInfo() {
        ReadingBookRequest readingBookRequest = new ReadingBookRequest();
        readingBookRequest.token = mBuProcessor.getToken();
        readingBookRequest.book_id = mBookId;
        readingBookRequest.catalogue_id = String.valueOf(mCatalogueId);
        mPresenter.onRequestBookInfo(readingBookRequest);
    }

    protected String ContentStr = null;
    protected String FilePath = null;
    protected String FileName = null;
    private boolean isFirstLoadActivity = true;

    @Override
    public void getReadingBookInfoSuccess(ReadingBookResponse readingBookResponse) {
        mReadingBookResponse = readingBookResponse;
        String urlPath = readingBookResponse.getCatalogue().getNovel_url();
        FileName = readingBookResponse.getCatalogue().getBook_name();
        String localFilePath = DownloadUtil.checkFileIsExist(urlPath);
        if (TextUtils.isEmpty(localFilePath)) {
            if (TextUtils.isEmpty(DownloadUtil.checkFileIsExist(urlPath))) {
                downloadVideo(urlPath); //处理具体下载过程
            }
        } else {
            if (FileUtils.createOrExistsDir(DownloadUtil.PATH_CHALLENGE_VIDEO)) {
                int i = urlPath.lastIndexOf('/');//一定是找最后一个'/'出现的位置
                if (i != -1) {
                    FilePath = DownloadUtil.PATH_CHALLENGE_VIDEO + DownloadUtil.fileName + urlPath.substring(i);
                }
            }
            loadFile();
        }
        setIsRecharge();
    }

    private void setIsRecharge() {
        //是否免费 0 免费 1 收费1
        ReadingBookResponse.CatalogueBean catalogueBean = mReadingBookResponse.getCatalogue();
        if (catalogueBean.getType() != 0) {//收费
            if (mUser.vip == 0) {//非VIP
                if (mUser.bean >= catalogueBean.getCost()) {
                    onRequestReadRecording(catalogueBean.getCost());//消耗漫豆
                } else {
                    //进行弹框
                    showRechargeDialog();
                }
            } else {
                if (mUser.bean >= catalogueBean.getVip_cost()) {
                    onRequestReadRecording(catalogueBean.getVip_cost());
                } else {
                    //进行弹框
                    showRechargeDialog();
                }
            }
        } else {
            onRequestReadRecording(0);
        }
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
     * 阅读上报
     */
    private int useBean;

    public void onRequestReadRecording(int beans) {
        useBean = beans;
        ReadRecordingRequest readRecordingRequest = new ReadRecordingRequest();
        readRecordingRequest.token = mBuProcessor.getToken();
        readRecordingRequest.book_id = mBookId;
        readRecordingRequest.catalogue_id = String.valueOf(mCatalogueId);
        readRecordingRequest.type = String.valueOf(mReadingBookResponse.getCatalogue().getType() + 1);
        readRecordingRequest.bean = String.valueOf(beans);
        mPresenter.onRequestReadRecording(readRecordingRequest);
    }

    @Override
    public void getReadRecordingSuccess() {
        mUser.bean = mUser.bean - useBean;
        mBuProcessor.setLoginUser(mUser);
        mUser = mBuProcessor.getUser();
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ActivityConstant.UPDATE_PERSONAL_INFO));
    }

    /**
     * 请求书籍选集信息
     */
    private void onRequestSelectionInfo() {
        SelectionRequest selectionRequest = new SelectionRequest();
        selectionRequest.token = mBuProcessor.getToken();
        selectionRequest.book_id = mBookId;
        selectionRequest.orderby = "asc";
        selectionRequest.page = String.valueOf(1);
        selectionRequest.pagesize = "500";//
        mPresenter.onRequestSelectionInfo(selectionRequest);
    }


    @Override
    public void getSelectionInfoSuccess(SelectionResponse selectionResponse) {
        mSelectionResponse = selectionResponse;
        if (mChapterListPopupWindow == null) {
            mChapterListPopupWindow = new ChapterListPopupWindow(this, mReadingBookResponse, mSelectionResponse, mBuProcessor, mImageLoaderHelper, this);
        } else {
            mChapterListPopupWindow.setDismiss();
            mChapterListPopupWindow = null;
            mChapterListPopupWindow = new ChapterListPopupWindow(this, mReadingBookResponse, mSelectionResponse, mBuProcessor, mImageLoaderHelper, this);
        }
        mChapterListPopupWindow.initPopWindow(mReadBookLayout);
        mChapterListPopupWindow.setBackGroundColor(mTxtReaderView.getBackgroundColor());
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
     * 下载音频文件
     */
    private void downloadVideo(String urlPath) {
        DownloadUtil mDownloadUtil = new DownloadUtil();
        mDownloadUtil.downloadFile(urlPath, new DownloadListener() {
            @Override
            public void onStart() {
                LogUtils.e("onStart: ");
                runOnUiThread(() -> {
                    showLoading(getResources().getString(R.string.loading));
                });
            }

            @Override
            public void onProgress(final int currentLength) {
//                LogUtils.e("currentLength: " + currentLength);
            }

            @Override
            public void onFinish(String localPath) {
                LogUtils.e("onFinish ");
                //下载到本地视频路径 localPath
                runOnUiThread(() -> {
                    dismissLoading();
                    FilePath = localPath;
                    loadFile();
                });
            }

            @Override
            public void onFailure(final String erroInfo) {
                LogUtils.e("onFailure: " + erroInfo);
                runOnUiThread(() -> {
                    showToast("fail");
                    dismissLoading();
                });
            }
        });
    }


    protected String CurrentSelectedText;

    protected void init() {
        mHandler = new Handler();
    }

    private final int[] StyleTextColors = new int[]{
            Color.parseColor("#4a453a"),
            Color.parseColor("#505550"),
            Color.parseColor("#453e33"),
            Color.parseColor("#8f8e88"),
            Color.parseColor("#27576c")
    };


    protected void loadFile() {
        TxtConfig.savePageSwitchDuration(this, 400);
        if (ContentStr == null) {
            if (TextUtils.isEmpty(FilePath) || !(new File(FilePath).exists())) {
                showToast("file fail");
                return;
            }
        }
        mHandler.postDelayed(() -> {
            //延迟加载避免闪一下的情况出现
            if (ContentStr == null) {
                loadOurFile();
            }
        }, 300);
    }

    /**
     * 加载文件
     */
    protected void loadOurFile() {
        mTxtReaderView.loadTxtFile(FilePath, new ILoadListener() {
            @Override
            public void onSuccess() {
                if (!hasExisted) {
                    if (!isFirstLoadActivity) {
                        mTxtReaderView.loadFromProgress(0);//设置当前进度到最开始位置
                    } else {
                        isFirstLoadActivity = false;
                    }
                    onLoadDataSuccess();
                }
            }

            @Override
            public void onFail(final TxtMsg txtMsg) {
                if (!hasExisted) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onLoadDataFail(txtMsg);
                        }
                    });
                }
            }

            @Override
            public void onMessage(String message) {
                //加载过程信息
            }
        });
    }

    /**
     * @param txtMsg txtMsg
     */
    protected void onLoadDataFail(TxtMsg txtMsg) {
        //加载失败信息
        showToast(txtMsg + "");
    }

    /**
     *
     */
    protected void onLoadDataSuccess() {
        if (TextUtils.isEmpty(FileName)) {//没有显示的名称，获取文件名显示
            FileName = mTxtReaderView.getTxtReaderContext().getFileMsg().FileName;
        }
        setBookName(FileName);
        initWhenLoadDone();
    }


    protected void initWhenLoadDone() {
        if (mTxtReaderView.getTxtReaderContext().getFileMsg() != null) {
            FileName = mTxtReaderView.getTxtReaderContext().getFileMsg().FileName;
        }
        mMenuTextSize.setText(mTxtReaderView.getTextSize() + "");
        mTopDecoration.setBackgroundColor(mTxtReaderView.getBackgroundColor());
        mBottomDecoration.setBackgroundColor(mTxtReaderView.getBackgroundColor());
        //mTxtReaderView.setLeftSlider(new MuiLeftSlider());//修改左滑动条
        //mTxtReaderView.setRightSlider(new MuiRightSlider());//修改右滑动条
        //字体初始化
        onTextSettingUi(mTxtReaderView.getTxtReaderContext().getTxtConfig().Bold);
        //翻页初始化
        onPageSwitchSettingUi(mTxtReaderView.getTxtReaderContext().getTxtConfig().Page_Switch_Mode);
        //保存的翻页模式
        int pageSwitchMode = mTxtReaderView.getTxtReaderContext().getTxtConfig().Page_Switch_Mode;
        if (pageSwitchMode == TxtConfig.PAGE_SWITCH_MODE_SERIAL) {
            mTxtReaderView.setPageSwitchByTranslate();
        } else if (pageSwitchMode == TxtConfig.PAGE_SWITCH_MODE_COVER) {
            mTxtReaderView.setPageSwitchByCover();
        } else if (pageSwitchMode == TxtConfig.PAGE_SWITCH_MODE_SHEAR) {
            mTxtReaderView.setPageSwitchByShear();
        }
        //章节初始化
        if (mTxtReaderView.getChapters() != null && mTxtReaderView.getChapters().size() > 0) {
            WindowManager m = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics metrics = new DisplayMetrics();
            m.getDefaultDisplay().getMetrics(metrics);
            int ViewHeight = metrics.heightPixels - mTopDecoration.getHeight();
        } else {
            Gone(mChapterMenuText);
        }
    }


    protected void registerListener() {
        setMenuListener();
        setSeekBarListener();
        setCenterClickListener();
        setPageChangeListener();
        setOnTextSelectListener();
        setStyleChangeListener();
        setExtraListener();
    }

    private void setExtraListener() {
        mMenuTextSizeAdd.setOnClickListener(new TextChangeClickListener(true));
        mMenuTextSizeDel.setOnClickListener(new TextChangeClickListener(false));
        mMenuBoldSelectedLayout.setOnClickListener(new TextSettingClickListener(true));
        mMenuNormalSelectedLayout.setOnClickListener(new TextSettingClickListener(false));
        mMenuTranslateSelectedLayout.setOnClickListener(new SwitchSettingClickListener(TxtConfig.PAGE_SWITCH_MODE_SERIAL));
        mMenuCoverSelectedLayout.setOnClickListener(new SwitchSettingClickListener(TxtConfig.PAGE_SWITCH_MODE_COVER));
        mMenuShearSelectedLayout.setOnClickListener(new SwitchSettingClickListener(TxtConfig.PAGE_SWITCH_MODE_SHEAR));
    }

    protected void setStyleChangeListener() {
        mMenuStyle1.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor1), StyleTextColors[0]));
        mMenuStyle2.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor2), StyleTextColors[1]));
        mMenuStyle3.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor3), StyleTextColors[2]));
        mMenuStyle4.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor4), StyleTextColors[3]));
        mMenuStyle5.setOnClickListener(new StyleChangeClickListener(ContextCompat.getColor(this, R.color.hwtxtreader_styleclor5), StyleTextColors[4]));
    }

    protected void setOnTextSelectListener() {
        mTxtReaderView.setOnTextSelectListener(new ITextSelectListener() {
            @Override
            public void onTextChanging(TxtChar firstSelectedChar, TxtChar lastSelectedChar) {
                //firstSelectedChar.Top
                //  firstSelectedChar.Bottom
                // 这里可以根据 firstSelectedChar与lastSelectedChar的top与bottom的位置
                //计算显示你要显示的弹窗位置，如果需要的话
            }

            @Override
            public void onTextChanging(String selectText) {
                onCurrentSelectedText(selectText);
            }

            @Override
            public void onTextSelected(String selectText) {
                onCurrentSelectedText(selectText);
            }
        });

        mTxtReaderView.setOnSliderListener(new ISliderListener() {
            @Override
            public void onShowSlider(TxtChar txtChar) {
                //TxtChar 为当前长按选中的字符
                // 这里可以根据 txtChar的top与bottom的位置
                //计算显示你要显示的弹窗位置，如果需要的话
            }

            @Override
            public void onShowSlider(String currentSelectedText) {
                onCurrentSelectedText(currentSelectedText);
                Show(ClipboardView);
            }

            @Override
            public void onReleaseSlider() {
                Gone(ClipboardView);
            }
        });

    }

    protected void setPageChangeListener() {
        mTxtReaderView.setPageChangeListener(progress -> {
            int p = (int) (progress * 1000);
//            LogUtils.e("p:" + p / 10);
            mChapterSeekBar.setProgress(p / 10);
            mChapterNameText.setText(mReadingBookResponse.getCatalogue().getCatalogue_name());
        });
    }

    protected void setCenterClickListener() {
        mTxtReaderView.setOnCenterAreaClickListener(new ICenterAreaClickListener() {
            @Override
            public boolean onCenterClick(float widthPercentInView) {
                mSettingText.performClick();
                return true;
            }

            @Override
            public boolean onOutSideCenterClick(float widthPercentInView) {
                if (mBottomMenu.getVisibility() == View.VISIBLE) {
                    mSettingText.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    protected void setMenuListener() {
        mCoverView.setOnTouchListener((view, motionEvent) -> {
            Gone(mTopMenu, mBottomMenu, mCoverView, mChapterMsgView);
            return true;
        });
    }

    protected void setSeekBarListener() {
        //章节
        mChapterSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    mTxtReaderView.loadFromProgress(mChapterSeekBar.getProgress());
                    Gone(mChapterMsgView);
                }
                return false;
            }
        });
        //亮度
        mMenuSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {
                if (fromUser) {
                    mHandler.post(() -> checkPermissions(2, progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                Gone(mChapterMsgView);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.collection_iv, R.id.pre_chapter_tv, R.id.next_chapter_tv, R.id.directory_tv, R.id.read_model_tv, R.id.activity_hwtxtplay_setting_text,
            R.id.txtreadr_menu_brightness_system, R.id.txtreadr_menu_page_model1_tv, R.id.txtreadr_menu_page_model2_tv, R.id.share_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.directory_tv://目录
                if (mSelectionResponse == null) {
                    onRequestSelectionInfo();
                } else {
                    if (mChapterListPopupWindow == null) {
                        mChapterListPopupWindow = new ChapterListPopupWindow(this, mReadingBookResponse, mSelectionResponse, mBuProcessor, mImageLoaderHelper, this);
                    } else {
                        mChapterListPopupWindow.setDismiss();
                        mChapterListPopupWindow = null;
                        mChapterListPopupWindow = new ChapterListPopupWindow(this, mReadingBookResponse, mSelectionResponse, mBuProcessor, mImageLoaderHelper, this);
                    }
                    mChapterListPopupWindow.initPopWindow(mReadBookLayout);
                    mChapterListPopupWindow.setBackGroundColor(mTxtReaderView.getBackgroundColor());
                }
                break;
            case R.id.collection_iv://加入书架
                if (mLoginModel != 2) {
                    showToast(getString(R.string.please_login_hint));
                    startActivitys(LoginActivity.class);
                } else {
                    onAddBookShelfRequest();
                }
                break;
            case R.id.pre_chapter_tv://上一章
                if (mReadingBookResponse != null) {
                    mCatalogueId = mReadingBookResponse.getCatalogue().getPre_catalogue_id();
                    onRequestBookInfo();
                }
                break;
            case R.id.next_chapter_tv://下一章
                if (mReadingBookResponse != null) {
                    mCatalogueId = mReadingBookResponse.getCatalogue().getNext_catalogue_id();
                    onRequestBookInfo();
                }
                break;
            case R.id.read_model_tv:
                boolean nightModelFlag = mSharePreferenceUtil.getBooleanData(Constant.IS_NIGHT_MODEL, false);//夜间模式
                mSharePreferenceUtil.setData(Constant.IS_NIGHT_MODEL, !nightModelFlag);
                if (nightModelFlag) {
                    mReadModelTv.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.night_mode), null, null);
                    //设置白天模式
                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 110);//屏幕亮度值范围必须位于：0～255
                } else {
                    mReadModelTv.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.night_mode_choose), null, null);
                    //设置夜间模式
                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 25);//屏幕亮度值范围必须位于：0～255
                }
                break;
            case R.id.activity_hwtxtplay_setting_text://设置
                Show(mTopMenu, mBottomMenu, mCoverView);
                break;
            case R.id.txtreadr_menu_brightness_system://亮度跟随系统
                checkPermissions(1, 0);
                break;
            case R.id.txtreadr_menu_page_model1_tv://阅读页面模式  左右翻页
                mSharePreferenceUtil.setData(Constant.READ_PAGE_MODEL, 0);
                mMenuPageModel1Tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.novel_settings_oval_choose), null, null, null);
                mMenuPageModel2Tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.novel_settings_oval), null, null, null);
                break;
            case R.id.txtreadr_menu_page_model2_tv:///阅读页面模式  上下翻页
                mSharePreferenceUtil.setData(Constant.READ_PAGE_MODEL, 1);
                mMenuPageModel1Tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.novel_settings_oval), null, null, null);
                mMenuPageModel2Tv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.novel_settings_oval_choose), null, null, null);
                break;
            case R.id.share_iv:
                new SharePopupWindow(this, this).initPopWindow(mReadBookLayout);
                break;

        }
    }

    /**
     * 检查app 权限
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("CheckResult")
    private void checkPermissions(int flag, int progress) {
        //设置系统亮度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                //有了权限，具体的动作
                if (flag == 1) {
                    boolean lingSystem = mSharePreferenceUtil.getBooleanData(Constant.LING_SYSTEM, false);
                    if (lingSystem) {
                        mSharePreferenceUtil.setData(Constant.LING_SYSTEM, false);
                        mMenuBrightnessSystem.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.novel_settings_oval), null, null, null);
                    } else {
                        mSharePreferenceUtil.setData(Constant.LING_SYSTEM, true);
                        mMenuBrightnessSystem.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.novel_settings_oval_choose), null, null, null);
                        LogUtils.e("ScreenBrightness:" + BrightnessTools.getScreenBrightness(this));
                        mMenuSeekBar.setProgress(BrightnessTools.getScreenBrightness(this));
                    }
                    BrightnessTools.setBrightness(this, BrightnessTools.getScreenBrightness(this));
                } else if (flag == 2) {
                    LogUtils.e("progress:" + progress);
                    BrightnessTools.setBrightness(ReadBookActivity.this, progress);
                    mSharePreferenceUtil.setData(Constant.SET_LING, progress);
                    mSharePreferenceUtil.setData(Constant.LING_SYSTEM, false);
                    mMenuBrightnessSystem.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.novel_settings_oval), null, null, null);
                }
            }
        }
    }


    private void onCurrentSelectedText(String SelectedText) {
        mSelectedText.setText("select" + (SelectedText + "").length() + "words");
        CurrentSelectedText = SelectedText;
    }

    private void onTextSettingUi(Boolean isBold) {
        if (isBold) {
            mMenuBoldSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_selected);
            mMenuNormalSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_unselected);
        } else {
            mMenuBoldSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_unselected);
            mMenuNormalSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_selected);
        }
    }

    private void onPageSwitchSettingUi(int pageSwitchMode) {
        if (pageSwitchMode == TxtConfig.PAGE_SWITCH_MODE_SERIAL) {
            mMenuTranslateSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_selected);
            mMenuCoverSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_unselected);
            mMenuShearSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_unselected);
        } else if (pageSwitchMode == TxtConfig.PAGE_SWITCH_MODE_COVER) {
            mMenuTranslateSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_unselected);
            mMenuCoverSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_selected);
            mMenuShearSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_unselected);
        } else if (pageSwitchMode == TxtConfig.PAGE_SWITCH_MODE_SHEAR) {
            mMenuTranslateSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_unselected);
            mMenuCoverSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_unselected);
            mMenuShearSelectedLayout.setBackgroundResource(R.drawable.shape_menu_textsetting_selected);
        }
    }

    @Override
    public void switchChapterPage(int chapterId) {
        LogUtils.e("chapterId:" + chapterId);
        mCatalogueId = chapterId;
        onRequestBookInfo();
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
     * 上传分享成功
     */
    private void onShareTaskRequest() {
        ShareTaskRequest shareTaskRequest = new ShareTaskRequest();
        shareTaskRequest.token = mBuProcessor.getToken();
        mPresenter.onRequestShareTask(shareTaskRequest);
    }

    @Override
    public void addBookShelf() {
        onAddBookShelfRequest();
    }

    @Override
    public void exitReading() {
        finish();
    }

    private class TextSettingClickListener implements View.OnClickListener {
        private Boolean Bold;

        TextSettingClickListener(Boolean bold) {
            Bold = bold;
        }

        @Override
        public void onClick(View view) {
            mTxtReaderView.setTextBold(Bold);
            onTextSettingUi(Bold);
        }
    }

    private class SwitchSettingClickListener implements View.OnClickListener {
        private int pageSwitchMode;

        SwitchSettingClickListener(int pageSwitchMode) {
            this.pageSwitchMode = pageSwitchMode;
        }

        @Override
        public void onClick(View view) {
            if (pageSwitchMode == TxtConfig.PAGE_SWITCH_MODE_COVER) {
                mTxtReaderView.setPageSwitchByCover();
            } else if (pageSwitchMode == TxtConfig.PAGE_SWITCH_MODE_SERIAL) {
                mTxtReaderView.setPageSwitchByTranslate();
            }
            if (pageSwitchMode == TxtConfig.PAGE_SWITCH_MODE_SHEAR) {
                mTxtReaderView.setPageSwitchByShear();
            }
            onPageSwitchSettingUi(pageSwitchMode);
        }
    }


    private class TextChangeClickListener implements View.OnClickListener {
        private Boolean Add;

        TextChangeClickListener(Boolean pre) {
            Add = pre;
        }

        @Override
        public void onClick(View view) {
            int textSize = mTxtReaderView.getTextSize();
            if (Add) {
                if (textSize + 2 <= TxtConfig.MAX_TEXT_SIZE) {
                    mTxtReaderView.setTextSize(textSize + 2);
                    mMenuTextSize.setText(textSize + 2 + "");
                }
            } else {
                if (textSize - 2 >= TxtConfig.MIN_TEXT_SIZE) {
                    mTxtReaderView.setTextSize(textSize - 2);
                    mMenuTextSize.setText(textSize - 2 + "");
                }
            }
        }
    }

    private class StyleChangeClickListener implements View.OnClickListener {
        private int BgColor;
        private int TextColor;

        StyleChangeClickListener(int bgColor, int textColor) {
            BgColor = bgColor;
            TextColor = textColor;
        }

        @Override
        public void onClick(View view) {
            mTxtReaderView.setStyle(BgColor, TextColor);
            mTopDecoration.setBackgroundColor(BgColor);
            mBottomDecoration.setBackgroundColor(BgColor);
        }
    }

    protected void setBookName(String name) {
        mMenuTitle.setText(name);
    }

    protected void Show(View... views) {
        for (View v : views) {
            v.setVisibility(View.VISIBLE);
        }
    }

    protected void Gone(View... views) {
        for (View v : views) {
            v.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        exist();
    }

    public void BackClick(View view) {
        onBackPressed();
    }

    public void onCopyText(View view) {
        if (!TextUtils.isEmpty(CurrentSelectedText)) {
            showToast("已经复制到粘贴板");
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            cm.setText(CurrentSelectedText + "");
        }
        onCurrentSelectedText("");
        mTxtReaderView.releaseSelectedState();
        Gone(ClipboardView);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (mReadingBookResponse.getCatalogue().getState() == 1) {
            finish();
        } else {
            new ExitReadingBookPopupWindow(this, this).initPopWindow(mReadBookLayout);
        }
    }

    @Override
    public void finish() {
        super.finish();
        exist();
    }

    protected boolean hasExisted = false;

    protected void exist() {
        if (!hasExisted) {
            ContentStr = null;
            hasExisted = true;
            if (mTxtReaderView != null) {
                mTxtReaderView.saveCurrentProgress();
            }
            mHandler.postDelayed(() -> {
                if (mTxtReaderView != null) {
                    mTxtReaderView.getTxtReaderContext().Clear();
                    mTxtReaderView = null;
                }
                if (mHandler != null) {
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler = null;
                }
                if (mChapterListPopupWindow != null) {
                    mChapterListPopupWindow.setDismiss();
                    mChapterListPopupWindow = null;
                }
            }, 300);

        }
    }

    private void initInjectData() {
        DaggerReadBookComponent.builder().appComponent(getAppComponent())
                .readBookModule(new ReadBookModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }

}
