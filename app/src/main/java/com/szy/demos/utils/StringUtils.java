package com.szy.demos.utils;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/5/27.
 */

public class StringUtils {
    private static long oneDay = 24 * 60 * 60 * 1000;
    public static final int TYPE_TIME_CHAR = 0;
    public static final int TYPE_TIME_NUM = 1;

    public static boolean isEmpty(String str) {
        boolean ret = false;
        if (str == null || str.trim().equals("") || str.trim().equalsIgnoreCase("null")) {
            ret = true;
        }
        return ret;
    }

    /*
    * 把时间转成今天明天后天 其他是long
    * */
    public static String getTimeContent(Calendar calendar, DateFormat format, int type) {
        String ret = "";
        if (type == TYPE_TIME_CHAR) {
            long nontime = System.currentTimeMillis();
            String strtime = format.format(calendar.getTimeInMillis());
            if (strtime.equals(format.format(nontime).toString())) {
                ret = "今天";
            } else if (strtime.equals(format.format(nontime + oneDay).toString())) {
                ret = "明天";
            } else if (strtime.equals(format.format(nontime + 2 * oneDay).toString())) {
                ret = "后天";
            } else {
                ret = calendar.getTime().getTime() + "";
            }
        } else if (type == TYPE_TIME_NUM) {
            ret = calendar.getTimeInMillis() + "";
        }
        return ret;
    }


}