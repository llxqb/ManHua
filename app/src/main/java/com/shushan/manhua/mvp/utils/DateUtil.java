package com.shushan.manhua.mvp.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by li.liu on 2018/10/19.
 * 日期相关util
 */

public class DateUtil {

    public static final String TIME_YYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_YYMMDD = "yyyy-MM-dd";
    public static final String TIME_YYMMDD_CHINA = "yyyy年MM月dd日";
    public static final String TIME_YYMM = "yyyy-MM";
    public static final String TIME_YYMMDD_T_HHMMSS = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String TIME_MMDD_HHMM = "MM-dd HH:mm";

    /**
     * POS交易序号
     */
    public static int getPOSRandomDate() {
        SimpleDateFormat format = new SimpleDateFormat(TIME_YYMMDD_HHMMSS, Locale.CHINA);
        String time = "2014-01-01 00:00:00";
        try {
            Date date = format.parse(time);
            long tmp = System.currentTimeMillis() - date.getTime();
            return Math.abs((int) tmp / 1000);
        } catch (ParseException ex) {
            return 0;
        }
    }


    public static Date getDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static String getCurrentTime(String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.CHINA);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 根据pattern获取当前时间
     */
    public static String getTimeByPattern(String pattern) {
        Date date = getDate();
        SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.CHINA);
        return df.format(date);
    }


    /**
     * 日期转换为字符串
     */
    public static String dateTranString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 获取n天前的时间戳（秒）
     */
    public static int beforeNDateStamp(int n) {
        return (int) (System.currentTimeMillis() / 1000) - 86400 * n;
    }

    public static String transformTimestamp(int timestamp, String pattern) {
        try {
            Date date = new Date(timestamp * 1000L);
            SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
            String d = format.format(date);
            return d;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 时间戳转换成字符串
     */
    public static String getStrTime(long cc_time, String pattern) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
        re_StrTime = sdf.format(new Date(cc_time * 1000L));
        return re_StrTime;
    }

    // 将字符串转为时间戳
    public static String getTime(String user_time, String pattern) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException ignored) {
        }
        return re_time;
    }

    /**
     * 浏览器时间转字符串
     */
    public static String getDateToString(String dateString, String pattern) {
        if (TextUtils.isEmpty(dateString)) return "";
        Date date = new Date(Long.parseLong(dateString));
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(date);
    }


    public static Date formatPayDate(String time) {
        Date date = new Date();
        try {
            String tmp = time.replace("T", " ").split("\\.")[0];
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = df.parse(tmp);
        } catch (Exception ex) {
        }
        return date;
    }

    /**
     * 获取某月份天数
     * b
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前时间：long类型
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 得到 今天 明天 后天
     *
     * @return
     */
    public static String getTimeChinString(String time, String format) {
        long s = timeToLong(stringTimeToFormat(time, TIME_YYMMDD), TIME_YYMMDD);
        long now = timeToLong(getFullTime(TIME_YYMMDD), TIME_YYMMDD);
        if (now - s == 0) {
            return "Hari ini";
        } else if (now - s == 1000 * 60 * 60 * 24) {
            return "1 hari lalu";
        } else if (now - s == 1000 * 60 * 60 * 24 * 2) {
            return "2 hari lalu";
        } else {
            return "3 hari lalu";
//            return stringTimeToFormat(time, format);
        }
    }

    /**
     * 得到 今天 明天 后天
     *
     * @return
     */
    public static String getTimeChinString(long s, String format) {
        long now = timeToLong(getFullTime(TIME_YYMMDD), TIME_YYMMDD);
        if (now - s == 0) {
            return "Hari ini";
        } else if (now - s == 1000 * 60 * 60 * 24) {
            return "1 hari lalu";
        } else if (now - s == 1000 * 60 * 60 * 24 * 2) {
            return "2 hari lalu";
        } else {
            return "3 hari lalu";
//            return stringTimeToFormat(time, format);
        }
    }

    /**
     * 把String类型的时间 转换为long
     *
     * @param time   2014-12-20 15:33:20
     * @param format mm:ss
     * @return
     */
    public static long timeToLong(String time, String format) {
        long min = 0;
        try {
            min = new SimpleDateFormat(format).parse(time).getTime();
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return min;
    }

    /**
     * 把String类型的时间 转换为format类型的时间
     *
     * @param time   2014-12-20 15:33:20
     * @param format mm:ss
     * @return
     */
    public static String stringTimeToFormat(String time, String format) {
        long time1 = timeToLong(time, TIME_YYMMDD_HHMMSS);

        return longToTime(time1, format);
    }

    /**
     * 把long类型的时间 转换为format类型的时间
     *
     * @param time   long
     * @param format 2014-12-20 15:33:20
     * @return
     */
    public static String longToTime(long time, String format) {
        return new SimpleDateFormat(format).format(new Date(time));
    }

    public static String getFullTime(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

}
