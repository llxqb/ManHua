package com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces;


import com.shushan.manhua.mvp.ui.activity.txtreaderlib.main.TxtReaderContext;

/*
* create by bifan-wei
* 2017-11-13
*/
public interface ITxtTask {
    void Run(ILoadListener callBack, TxtReaderContext readerContext);
}
