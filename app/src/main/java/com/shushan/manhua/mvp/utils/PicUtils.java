package com.shushan.manhua.mvp.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class PicUtils {

    /**
     * 图片转Base64字符串
     */
    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);
    }


    /**
     * * 图片压缩方法一
     * <p>
     * 计算 bitmap大小，如果超过64kb，则进行压缩
     *
     * @param bitmap
     * @return
     */
    public static Bitmap ImageCompressL(Bitmap bitmap) {
        double targetwidth = Math.sqrt(64.00 * 1000);
        if (bitmap.getWidth() > targetwidth || bitmap.getHeight() > targetwidth) {
            // 创建操作图片用的matrix对象
            Matrix matrix = new Matrix();
            // 计算宽高缩放率
            double x = Math.max(targetwidth / bitmap.getWidth(), targetwidth
                    / bitmap.getHeight());
            // 缩放图片动作
            matrix.postScale((float) x, (float) x);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }


    /**
     * 将bmp转换为字节数组
     *
     * @param bmp
     * @param needRecycle
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * retrofit上传文件  测试
     */
//    public static void file_img(String path) {
//        OkHttpClient client = new OkHttpClient.Builder().
//                connectTimeout(60, TimeUnit.SECONDS).
//                readTimeout(60, TimeUnit.SECONDS).
//                writeTimeout(60, TimeUnit.SECONDS).build();
//
//        Retrofit retrofitUpload = new Retrofit.Builder()
//                .baseUrl(ServerConstant.DISPATCH_SERVICE)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//
//        PersonalInfoApi service = retrofitUpload.create(PersonalInfoApi.class);
//        File file = new File(path);
//        //设置Content-Type:application/octet-stream
//        RequestBody photoRequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
//        //设置Content-Disposition:form-data; name="photo"; filename="xuezhiqian.png"
//        MultipartBody.Part photo = MultipartBody.Part.createFormData("video", file.getName(), photoRequestBody);
//        //添加参数用户名和密码，并且是文本类型
//        Call<ResponseData> loadCall = service.uploadVideoRequest2(photo);
//        Log.e("ddd", "photo:" + photo);
//        loadCall.enqueue(new Callback<ResponseData>() {
//            @Override
//            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
//                Log.e("APP", response.body().resultCode + "");
//            }
//
//            @Override
//            public void onFailure(Call<ResponseData> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
//    }

}
