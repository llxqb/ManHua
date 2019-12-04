package com.shushan.manhua.mvp.ui.activity.book;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.shushan.manhua.R;
import com.shushan.manhua.di.components.DaggerReadComponent;
import com.shushan.manhua.di.modules.ActivityModule;
import com.shushan.manhua.di.modules.ReadModule;
import com.shushan.manhua.entity.BannerBean;
import com.shushan.manhua.entity.CommentBean;
import com.shushan.manhua.entity.RecommendBean;
import com.shushan.manhua.entity.constants.ActivityConstant;
import com.shushan.manhua.entity.request.AddBookShelfRequest;
import com.shushan.manhua.entity.request.ExchangeBarrageStyleRequest;
import com.shushan.manhua.entity.request.ReadRecordingRequest;
import com.shushan.manhua.entity.request.ReadingRequest;
import com.shushan.manhua.entity.request.SendBarrageRequest;
import com.shushan.manhua.entity.request.SupportRequest;
import com.shushan.manhua.entity.response.BarrageListResponse;
import com.shushan.manhua.entity.response.BarrageStyleResponse;
import com.shushan.manhua.entity.response.BuyBarrageStyleResponse;
import com.shushan.manhua.entity.response.ReadingInfoResponse;
import com.shushan.manhua.entity.response.SelectionResponse;
import com.shushan.manhua.entity.user.User;
import com.shushan.manhua.help.DialogFactory;
import com.shushan.manhua.listener.MyUMShareListener;
import com.shushan.manhua.listener.SoftKeyBoardListener;
import com.shushan.manhua.mvp.ui.activity.mine.BuyActivity;
import com.shushan.manhua.mvp.ui.adapter.BannerReadingViewHolder;
import com.shushan.manhua.mvp.ui.adapter.ReadingCommentAdapter;
import com.shushan.manhua.mvp.ui.adapter.ReadingPicAdapter;
import com.shushan.manhua.mvp.ui.adapter.RecommendAdapter;
import com.shushan.manhua.mvp.ui.base.BaseActivity;
import com.shushan.manhua.mvp.ui.dialog.AddBarrageDialog;
import com.shushan.manhua.mvp.ui.dialog.BarrageSoftKeyPopupWindow;
import com.shushan.manhua.mvp.ui.dialog.BarrageStylePopupWindow;
import com.shushan.manhua.mvp.ui.dialog.CommentSoftKeyPopupWindow;
import com.shushan.manhua.mvp.ui.dialog.ReadBeansExchangeDialog;
import com.shushan.manhua.mvp.ui.dialog.ReadContentsPopupWindow;
import com.shushan.manhua.mvp.ui.dialog.ReadOpenVipDialog;
import com.shushan.manhua.mvp.ui.dialog.ReadSettingPopupWindow;
import com.shushan.manhua.mvp.ui.dialog.ReadUseCoinDialog;
import com.shushan.manhua.mvp.ui.dialog.SharePopupWindow;
import com.shushan.manhua.mvp.utils.LogUtils;
import com.shushan.manhua.mvp.utils.SoftKeyboardUtil;
import com.shushan.manhua.mvp.utils.SystemUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 阅读页面父类
 */
