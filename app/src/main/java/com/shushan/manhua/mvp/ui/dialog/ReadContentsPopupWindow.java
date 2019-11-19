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
import com.shushan.manhua.entity.response.ChapterResponse;
import com.shushan.manhua.mvp.ui.adapter.ReadingChapterAdapter;
import com.shushan.manhua.mvp.utils.LogUtils;
import com.shushan.manhua.mvp.utils.SystemUtils;

import java.util.List;


/**
 * 目录PopupWindow
 */
public class ReadContentsPopupWindow {
    private Activity mContext;
    private ReadContentsPopupWindowListener mPopupWindowListener;
    private CustomPopWindow mCustomPopWindow;
    private List<ChapterResponse> chapterResponseList;

    public ReadContentsPopupWindow(Activity context, List<ChapterResponse> chapterResponseList) {
        mContext = context;
//        mPopupWindowListener = popupWindowListener;
        this.chapterResponseList = chapterResponseList;
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

    private void handlePopListView(View contentView) {
        RecyclerView recyclerView = contentView.findViewById(R.id.recycler_view);
        SeekBar mSeekBar = contentView.findViewById(R.id.seek_bar);
        ImageView preChapterIv = contentView.findViewById(R.id.pre_chapter_iv);
        ImageView nextChapterIv = contentView.findViewById(R.id.next_chapter_iv);
        TextView bookNumTv = contentView.findViewById(R.id.book_num_tv);
        ReadingChapterAdapter readingChapterAdapter = new ReadingChapterAdapter(chapterResponseList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(readingChapterAdapter);
//        bookNumTv.setText("");

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //正在拖动
                smoothMoveToPosition(recyclerView, progress * chapterResponseList.size() / 100);
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
            smoothMoveToPosition(recyclerView, 0);
            mSeekBar.setProgress(0);
        });
        nextChapterIv.setOnClickListener(v -> {
            smoothMoveToPosition(recyclerView, chapterResponseList.size());
            mSeekBar.setProgress(100);
        });

//这里的mRvHx是需要绑定滚动条的RecyclerView
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //整体的总宽度，注意是整体，包括在显示区域之外的。
                int range = recyclerView.computeHorizontalScrollRange();
                float density = SystemUtils.getScreenDensity(mContext);
                //计算出溢出部分的宽度，即屏幕外剩下的宽度
                float maxEndX = range + (10 * density) + 5 - SystemUtils.getScreenWidth(mContext);
                //滑动的距离
                endX += dx;
                //计算比例
                float proportion = endX / maxEndX;

                mSeekBar.setProgress((int) (proportion * 100));
//                //计算滚动条宽度
//                int transMaxRange = ((ViewGroup) mSeekBar.getParent()).getWidth() - mSeekBar.getWidth();
//                //设置滚动条移动
////                mSeekBar.setTranslationX(transMaxRange * proportion);
//                LogUtils.e("proportion2:"+transMaxRange * proportion);
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
//        void pageTurningBtnListener();
//
//        void nightModelBtnListener(boolean nightModel);
//
//        void barrageSwitchBtnListener();
//
//        void clickMoreBtnListener();
    }
}
