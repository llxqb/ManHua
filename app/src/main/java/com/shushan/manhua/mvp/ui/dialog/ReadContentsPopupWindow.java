package com.shushan.manhua.mvp.ui.dialog;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.response.SelectionResponse;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.ui.adapter.ReadingChapterAdapter;


/**
 * 目录PopupWindow
 */
public class ReadContentsPopupWindow {
    private Activity mContext;
    private ReadContentsPopupWindowListener mPopupWindowListener;
    private CustomPopWindow mCustomPopWindow;
    private SelectionResponse mSelectionResponse;
    private ImageLoaderHelper mImageLoaderHelper;
    private int mIsVip;

    public ReadContentsPopupWindow(Activity context, SelectionResponse selectionResponse, ReadContentsPopupWindowListener popupWindowListener, int isVip, ImageLoaderHelper imageLoaderHelper) {
        mContext = context;
        mPopupWindowListener = popupWindowListener;
        this.mSelectionResponse = selectionResponse;
        mIsVip = isVip;
        mImageLoaderHelper = imageLoaderHelper;
    }

    public void initPopWindow(View view) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_read_content, null);
        //处理popWindow 显示内容
        handlePopListView(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)
                .enableBackgroundDark(true)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create();
        mCustomPopWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private float endX = 0;
    boolean isScrolling = false;//是否正在滑动

    private void handlePopListView(View contentView) {
        RecyclerView recyclerView = contentView.findViewById(R.id.recycler_view);
        SeekBar mSeekBar = contentView.findViewById(R.id.seek_bar);
        ImageView preChapterIv = contentView.findViewById(R.id.pre_chapter_iv);
        ImageView nextChapterIv = contentView.findViewById(R.id.next_chapter_iv);
        TextView bookNumTv = contentView.findViewById(R.id.book_num_tv);
        ReadingChapterAdapter readingChapterAdapter = new ReadingChapterAdapter(mSelectionResponse.getAnthology(), mImageLoaderHelper);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(readingChapterAdapter);
        readingChapterAdapter.setVipCost(mIsVip, mSelectionResponse.getVip_cost());
//        bookNumTv.setText("");
//        Total 20 chapter,10 chapter belum dibaca
        String totalValue = "Total " + mSelectionResponse.getWords() + " chapter," + mSelectionResponse.getResidue_words() + " chapter belum dibaca";
        bookNumTv.setText(totalValue);
        readingChapterAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            SelectionResponse.AnthologyBean anthologyBean = (SelectionResponse.AnthologyBean) adapter.getItem(position);
            if (mPopupWindowListener != null) {
                if (anthologyBean != null) {
                    mPopupWindowListener.clickChapterBtnListener(anthologyBean.getCatalogue_id());
                    mCustomPopWindow.dissmiss();
                }
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!isScrolling) {
                    //正在拖动
                    smoothMoveToPosition(recyclerView, progress * mSelectionResponse.getAnthology().size() / 100);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //开始拖动
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //停止拖动
            }
        });
        preChapterIv.setOnClickListener(v -> {
            mSeekBar.setProgress(0);
            smoothMoveToPosition(recyclerView, 0);
        });
        nextChapterIv.setOnClickListener(v -> {
            mSeekBar.setProgress(100);
            smoothMoveToPosition(recyclerView, mSelectionResponse.getAnthology().size());
        });


//这里的mRvHx是需要绑定滚动条的RecyclerView
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                // 当不滚动时处理
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    // 获取当前滚动到的条目位置
//                    float firstIndex = linearLayoutManager.findFirstVisibleItemPosition();
//                    LogUtils.e("Progress:" + (int)(firstIndex / (readingChapterAdapter.getItemCount() - 3) * 100));
//                    LogUtils.e("Count:" + readingChapterAdapter.getItemCount());
//                    mSeekBar.setProgress((int)(firstIndex / (readingChapterAdapter.getItemCount() - 3) * 100));
//                }
                isScrolling = false;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isScrolling = true;
                // 获取当前滚动到的条目位置
                float firstIndex = linearLayoutManager.findFirstVisibleItemPosition();
                mSeekBar.setProgress((int) (firstIndex / (readingChapterAdapter.getItemCount() - 3) * 100));

//                //整体的总宽度，注意是整体，包括在显示区域之外的。
//                int range = recyclerView.computeHorizontalScrollRange();
//                float density = SystemUtils.getScreenDensity(mContext);
//                //计算出溢出部分的宽度，即屏幕外剩下的宽度
//                float maxEndX = range + (10 * density) + 5 - SystemUtils.getScreenWidth(mContext);
//                //滑动的距离
//                endX += dx;
//                //计算比例
//                float proportion = endX / maxEndX;
//                LogUtils.e("endX:" + endX + " maxEndX:" + maxEndX + " proportion:" + (int) (proportion * 100));
//                mSeekBar.setProgress((int) (proportion * 100));
            }

        });


    }

    /**
     * 目标项是否在最后一个可见项之后
     */
    private boolean mShouldScroll;
    /**
     * 记录目标项位置
     */
    private int mToPosition;

    /**
     * 滑动到指定位置
     *
     * @param mRecyclerView
     * @param position
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));

//        LogUtils.e("position:" + position + "  firstItem:" + firstItem);
        if (position < firstItem) {
            // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在第一个可见项之后，最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }

    public interface ReadContentsPopupWindowListener {
        void clickChapterBtnListener(int chapterId);
    }
}