public abstract class ReadBaseActivity extends BaseActivity implements ReadControl.ReadView, ReadUseCoinDialog.ReadUseCoinDialogListener, ReadBeansExchangeDialog.ReadBeansExchangeDialogListener,
        ReadOpenVipDialog.ReadOpenVipDialogListener, ReadSettingPopupWindow.ReadSettingPopupWindowListener, BarrageStylePopupWindow.BarrageStylePopupWindowListener,
        BarrageSoftKeyPopupWindow.BarrageSoftKeyPopupWindowListener, CommentSoftKeyPopupWindow.CommentSoftKeyPopupWindowListener, TakePhoto.TakeResultListener,
        InvokeListener, AddBarrageDialog.AddBarrageDialogListener,SharePopupWindow.PopupWindowShareListener {
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
    @BindView(R.id.banner)
    MZBannerView mBanner;
    @BindView(R.id.barrage_iv)
    ImageView mBarrageIv;//弹幕开关
    @BindView(R.id.send_message_ll)
    LinearLayout mSendMessageLl;
    @BindView(R.id.send_message_left_iv)
    ImageView mSendMessageLeftIv;
    @BindView(R.id.send_message_right_iv)
    ImageView mSendMessageRightIv;
    @BindView(R.id.message_et)
    TextView mMessageEt;
    @BindView(R.id.send_tv)
    TextView mSendTv;
    @BindView(R.id.comment_num_tv)
    TextView mCommentNumTv;
    @BindView(R.id.pic_recycler_view)
    RecyclerView mPicRecyclerView;
    @BindView(R.id.support_tv)
    TextView mSupportTv;
    @BindView(R.id.add_bookshelf_iv)
    ImageView mAddBookshelfIv;
    @BindView(R.id.back_top_iv)
    ImageView mBackTopIv;
    @BindView(R.id.read_bottom_ll)
    LinearLayout mReadBottomLl;//底部布局
    @BindView(R.id.barrage_ll)
    LinearLayout mBarrageLl;//弹幕布局
    @BindView(R.id.recommend_recycler_view)
    RecyclerView mRecommendRecyclerView;
    @BindView(R.id.comment_recycler_view)
    RecyclerView mCommentRecyclerView;
    boolean mBarrage;//是否弹幕
    List<BannerBean> bannerList = new ArrayList<>();
    private List<RecommendBean> readingRecommendResponseList = new ArrayList<>();//推荐list
    private List<CommentBean> readingCommendResponseList = new ArrayList<>();//评论
    private List<BarrageStyleResponse> barrageStyleResponseList = new ArrayList<>();//弹幕样式list
    private List<String> bookPicList = new ArrayList<>();//漫画章节图片
    private Integer[] barrageStyleIcon = {R.mipmap.barrage0, R.mipmap.barrage1, R.mipmap.barrage2, R.mipmap.barrage3, R.mipmap.barrage4, R.mipmap.barrage5};
    private Integer[] barrageStyleType = {0, 1, 1, 2, 1, 2};
    //是否是弹幕状态否则是评论状态 false
    private boolean isBarrageState = true;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Uri uri;
    //成功取得照片
    Bitmap bitmap;
    //选择照片的路径集合
    private ArrayList<TImage> photoList = new ArrayList<>();
    private CommentSoftKeyPopupWindow mCommentSoftKeyPopupWindow;//评论弹幕
    /**
     * 当前选择的弹幕样式
     */
    private int mBarrageStyle = 0;
    public String mBookId;
    public int mCatalogueId;// 当前章节id
    public String mBookCover;//书籍封面
    public ReadingPicAdapter mReadingPicAdapter;//章节图片adapter
    public ReadingCommentAdapter mReadingCommentAdapter;//评价adapter
    public RecommendAdapter mRecommendAdapter;//推荐adapter
    public ReadingInfoResponse mReadingInfoResponse;
    public SelectionResponse mSelectionResponse;
    public int page = 1;
    public int picRvHeight;//图片recyclerView总高度
    public int mCurrentHeight = 0;//当前高度
    public User mUser;
    public BarrageListResponse mBarrageListResponse;//弹幕集合
    public BuyBarrageStyleResponse mBuyBarrageStyleResponse;//用户购买弹幕样式列表
    private ReadUseCoinDialog mReadUseCoinDialog;//非免费章节 弹出购买弹框

    public static void start(Context context, String bookId, int catalogueId, String bookCover) {
        Intent intent = new Intent(context, ReadActivity.class);
        intent.putExtra("bookId", bookId);
        intent.putExtra("catalogueId", catalogueId);
        intent.putExtra("bookCover", bookCover);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        File file = new File(getExternalCacheDir(), System.currentTimeMillis() + ".png");
        uri = Uri.fromFile(file);
    }

    @Override
    public void onReceivePro(Context context, Intent intent) {
        if (intent.getAction() != null) {
            if (intent.getAction().equals(ActivityConstant.PAY_SUCCESS)) {//购买成功更新数据
                mUser = mBuProcessor.getUser();
                if (mReadUseCoinDialog != null) {
                    mReadUseCoinDialog.closeDialog();
                }
            }
        }
        super.onReceivePro(context, intent);
    }

    @Override
    public void addFilter() {
        super.addFilter();
        mFilter.addAction(ActivityConstant.PAY_SUCCESS);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_read);
        setStatusBar();
        initInjectData();
        mUser = mBuProcessor.getUser();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView() {
        if (getIntent() != null) {
            mBookId = getIntent().getStringExtra("bookId");
            mCatalogueId = getIntent().getIntExtra("catalogueId", 1);
            mBookCover = getIntent().getStringExtra("bookCover");
        }
        mMessageEt.clearFocus();//让编辑框失去焦点 配合布局一起使用
        mBarrage = mSharePreferenceUtil.getBooleanData("barrage");
        if (mBarrage) {
            mBarrageIv.setImageResource(R.mipmap.barrage_open);
        } else {
            mBarrageIv.setImageResource(R.mipmap.barrage_close);
        }
        onKeyBoardListener();
        initAdapter();
        initScrollView();
        onRequestReadingInfo();
    }

    private void initAdapter() {
        mRecommendAdapter = new RecommendAdapter(readingRecommendResponseList, mImageLoaderHelper);
        mRecommendRecyclerView.setAdapter(mRecommendAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecommendRecyclerView.setLayoutManager(linearLayoutManager);
        mReadingCommentAdapter = new ReadingCommentAdapter(readingCommendResponseList, mImageLoaderHelper);
        mCommentRecyclerView.setAdapter(mReadingCommentAdapter);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mReadingCommentAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CommentBean commentBean = (CommentBean) adapter.getItem(position);
            switch (view.getId()) {
                case R.id.comment_ll:
                    CommentDetailsActivity.start(this, String.valueOf(commentBean.getComment_id()));
                    break;
            }
        });
        //图片adapter
        mReadingPicAdapter = new ReadingPicAdapter(bookPicList, mImageLoaderHelper);
        mPicRecyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
                super.onMeasure(recycler, state, widthSpec, heightSpec);
                picRvHeight = mPicRecyclerView.getHeight();
//                LogUtils.e("picRvHeight:" + picRvHeight);
            }
        });
        mPicRecyclerView.setAdapter(mReadingPicAdapter);

        mReadingPicAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            SoftKeyboardUtil.hideSoftKeyboard(ReadBaseActivity.this);
            showFunction();
        });
    }


    float yDown = 0;
    float yUp = 0;

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        //mViewWidth 是整个屏幕的宽度
//        int mViewWidth = SystemUtils.getScreenWidth(ReadBaseActivity.this);
//        int mViewHeight = SystemUtils.getScreenHeight(ReadBaseActivity.this);
//        //就是在屏幕的一半+100和-100之间的宽度 同理高度
//        boolean isCenterOfX = event.getX() < mViewWidth / 2 + 150
//                && event.getX() > mViewWidth / 2 - 150;
//        boolean isCenterOfY = event.getY() < mViewHeight / 2 + 150
//                && event.getY() > mViewHeight / 2 - 150;
//        boolean top = event.getY() < mViewHeight / 2 - 150;
//        boolean bottom = event.getY() > mViewHeight / 2 + 150;
//        boolean center = isCenterOfX && isCenterOfY;
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            yDown = event.getY();
//        } else if (event.getAction() == MotionEvent.ACTION_UP) {
//            yUp = event.getY();
//            //判断是点击还是滑动
//            if ((yUp - yDown) <= 20 && (yUp - yDown) >= -20) { //点击
//                //如果点击的位置是在这个方形之间
//                //必须要点击之后手指离开才进行监听
//                if (top) {
//                    SoftKeyboardUtil.hideSoftKeyboard(ReadBaseActivity.this);
//                    hideFunction();
//                    upPageBtn();
//                } else if (center) {
//                    SoftKeyboardUtil.hideSoftKeyboard(ReadBaseActivity.this);
//                    showFunction();
//                } else if (bottom) {
//                    SoftKeyboardUtil.hideSoftKeyboard(ReadBaseActivity.this);
//                    hideFunction();
//                    downPageBtn();
//                }
//                return true;
//            }
//        }
//
////        this.getParent().requestDisallowInterceptTouchEvent(true);
//        return super.dispatchTouchEvent(event);
////        return super.onTouchEvent(event);
//    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initScrollView() {
        mNestedScrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
