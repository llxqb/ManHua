package com.shushan.manhua.mvp.ui.activity.txtreaderlib.main;

import android.graphics.Canvas;
import android.graphics.Region;
import android.graphics.drawable.GradientDrawable;
import android.view.MotionEvent;
import android.widget.Scroller;

import com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces.IReaderViewDrawer;


/**
 * Created by bifan-wei
 * on 2020/3/3.
 * 上下翻页
 */

public class UpDownDrawer extends PageDrawerBase implements IReaderViewDrawer {
    private String tag = "SerialPageDrawer";
    private final static int BorderShadowWith = 5;

    public UpDownDrawer(TxtReaderView readerView, TxtReaderContext readerContext, Scroller scroller) {
        super(readerView, readerContext, scroller);
    }

    @Override
    public void drawPageNextPageShadow(Canvas canvas) {
        //绘制执行下一页的页边阴影
        mPath.reset();
        int left = 0;
        int top = (int) getMoveDistanceY() + getHeight();
        int right = getWidth();
        int bottom = top + BorderShadowWith;
        if (top > BorderShadowWith) {
            getPageNextBorderDrawable().setBounds(left, top, right, bottom);
            getPageNextBorderDrawable().draw(canvas);
        }
    }

    @Override
    public void drawPageNextBottomPage(Canvas canvas) {
        //绘制执行下一页时的下面页部分
        float startPosition = getHeight() + getMoveDistanceY();
        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.lineTo(getWidth(), 0);
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.lineTo(0, 0);
        canvas.clipPath(mPath, Region.Op.INTERSECT);
        canvas.drawBitmap(getBottomPage(), 0, startPosition, null);


    }

    @Override
    public void drawPageNextTopPage(Canvas canvas) {
        //绘制执行下一页时的上面页部分
        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.lineTo(getWidth(), 0);
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.lineTo(0, 0);
        canvas.clipPath(mPath, Region.Op.INTERSECT);
        canvas.drawBitmap(getTopPage(), 0, getMoveDistanceY() + 1, null);
    }

    @Override
    public void drawPagePrePageShadow(Canvas canvas) {
        //绘制执行上一页的页边阴影
        mPath.reset();
        int left = 0;
        int top = (int) getMoveDistanceY() - BorderShadowWith;
        int right = getWidth();
        int bottom =(int) getMoveDistanceY();
        if (bottom < getHeight() - BorderShadowWith) {
            getPagePreBorderDrawable().setBounds(left, top, right, bottom);
            getPagePreBorderDrawable().draw(canvas);
        }

    }

    @Override
    public void drawPagePreBottomPage(Canvas canvas) {
        //绘制执行上一页时的下面页部分
        float startPosition = getMoveDistanceY();
        float x = 0;
        mPath.reset();
        mPath.moveTo(x, 0);
        mPath.lineTo(getWidth(), 0);
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(x, getHeight());
        mPath.lineTo(x, 0);
        canvas.clipPath(mPath, Region.Op.INTERSECT);
        canvas.drawBitmap(getBottomPage(), 0, startPosition - getHeight(), null);

    }

    @Override
    public void drawPagePreTopPage(Canvas canvas) {
        //绘制执行上一页时的上面页部分
        mPath.reset();
        mPath.moveTo(0, 0);
        mPath.lineTo(getWidth(), 0);
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.lineTo(0, 0);
        canvas.clipPath(mPath, Region.Op.INTERSECT);
        canvas.drawBitmap(getTopPage(), 0, getMoveDistanceY(), null);
    }

    private Boolean onPageStateBackAnimation = false;

    @Override
    public void startPageStateBackAnimation() {
        if (readerView.isPagePre() || readerView.isPageNext()) {
            onPageStateBackAnimation = true;
            scroller.startScroll(0, (int) readerView.mTouch.y, 0, -(int) getMoveDistanceY(), PageSwitchTime);
            postInvalidate();
        }

    }


