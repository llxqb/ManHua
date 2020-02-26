package com.shushan.manhua.mvp.ui.dialog;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shushan.manhua.R;
import com.shushan.manhua.entity.constants.Constant;
import com.shushan.manhua.entity.response.ReadingBookResponse;
import com.shushan.manhua.entity.response.SelectionResponse;
import com.shushan.manhua.entity.user.BuProcessor;
import com.shushan.manhua.help.ImageLoaderHelper;
import com.shushan.manhua.mvp.ui.adapter.SelectionAdapter;
import com.shushan.manhua.mvp.utils.SystemUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * 章节列表PopupWindow
 */
public class ChapterListPopupWindow {
    private Activity mContext;
    private ChapterListPopupWindowListener mPopupWindowListener;
    private CustomPopWindow mCustomPopWindow;
    private BuProcessor mBuProcessor;
    private ReadingBookResponse mReadingBookResponse;
    private SelectionResponse mSelectionResponse;
    private ImageLoaderHelper mImageLoaderHelper;
    private SelectionAdapter mSelectionAdapter;
    private ConstraintLayout mPopupChapterListLayout;

    public ChapterListPopupWindow(Activity context, ReadingBookResponse readingBookResponse, SelectionResponse selectionResponse, BuProcessor buProcessor, ImageLoaderHelper imageLoaderHelper, ChapterListPopupWindowListener popupWindowListener) {
        mContext = context;
        mReadingBookResponse = readingBookResponse;
        mSelectionResponse = selectionResponse;
        mBuProcessor = buProcessor;
        mImageLoaderHelper = imageLoaderHelper;
        mPopupWindowListener = popupWindowListener;
    }

    public void setBackGroundColor(int color) {
        mPopupChapterListLayout.setBackgroundColor(color);
    }

    public void setDismiss() {
        if (mCustomPopWindow != null) {
            mCustomPopWindow.getPopupWindow().dismiss();
        }
    }

    public void initPopWindow(View view) {
        if (mCustomPopWindow == null) {
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.popup_chapter_list, null);
            //处理popWindow 显示内容
            handlePopListView(contentView);
            //创建并显示popWindow
            mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                    .setView(contentView)
                    .enableBackgroundDark(false)
                    .size(SystemUtils.getScreenWidth(mContext) * 4 / 5, ViewGroup.LayoutParams.MATCH_PARENT)//显示大小
                    .create();
            mCustomPopWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
        }
    }


    private void handlePopListView(View contentView) {
        mPopupChapterListLayout = contentView.findViewById(R.id.popup_chapter_list_layout);
        ImageView mBookCoverIv = contentView.findViewById(R.id.book_cover_iv);
        TextView mBookNameTv = contentView.findViewById(R.id.book_name_tv);
        TextView mTotalChapterNumTv = contentView.findViewById(R.id.total_chapter_num_tv);
        TextView mSortTv = contentView.findViewById(R.id.sort_tv);
        RecyclerView mRecyclerView = contentView.findViewById(R.id.recycler_view);
        mImageLoaderHelper.displayRoundedCornerImage(mContext, mReadingBookResponse.getCatalogue().getSquare_cover(), mBookCoverIv, 4, Constant.LOADING_DEFAULT_2);
        mBookNameTv.setText(mReadingBookResponse.getCatalogue().getBook_name());
        String totalWords = "Total " + mSelectionResponse.getWords() + " bab";
        mTotalChapterNumTv.setText(totalWords);
        mSelectionAdapter = new SelectionAdapter(mSelectionResponse.getAnthology(), mImageLoaderHelper, true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mSelectionAdapter);
        mSelectionAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            SelectionResponse.AnthologyBean anthologyBean = (SelectionResponse.AnthologyBean) adapter.getItem(position);
            if (mPopupWindowListener != null) {
                if (anthologyBean != null) {
                    mPopupWindowListener.switchChapterPage(anthologyBean.getCatalogue_id());
                    mCustomPopWindow.dissmiss();
                }
            }
        });
        mSortTv.setOnClickListener(v -> {
            if (sort == 1) {//进行正序排序
                sort = 0;
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.list_positive_sequence);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mSortTv.setCompoundDrawables(null, null, drawable, null);
                mSortTv.setText(mContext.getString(R.string.SelectionDetailFragment_sort_positive));
            } else {//进行逆序排序
                sort = 1;
                Drawable drawable = mContext.getResources().getDrawable(R.mipmap.list_inverted_order);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mSortTv.setCompoundDrawables(null, null, drawable, null);
                mSortTv.setText(mContext.getString(R.string.SelectionDetailFragment_sort_negative));
            }
            initSortList(mSelectionResponse.getAnthology());
        });

    }

    private int sort = 0;//sort 0: 正序  1 ：逆序

    /**
     * 对列表正序 逆序排序
     * isAllSort : 0 是 对一页排序  1 是对所有数据排序
     */
    private void initSortList(List<SelectionResponse.AnthologyBean> selectionResponseList) {
        if (selectionResponseList != null) {
            Comparator<SelectionResponse.AnthologyBean> comparator = (dataBean1, dataBean2) -> {
                // 按getRecommend从大到小排序
                if (sort == 1) {
                    return dataBean2.getSort() - dataBean1.getSort();
                } else {
                    return dataBean1.getSort() - dataBean2.getSort();
                }
            };
            //这里就会自动根据规则进行排序
            Collections.sort(selectionResponseList, comparator);
            mSelectionAdapter.setNewData(selectionResponseList);
//            //加载更多这样设置
//            if (page == 1) {
//                mSelectionAdapter.setNewData(selectionResponseList);
//            } else {
//                mSelectionAdapter.addData(selectionResponseList);
//                mSelectionAdapter.loadMoreComplete();
//            }
        }
    }


    public interface ChapterListPopupWindowListener {
        void switchChapterPage(int chapterId);
    }
}
