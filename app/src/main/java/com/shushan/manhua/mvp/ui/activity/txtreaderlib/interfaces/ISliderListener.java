package com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces;


import com.shushan.manhua.mvp.ui.activity.txtreaderlib.bean.TxtChar;

/**
 * created by ： bifan-wei
 */

public interface ISliderListener {
    void onShowSlider(TxtChar txtChar);
    void onShowSlider(String CurrentSelectedText);
    void onReleaseSlider();
}
