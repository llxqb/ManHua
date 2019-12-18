package com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces;


import com.shushan.manhua.mvp.ui.activity.txtreaderlib.bean.TxtMsg;

/*
* create by bifan-wei
* 2017-11-13
*/
public interface ILoadListener {
    void onSuccess();
    void onFail(TxtMsg txtMsg);
    void onMessage(String message);
}
