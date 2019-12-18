package com.shushan.manhua.mvp.ui.activity.txtreaderlib.tasks;


import com.shushan.manhua.mvp.ui.activity.txtreaderlib.bean.FileReadRecordBean;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.bean.TxtFileMsg;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.bean.TxtMsg;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces.ILoadListener;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.interfaces.ITxtTask;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.main.FileReadRecordDB;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.main.TxtReaderContext;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.utils.ELogger;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.utils.FileCharsetDetector;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.utils.FileHashUtil;
import com.shushan.manhua.mvp.ui.activity.txtreaderlib.utils.FileUtil;

import java.io.File;


/**
 * created by ： bifan-wei
 */

public class TxtFileLoader {
    private String tag = "TxtFileLoader";

    public void load(String filePath, TxtReaderContext readerContext, ILoadListener loadListener) {
        load(filePath, null, readerContext, loadListener);
    }

    public void load(String filePath, String fileName, TxtReaderContext readerContext, ILoadListener loadListener) {
        if (!FileUtil.FileExist(filePath)) {
            loadListener.onFail(TxtMsg.FileNoExist);
            return;
        }
        loadListener.onMessage("initFile start");
        initFile(filePath, fileName, readerContext);
        ELogger.log(tag, "initFile done");
        loadListener.onMessage("initFile done");
        ITxtTask txtTask = new FileDataLoadTask();
        txtTask.Run(loadListener, readerContext);

    }

    private void initFile(String filePath, String fileName, TxtReaderContext readerContext) {
        File file = new File(filePath);
        TxtFileMsg fileMsg = new TxtFileMsg();
        fileMsg.FileSize = file.getTotalSpace();
        fileMsg.FilePath = filePath;
        fileMsg.FileCode = new FileCharsetDetector().getCharset(new File(filePath));

        fileMsg.CurrentParagraphIndex = 0;
        fileMsg.CurrentParagraphIndex = 0;
        fileMsg.PreParagraphIndex = 0;
        fileMsg.PreCharIndex = 0;
        if (fileName == null || fileName.trim().length() == 0) {
            fileName = file.getName();
        }
        fileMsg.FileName = fileName;

        //获取之前的播放进度记录
        FileReadRecordDB readRecordDB = new FileReadRecordDB(readerContext.getContext());
        readRecordDB.createTable();
        try {
            FileReadRecordBean r = readRecordDB.getRecordByHashName(FileHashUtil.getMD5Checksum(filePath));
            if (r != null) {
                fileMsg.PreParagraphIndex = r.paragraphIndex;
                fileMsg.PreCharIndex = r.chartIndex;
            }
        } catch (Exception e) {
        }
        readRecordDB.createTable();
        readerContext.setFileMsg(fileMsg);
    }
}
