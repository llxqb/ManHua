package com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces;


import com.shushan.manhua.mvp.ui.activity.txtreaderlib.bean.TxtChar;

/**
 * Created by bifan-wei
 * on 2017/12/5.
 */

public interface ITextSelectListener {
    void onTextChanging(TxtChar firstSelectedChar, TxtChar lastSelectedChar);
    void onTextChanging(String selectText);
    void onTextSelected(String selectText);
}