//            LogUtils.e("scrollY:" + scrollY + " oldScrollY:" + oldScrollY);
            if (scrollY < oldScrollY) {//往上滑
                mBackTopIv.setVisibility(View.VISIBLE);
            } else {
                mBackTopIv.setVisibility(View.INVISIBLE);
            }
            mCurrentHeight = scrollY;
//            LogUtils.e("mCurrentHeight:" + mCurrentHeight);
//            isShowBackTopIv = scrollY > mPicRecyclerView.getHeight() * 4 / 5;//大于图片的4/5 显示返回顶部按钮   往上滑显示返回顶部按钮
            //mNestedScrollView.getChildAt(0).getMeasuredHeight()- mNestedScrollView.getMeasuredHeight()
            if (scrollY >= mPicRecyclerView.getHeight()) {//设置隐藏功能键
                isBarrageState = false;
                if (mReadBottomLl.getVisibility() == View.VISIBLE) {
                    showFunction();
                }
            } else {
                isBarrageState = true;
            }

            //显示弹幕
            for (BarrageListResponse.DataBean dataBean : mBarrageListResponse.getData()) {
                if (Double.parseDouble(dataBean.getYcoord()) < (mCurrentHeight + 20) && Double.parseDouble(dataBean.getYcoord()) > mCurrentHeight) {//显示出来
                    addTvView(dataBean);
                }
            }
        });
    }

    /**
     * 增加弹幕view
     */
    public void addTvView(BarrageListResponse.DataBean dataBean) {
        int screenWidth = SystemUtils.getScreenWidth(this);
        int screenHeight = SystemUtils.getScreenHeight(this);
        Toast toast2 = new Toast(this);
        View view = LayoutInflater.from(this).inflate(R.layout.text_view, null);
        TextView textTv = view.findViewById(R.id.text_tv);

        switch (dataBean.getStyle_id()) {
            case 0:
                textTv.setBackgroundResource(R.mipmap.black_bg);
                break;
            case 1:
                textTv.setBackgroundResource(R.mipmap.barrage1_bg);
                break;
            case 2:
                textTv.setBackgroundResource(R.mipmap.barrage2_bg);
                break;
            case 3:
                textTv.setBackgroundResource(R.mipmap.barrage3_bg);
                break;
            case 4:
                textTv.setBackgroundResource(R.mipmap.barrage4_bg);
                break;
            case 5:
                textTv.setBackgroundResource(R.mipmap.barrage5_bg);
                break;
        }
        textTv.setText(dataBean.getBarrage_content());
        toast2.setView(view);
        toast2.setGravity(Gravity.CENTER, (int) Double.parseDouble(dataBean.getXcoord()) - screenWidth / 2, screenHeight / 2 - (int) Double.parseDouble(dataBean.getYcoord()));
        toast2.show();
    }

    @Override
    public void initData() {
        mCommonRightTv.setVisibility(View.VISIBLE);
        mCommonRightTv.setText(getResources().getString(R.string.ReadActivity_right_title));
        for (int i = 0; i < barrageStyleIcon.length; i++) {
            BarrageStyleResponse barrageStyleResponse = new BarrageStyleResponse();
            barrageStyleResponse.isCheck = i == 0;//默认选择第一项
            barrageStyleResponse.styleIcon = barrageStyleIcon[i];
            barrageStyleResponse.styleType = barrageStyleType[i];
            barrageStyleResponseList.add(barrageStyleResponse);
        }
    }


    //监听软件盘是否弹起
    private void onKeyBoardListener() {
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
//                LogUtils.e("onKeyBoardListener()");
                hideLayout();
                if (barrageSoftKeyPopupWindow == null) {
                    if (isBarrageState) {//弹幕状态
                        showBarragePopupWindow();
                    } else {//评论状态
                        showCommentPopupWindow(getString(R.string.BarrageStylePopupWindow_comment_hint));
                    }
                }
            }

            @Override
            public void keyBoardHide(int height) {//键盘隐藏 高度
                showLayout();
            }
        });
    }

    /**
     * 章节详情
     */
    private void onRequestReadingInfo() {
        ReadingRequest readingRequest = new ReadingRequest();
        readingRequest.token = mBuProcessor.getToken();
        readingRequest.book_id = mBookId;
        readingRequest.catalogue_id = String.valueOf(mCatalogueId);
        mPresenter.onRequestReadingInfo(readingRequest);
    }

    /**
     * 显示popupWindow时 隐藏的布局
     */
    private void hideLayout() {
        mSendMessageLl.setVisibility(View.GONE);
        mReadBottomLl.setVisibility(View.GONE);
        mBarrageLl.setVisibility(View.GONE);
        mBackTopIv.setVisibility(View.GONE);
        mAddBookshelfIv.setVisibility(View.GONE);
    }

    /**
     * 显示popupWindow时 显示的布局
     */
    private void showLayout() {
        mSendMessageLl.setVisibility(View.GONE);
        mReadBottomLl.setVisibility(View.GONE);
        mBarrageLl.setVisibility(View.GONE);
        mBackTopIv.setVisibility(View.GONE);
        mAddBookshelfIv.setVisibility(View.GONE);
    }

    BarrageSoftKeyPopupWindow barrageSoftKeyPopupWindow;

    /**
     * 显示弹幕弹框PopupWindow
     */
    private void showBarragePopupWindow() {
        if (barrageSoftKeyPopupWindow == null) {
            barrageSoftKeyPopupWindow = new BarrageSoftKeyPopupWindow(this, this);
        }
        barrageSoftKeyPopupWindow.initPopWindow(mReadLayout);
    }

    /**
     * 显示评论弹框PopupWindow
     */
    private void showCommentPopupWindow(String editHintContent) {
        if (mCommentSoftKeyPopupWindow == null) {
            mCommentSoftKeyPopupWindow = new CommentSoftKeyPopupWindow(this, this, photoList, editHintContent);
        }
        mCommentSoftKeyPopupWindow.initPopWindow(mReadLayout);
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
     * 显示开通会员弹框
     */
    private void showOpenVipDialog() {
        ReadOpenVipDialog readOpenVipDialog = ReadOpenVipDialog.newInstance();
        readOpenVipDialog.setListener(this);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), readOpenVipDialog, ReadOpenVipDialog.TAG);
    }

    /**
     * 显示漫豆兑换弹幕弹框
     */
    private void showBeansExchangeDialog() {
        ReadBeansExchangeDialog readBeansExchangeDialog = ReadBeansExchangeDialog.newInstance();
        readBeansExchangeDialog.setListener(this);
        readBeansExchangeDialog.setDate(mUser.bean);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), readBeansExchangeDialog, ReadBeansExchangeDialog.TAG);
    }


    @OnClick({R.id.common_left_iv, R.id.common_right_tv, R.id.bottom_directory_ll, R.id.last_chapter_iv, R.id.next_chapter_iv, R.id.barrage_ll, R.id.send_message_left_iv, R.id.send_message_right_iv,
            R.id.support_tv, R.id.add_bookshelf_tv, R.id.share_tv, R.id.last_chapter_ll, R.id.next_chapter_ll, R.id.bottom_comment_ll, R.id.bottom_share_ll, R.id.bottom_setting_ll,
            R.id.back_top_iv, R.id.add_bookshelf_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.common_left_iv:
                finish();
                break;
            case R.id.common_right_tv: //全集    跳到详情
                BookDetailActivity.start(this, mBookId, mBookCover);
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
                switchFunction();
                break;
            case R.id.send_message_right_iv://打开选择弹幕样式  隐藏输入法 打开样式选择
                showBarrageStyle();
                break;
            case R.id.support_tv://点赞
                onRequestSupport();
                break;
            case R.id.add_bookshelf_tv://加入书架
                if (mReadingInfoResponse.getCatalogue().getState() == 1) {
                    showToast("telah masuk rak buku");//已加入书架
                } else {
                    onAddBookShelfRequest();
                }
                break;
            case R.id.share_tv://分享
                break;
            case R.id.last_chapter_ll://上一篇
                if (mCatalogueId > 1) {
                    mCatalogueId = mCatalogueId - 1;
                    onRequestReadingInfo();
                }
                break;
            case R.id.next_chapter_ll://下一篇
                mCatalogueId = mCatalogueId + 1;
                onRequestReadingInfo();
                break;
            case R.id.last_chapter_iv: //上一话
                if (mCatalogueId > 1) {
                    mCatalogueId = mCatalogueId - 1;
                    onRequestReadingInfo();
                }
                break;
            case R.id.next_chapter_iv: //下一话
                mCatalogueId = mCatalogueId + 1;
                onRequestReadingInfo();
                break;
            case R.id.bottom_directory_ll: //目录
                if (mSelectionResponse != null) {
                    new ReadContentsPopupWindow(this, mSelectionResponse, mImageLoaderHelper).initPopWindow(mReadLayout);
                }
                break;
            case R.id.bottom_comment_ll://评论
                MoreCommentActivity.start(this, mBookId);
                break;
            case R.id.bottom_share_ll://分享
                new SharePopupWindow(this, this).initPopWindow(mReadLayout);
                break;
            case R.id.bottom_setting_ll://设置
                new ReadSettingPopupWindow(this, this, mSharePreferenceUtil).initPopWindow(mReadLayout);
                break;
            case R.id.back_top_iv:// 让页面返回顶部
                mNestedScrollView.post(() -> mNestedScrollView.post(new Runnable() {
                    public void run() {
                        // 滚动至顶部
                        mNestedScrollView.fullScroll(ScrollView.FOCUS_UP);
                        // 滚动到底部
                        //sc.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                }));
                break;
            case R.id.add_bookshelf_iv://加入书架
                if (mReadingInfoResponse.getCatalogue().getState() == 1) {
                    showToast("telah masuk rak buku");//已加入书架
                } else {
                    onAddBookShelfRequest();
                }
                break;
        }
    }


    /**
     * 点赞
     */
    private void onRequestSupport() {
        SupportRequest commentSuggestRequest = new SupportRequest();
        commentSuggestRequest.token = mBuProcessor.getToken();
        commentSuggestRequest.relation_id = String.valueOf(mCatalogueId);
        commentSuggestRequest.type = "2";
        mPresenter.onSupportRequest(commentSuggestRequest);
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


    /**
     * 去充值弹框  去充值
     */
    @Override
    public void readUseCoinDialogBtnOkListener() {
        //这里 只会跳到购买
        //去购买漫豆
        startActivitys(BuyActivity.class);
//        if (mUser.vip == 1) {
//            if (mUser.bean >= 3) {
//                //去使用
//                onRequestReadRecording(3);
//            } else {
//                //去购买漫豆
//                startActivitys(BuyActivity.class);
//            }
//        } else {
//            if (mUser.bean >= 5) {
//                //去使用
//                onRequestReadRecording(5);
//            } else {
//                //去购买漫豆
//                startActivitys(BuyActivity.class);
//            }
//        }
    }

    /**
     * 漫豆兑换弹幕弹框  去兑换
     * 1 去冲漫豆  2 去兑换
     */
    @Override
    public void readBeansExchangeDialogBtnOkListener(int type) {
        if (type == 1) {
            startActivitys(BuyActivity.class);
        } else {
            //兑换
            exchangeBarrageStyleRequest();
        }
    }

    /**
     * 兑换弹幕样式
     */
    private void exchangeBarrageStyleRequest() {
        ExchangeBarrageStyleRequest request = new ExchangeBarrageStyleRequest();
        request.token = mBuProcessor.getToken();
        request.style_id = String.valueOf(mBarrageStyle);
        mPresenter.exchangeBarrageStyleRequest(request);
    }

    /**
     * 上传阅读记录  和  使用漫豆  购买阅读非免费章节
     */
    public void onRequestReadRecording(int beans) {
        ReadRecordingRequest readRecordingRequest = new ReadRecordingRequest();
        readRecordingRequest.token = mBuProcessor.getToken();
        readRecordingRequest.book_id = mBookId;
        readRecordingRequest.catalogue_id = String.valueOf(mCatalogueId);
        readRecordingRequest.type = String.valueOf(mReadingInfoResponse.getCatalogue().getType() + 1);
        readRecordingRequest.bean = String.valueOf(beans);
        mPresenter.onRequestReadRecording(readRecordingRequest);
    }

    /**
     * 显示开通会员弹框  开通会员
     */
    @Override
    public void readOpenVipDialogBtnOkListener() {

    }

    /**
     * 屏幕向上点击
     */
    private void upPageBtn() {
        mCurrentHeight = mCurrentHeight - SystemUtils.getScreenHeight(this) * 3 / 4;
        LogUtils.e("mCurrentHeight:" + mCurrentHeight + " picRvHeight:" + picRvHeight);
        if (mCurrentHeight < picRvHeight && mCurrentHeight > 0) {
            mNestedScrollView.smoothScrollTo(0, mCurrentHeight);
        }
    }

    /**
     * 屏幕向下点击
     */
    private void downPageBtn() {
        mCurrentHeight = mCurrentHeight + SystemUtils.getScreenHeight(this) * 3 / 4;
//        LogUtils.e("mCurrentHeight:" + mCurrentHeight + " picRvHeight:" + picRvHeight);
        if (mCurrentHeight < picRvHeight) {
            mNestedScrollView.smoothScrollTo(0, mCurrentHeight);
        }
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
        startActivitys(BarrageSettingActivity.class);
    }


    /**
     * 弹幕模式：显示自定义软键盘弹幕布局
     * 切换弹幕或者评论
     */
    @Override
    public void switchStyleLayoutBtnListenerByBarrageSoftKey() {
        switchFunction();
        //显示评论功能
        showCommentPopupWindow(getString(R.string.BarrageStylePopupWindow_comment_hint));
    }

    /**
     * 弹幕模式：显示弹幕样式
     */
    @Override
    public void showStyleBtnListenerByBarrageSoftKey() {
        showBarrageStyle();
    }

    /**
     * 弹幕模式：发送消息
     */
    @Override
    public void sendMessageBtnListenerByBarrageSoftKey(String message) {
        showAddBarrageDialog(message);
    }

    /**
     * 弹幕模式：popupWindow窗口关闭
     */
    @Override
    public void dismissBtnListenerByBarrageSoftKey() {
        barrageSoftKeyPopupWindow = null;
    }

    /**
     * 显示发送弹幕弹框
     * 记录当前X Y轴坐标
     */
    private void showAddBarrageDialog(String message) {
        AddBarrageDialog addBarrageDialog = AddBarrageDialog.newInstance();
        addBarrageDialog.setListener(this);
        addBarrageDialog.setMessage(message);
        addBarrageDialog.setBarrageStyle(mBarrageStyle);
        DialogFactory.showDialogFragment(getSupportFragmentManager(), addBarrageDialog, AddBarrageDialog.TAG);
    }

    /**
     * 发送添加弹幕
     */
    @Override
    public void addBarrageBtnOkListener(String moveTvValue, int width, int height) {
        LogUtils.e("X:" + width + "  Y:" + height);
        sendBarrageRequest(moveTvValue, width, height);
    }

    /**
     * 发送弹幕
     */
    private void sendBarrageRequest(String barrageValue, int width, int height) {
        SendBarrageRequest sendBarrageRequest = new SendBarrageRequest();
        sendBarrageRequest.token = mBuProcessor.getToken();
        sendBarrageRequest.book_id = mBookId;
        sendBarrageRequest.catalogue_id = String.valueOf(mCatalogueId);
        sendBarrageRequest.barrage_content = barrageValue;
        sendBarrageRequest.style_id = String.valueOf(mBarrageStyle);
        sendBarrageRequest.xcoord = String.valueOf(width);
        sendBarrageRequest.ycoord = mCurrentHeight + String.valueOf(height);
//        if (mUser.vip == 0) {
//            sendBarrageRequest.bean = "5";//非会员5 漫豆
//        } else {
//            sendBarrageRequest.bean = "3";//会员3 漫豆
//        }
        mPresenter.sendBarrageRequest(sendBarrageRequest);
    }

    /**
     * 发送弹幕成功
     */
    @Override
    public void getSendBarrageSuccess() {
        showToast("发送成功");
    }

    /**
     * 评论弹框 切换功能
     * 切换功能
     */
    @Override
    public void switchFunctionByCommentSoftKeyBtnListener() {
        switchFunction();
    }

    @Override
    public void photoBtnListener() {
        //从相机获取图片(不裁剪)
        getTakePhoto().onPickFromCapture(uri);
    }

    @Override
    public void albumBtnListener(int maxPicNum) {
        //从相册中获取图片（不裁剪）
        getTakePhoto().onPickMultiple(maxPicNum);
    }

    /**
     * 发送评论
     */
    @Override
    public void CommentSendMessageBtnListener(List<TImage> tImageList, String content) {

    }

    /**
     * 回复评论
     */
    @Override
    public void ReplyCommentBtnListener(String content) {

    }

    /**
     * 切换弹幕或者评论
     */
    private void switchFunction() {
        if (isBarrageState) {
            isBarrageState = false;
            //切换成评论状态
            mSendMessageLeftIv.setImageResource(R.mipmap.comment_icon);
            mSendMessageRightIv.setVisibility(View.GONE);
            mMessageEt.setHint(getResources().getString(R.string.BarrageStylePopupWindow_comment_hint));
        } else {//切换成弹幕状态
            isBarrageState = true;
            mSendMessageLeftIv.setImageResource(R.mipmap.barrage_icon);
            mSendMessageRightIv.setVisibility(View.VISIBLE);
            mMessageEt.setHint(getResources().getString(R.string.BarrageStylePopupWindow_barrage_hint));
        }
    }

    BarrageStylePopupWindow barrageStylePopupWindow;

    /**
     * 显示弹幕样式
     */
    public void showBarrageStyle() {
        SoftKeyboardUtil.hideSoftKeyboard(this);
        showFunction();
        if (barrageStylePopupWindow != null) {
            barrageStylePopupWindow.setDismiss();
            barrageStylePopupWindow = null;
        }
        barrageStylePopupWindow = new BarrageStylePopupWindow(this, barrageStyleResponseList, mBuProcessor, this);
        barrageStylePopupWindow.initPopWindow(mReadLayout);
    }

    @Override
    public void hintOpenVipBtnListener(int barrageStyle) {
        mBarrageStyle = barrageStyle;
        showOpenVipDialog();
    }

//    @Override
//    public void hintBeansExchangeBtnListener() {
//        showBeansExchangeDialog();
//    }

    /**
     * from:弹幕样式PopupWindow
     * 显示漫豆兑换弹幕弹框
     */
    @Override
    public void showBeansExchangeBtnListener(int barrageStyle) {
        mBarrageStyle = barrageStyle;
        showBeansExchangeDialog();
    }

//    /**
//     * from:弹幕样式PopupWindow
//     * 显示弹幕样式:确定样式按钮
//     */
//    @Override
//    public void switchStyleLayoutBtnListener(int style) {
//        mBarrageStyle = style;
//        SoftKeyboardUtil.hideSoftKeyboard(this);
//        //显示
//    }

    @Override
    public void showPublishBarrageBtnListener() {
        showBarragePopupWindow();
        SoftKeyboardUtil.hideSoftKeyboard(this);
    }

    /**
     * 点击图片自动显示或隐藏功能按钮
     */
    private void showFunction() {
        if (mReadBottomLl.getVisibility() == View.VISIBLE) {
            mSendMessageLl.setVisibility(View.INVISIBLE);
            mReadBottomLl.setVisibility(View.INVISIBLE);
            mBarrageLl.setVisibility(View.INVISIBLE);
            mAddBookshelfIv.setVisibility(View.INVISIBLE);

        } else {
            mSendMessageLl.setVisibility(View.VISIBLE);
            mReadBottomLl.setVisibility(View.VISIBLE);
            mBarrageLl.setVisibility(View.VISIBLE);
            mAddBookshelfIv.setVisibility(View.VISIBLE);
//            if (isShowBackTopIv) {
//                mBackTopIv.setVisibility(View.VISIBLE);
//            }
        }
    }

    /**
     * 点击图片隐藏功能按钮
     */
    private void hideFunction() {
        mSendMessageLl.setVisibility(View.INVISIBLE);
        mReadBottomLl.setVisibility(View.INVISIBLE);
        mBarrageLl.setVisibility(View.INVISIBLE);
        mAddBookshelfIv.setVisibility(View.INVISIBLE);
        mBackTopIv.setVisibility(View.INVISIBLE);
    }


    public void initBanner() {
        // 设置数据
        mBanner.setDelayedTime(4000);//切换时间
        mBanner.setPages(bannerList, (MZHolderCreator<BannerReadingViewHolder>) () -> new BannerReadingViewHolder(mImageLoaderHelper));
    }


    /**
     * 设置未点赞状态
     */
    public void setNoSupportState() {
        mSupportTv.setTextColor(getResources().getColor(R.color.color_30));
        Drawable drawable = getResources().getDrawable(R.mipmap.cartoon_chapter_praise_black);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mSupportTv.setCompoundDrawables(null, drawable, null, null);
    }

    /**
     * 设置点赞状态
     */
    public void setSupportState() {
        mSupportTv.setTextColor(getResources().getColor(R.color.buy_check_color));
        Drawable drawable = getResources().getDrawable(R.mipmap.cartoon_chapter_praise);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mSupportTv.setCompoundDrawables(null, drawable, null, null);
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
        UMWeb web = new UMWeb("https://www.baidu.com/");
        web.setTitle("hello");
        web.setThumb(new UMImage(this, R.mipmap.logo));
        web.setDescription("hello 123456");
        new ShareAction(this)
                .withMedia(web)
                .setPlatform(snsPlatform.mPlatform)
                .setCallback(new MyUMShareListener(this)).share();

    }

    private void shareWhatsApp(){
        //分享到WhatsApp
//        SnsPlatform snsPlatform = SHARE_MEDIA.WHATSAPP.toSnsPlatform();
//        //分享链接
//        UMWeb web = new UMWeb("https://www.baidu.com/");
//        web.setTitle("hello");
//        web.setThumb(new UMImage(this, R.mipmap.logo));
//        web.setDescription("hello 123456");
//        new ShareAction(this)
//                .withMedia(web)
//                .setPlatform(snsPlatform.mPlatform)
//                .setCallback(new MyUMShareListener(this)).share();
        new ShareAction(this)
                .setPlatform(SHARE_MEDIA.WHATSAPP)//传入平台
                .withText("hello")//分享内容
                .setCallback(new MyUMShareListener(this))//回调监听器
                .share();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void takeFail(TResult result, String msg) {
    }

    @Override
    public void takeCancel() {
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 获取TakePhoto实例
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        //设置压缩规则，最大500kb
        takePhoto.onEnableCompress(new CompressConfig.Builder().setMaxSize(500 * 1024).setMaxPixel(800).create(), true);
        return takePhoto;
    }

    @Override
    public void takeSuccess(TResult result) {
        showLoading();
//        photoList.add(null);
        photoList.addAll(result.getImages());
        //传到CommentSoftKeyPopupWindow
//        new CommentSoftKeyPopupWindow(ReadActivity.this, ReadActivity.this, photoList).initPopWindow(mReadLayout);
        mCommentSoftKeyPopupWindow.setListData(photoList, this);

    }

    private void initInjectData() {
        DaggerReadComponent.builder().appComponent(getAppComponent())
                .readModule(new ReadModule(this, this))
                .activityModule(new ActivityModule(this)).build().inject(this);
    }


}
