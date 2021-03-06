package com.shushan.manhua.mvp.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.List;

/**
 * desc:数据相关
 */
public class DataUtils {
    /**
     * json字符串转list
     */
    public static boolean isContainString(List<String> list, String text) {
        if (list == null) return false;
        for (String s : list) {
            if (s.equals(text)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串大写转小写
     */
    public static String uppercaseToLowercase(String uppercaseStr) {
        return uppercaseStr.toLowerCase();
    }


    /**
     * 清除缓存
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


    /**
     * 集合转字符串
     */
    public static String ListToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null || list.get(i).equals("")) {
                    continue;
                }
                sb.append(list.get(i)).append(",");
            }
            return sb.toString().substring(0, sb.toString().length() - 1);
        } else {
            return "";
        }
    }
}
