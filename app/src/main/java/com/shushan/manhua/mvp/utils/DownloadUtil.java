package com.shushan.manhua.mvp.utils;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.shushan.manhua.BuildConfig;
import com.shushan.manhua.help.ApiHelper;
import com.shushan.manhua.listener.DownloadListener;
import com.shushan.manhua.network.networkapi.BookApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Description：下载文件工具类
 * Created by kang on 2018/3/9.
 */

public class DownloadUtil {
    private static final String TAG = "DownloadUtil";
    public static final String fileName = "/DownloadFile";
    public static final String PATH_CHALLENGE_VIDEO = Environment.getExternalStorageDirectory() + "/manhua";
    //视频下载相关
    private BookApi mApi;
    private Call<ResponseBody> mCall;
    private File mFile;
    private Thread mThread;
    private String mVideoPath; //下载到本地的视频路径

    public DownloadUtil() {
        if (mApi == null) {
            //这里baseUrl 随便设置的
            mApi = ApiHelper.getInstance().buildRetrofit(BuildConfig.MAN_HUA_BASE_URL)
                    .createService(BookApi.class);
        }
    }

    public void downloadFile(String url, final DownloadListener downloadListener) {
        //通过Url得到保存到本地的文件名
        String name = url;
        if (FileUtils.createOrExistsDir(PATH_CHALLENGE_VIDEO)) {
            int i = name.lastIndexOf('/');//一定是找最后一个'/'出现的位置
            if (i != -1) {
                name = name.substring(i);
                mVideoPath = PATH_CHALLENGE_VIDEO + fileName +
                        name;
            }
        }
        if (TextUtils.isEmpty(mVideoPath)) {
            Log.e(TAG, "downloadVideo: 存储路径为空了");
            return;
        }
        //建立一个文件
        mFile = new File(mVideoPath);
        if (!FileUtils.isFileExists(mFile) && FileUtils.createOrExistsFile(mFile)) {
            if (mApi == null) {
                Log.e(TAG, "downloadVideo: 下载接口为空了");
                return;
            }
            mCall = mApi.downloadFile(url);
            mCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull final Response<ResponseBody> response) {
                    //下载文件放在子线程
                    mThread = new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            //保存到本地
                            writeFile2Disk(response, mFile, downloadListener);
                        }
                    };
                    mThread.start();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    downloadListener.onFailure("网络错误！");
                }
            });
        } else {
            downloadListener.onFinish(mVideoPath);
        }
    }

    /**
     * 检查本地是否有目标文件
     */
    public static String checkFileIsExist(String url) {
        //通过Url得到保存到本地的文件名
        String name = url;
        String localFilePath = "";
        if (FileUtils.createOrExistsDir(PATH_CHALLENGE_VIDEO)) {
            int i = name.lastIndexOf('/');//一定是找最后一个'/'出现的位置
            if (i != -1) {
                name = name.substring(i);
                localFilePath = PATH_CHALLENGE_VIDEO + fileName + name;
            }
        }
        File mFile = new File(localFilePath);
        if (FileUtils.isFileExists(mFile)) {
            return localFilePath;
        }
        return "";
    }

    private void writeFile2Disk(Response<ResponseBody> response, File file, DownloadListener downloadListener) {
        downloadListener.onStart();
        long currentLength = 0;
        OutputStream os = null;

        if (response.body() == null) {
            downloadListener.onFailure("资源错误！");
            return;
        }
        InputStream is = response.body().byteStream();
        long totalLength = response.body().contentLength();
        Log.e(TAG, "totalLength: " + totalLength);
        try {
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                downloadListener.onProgress((int) (100 * currentLength / totalLength));
//                Log.e(TAG, "当前进度: " + (int) (100 * currentLength / totalLength));
                Log.e(TAG, "currentLength:" + currentLength + "  当前进度: " + (int) (100 * currentLength / totalLength));
                if ((int) (100 * currentLength / totalLength) == 100) {
                    downloadListener.onFinish(mVideoPath);
                }
            }
        } catch (FileNotFoundException e) {
            downloadListener.onFailure("未找到文件！");
            e.printStackTrace();
        } catch (IOException e) {
            downloadListener.onFailure("IO错误！");
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