    @Override
    public void startPageNextAnimation() {
        scroller.startScroll(0, getHeight() + (int) getMoveDistanceY(), 0, -(getHeight() + (int) getMoveDistanceY()), PageSwitchTime);
        readerView.mDown.y = getHeight();//从getWidth()开始
        readerView.CurrentMode = TxtReaderBaseView.Mode.PageNextIng;
        postInvalidate();
    }

    @Override
    public void startPagePreAnimation() {
        scroller.startScroll(0, (int) getMoveDistanceY(), 0, getHeight() - (int) getMoveDistanceY(), PageSwitchTime);
        readerView.mDown.y = 0;
        readerView.CurrentMode = TxtReaderBaseView.Mode.PagePreIng;
        postInvalidate();
    }

    @Override
    public void onTextSelectMoveForward(MotionEvent event) {

    }

    @Override
    public void onTextSelectMoveBack(MotionEvent event) {

    }

    @Override
    public void onPageMove(MotionEvent event) {
        readerView.mTouch.x = event.getX();
        readerView.mTouch.y = event.getY();
        postInvalidate();
    }

    private void postInvalidate() {
        readerView.postInvalidate();
    }

    @Override
    public void drawNote(Canvas canvas) {

    }

    @Override
    public void drawSelectedText(Canvas canvas) {
        if (readerView.CurrentMode == TxtReaderBaseView.Mode.PressSelectText) {
            drawPressSelectedText(canvas);
        } else if (readerView.CurrentMode == TxtReaderBaseView.Mode.SelectMoveBack) {
            drawSelectedLinesText(canvas);
        } else if (readerView.CurrentMode == TxtReaderBaseView.Mode.SelectMoveForward) {
            drawSelectedLinesText(canvas);
        }

    }

    private void drawSelectedLinesText(Canvas canvas) {
        getTextSelectDrawer().drawSelectedLines(readerView.getCurrentSelectTextLine(), canvas, readerContext.getPaintContext().selectTextPaint);
        //draw slider
        drawSlider(canvas);

    }

    private void drawPressSelectedText(Canvas canvas) {
        getTextSelectDrawer().drawSelectedChar(readerView.FirstSelectedChar, canvas, readerContext.getPaintContext().selectTextPaint);
        //draw slider
        drawSlider(canvas);
    }

    private void drawSlider(Canvas canvas) {
        if (readerView.getLeftSliderPath() != null && readerView.getRightSliderPath() != null) {
            canvas.drawPath(readerView.getLeftSliderPath(), readerContext.getPaintContext().sliderPaint);
            canvas.drawPath(readerView.getRightSliderPath(), readerContext.getPaintContext().sliderPaint);
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            readerView.mTouch.y = scroller.getCurrY();
            readerView.invalidate();
            checkPageData();
        }
    }

    private synchronized void checkPageData() {
        if (onPageStateBackAnimation) {
            if ((getMoveDistanceY() > 0 && getMoveDistanceY() <= 3) || (getMoveDistanceY() < 0 && getMoveDistanceY() >= -3)) {
                scroller.abortAnimation();
                readerView.releaseTouch();
                readerView.invalidate();
                onPageStateBackAnimation = false;
            }
        } else {
            if (readerView.mTouch.y == 0)//执行下一页数据获取
            {
                readerView.doPageNextDone();
                scroller.abortAnimation();

            } else if (readerView.mTouch.y == getHeight()) {//执行上一页数据获取
                readerView.doPagePreDone();
                scroller.abortAnimation();

            }
        }

    }


    private GradientDrawable mPagePreBorderDrawable;
    private GradientDrawable mPageNextBorderDrawable;

    private GradientDrawable getPagePreBorderDrawable() {
        if (mPagePreBorderDrawable == null) {
            int[] color = new int[]{0x55666666, 0x55666666, 0x55666666};
            mPagePreBorderDrawable = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, color);
        }
        return mPagePreBorderDrawable;
    }

    private GradientDrawable getPageNextBorderDrawable() {
        if (mPageNextBorderDrawable == null) {
            int[] color = new int[]{0x55666666, 0x55666666, 0x55666666};
            mPageNextBorderDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, color);
        }
        return mPageNextBorderDrawable;
    }


}

