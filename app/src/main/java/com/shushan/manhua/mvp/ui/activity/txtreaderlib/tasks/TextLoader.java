package com.shushan.manhua.mvp.ui.activity.txtreaderlib.tasks;


import com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces.IChapter;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces.ILoadListener;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces.IParagraphData;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces.ITxtTask;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.main.ParagraphData;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.main.TxtReaderContext;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.utils.ELogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bifan-wei
 * on 2018/1/28.
 */

public class TextLoader  {
    private String tag = "FileDataLoadTask";
    public void load(String text, TxtReaderContext readerContext, ILoadListener callBack) {
        IParagraphData paragraphData = new ParagraphData();
        List<IChapter> chapter = new ArrayList<>();
        callBack.onMessage("start read text");
        ELogger.log(tag, "start read text");
        paragraphData.addParagraph(text + "");
        readerContext.setParagraphData(paragraphData);
        readerContext.setChapters(chapter);
        ITxtTask txtTask = new TxtConfigInitTask();
        txtTask.Run(callBack, readerContext);
    }
}
