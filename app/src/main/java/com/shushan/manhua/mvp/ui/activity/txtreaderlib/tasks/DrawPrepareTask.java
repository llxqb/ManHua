package com.shushan.manhua.mvp.ui.activity.txtreaderlib.tasks;

import android.graphics.Color;

import com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces.ILoadListener;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces.ITxtTask;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.main.PaintContext;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.main.TxtConfig;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.main.TxtReaderContext;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.utils.ELogger;


/**
 * Created by bifan-wei
 * on 2017/11/27.
 */

public class DrawPrepareTask implements ITxtTask {
    private String tag = "DrawPrepareTask";

    @Override
    public void Run(ILoadListener callBack, TxtReaderContext readerContext) {
        callBack.onMessage("start do DrawPrepare");
        ELogger.log(tag, "do DrawPrepare");
        initPainContext(readerContext.getPaintContext(), readerContext.getTxtConfig());
        readerContext.getPaintContext().textPaint.setColor(Color.WHITE);
        ITxtTask txtTask = new BitmapProduceTask();
        txtTask.Run(callBack, readerContext);
    }

    private void initPainContext(PaintContext paintContext, TxtConfig txtConfig) {
        TxtConfigInitTask.initPainContext(paintContext, txtConfig);
    }
}
