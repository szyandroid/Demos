package com.szy.demos.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {
    public final static long Minute = 60 * 1000;
    private final static long Hour = 60 * Minute;
    public final static long EightHours = 8 * Hour;
    public final static long Day = 24 * Hour;
    public final static long Month = 30 * Day;
    private final static long Week = 7 * Day;
    private final static String Space = "  ";
    private static SimpleDateFormat yyyyMMddHHmmssFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private static SimpleDateFormat yyyyMMddHHmmFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    private static SimpleDateFormat yyyyMMddFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static SimpleDateFormat yyyyMMFormater = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
    private static SimpleDateFormat yyMMddFormater = new SimpleDateFormat("yy/MM/dd", Locale.getDefault());

    private static SimpleDateFormat hhmmFormater = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private static SimpleDateFormat MMddhhmmFormater = new SimpleDateFormat("MM-dd  HH:mm", Locale.getDefault());
    private static SimpleDateFormat MMddFormater = new SimpleDateFormat("MM/dd", Locale.getDefault());
    private static SimpleDateFormat ddFormater = new SimpleDateFormat("dd", Locale.getDefault());
    private static SimpleDateFormat mmFormater = new SimpleDateFormat("MM", Locale.getDefault());
    private static SimpleDateFormat yyFormater = new SimpleDateFormat("yy", Locale.getDefault());

    /*
     * 获取传入时间与当前时间的天数差
     */
    public static int getTimeSpan(Long time) {

        if (time.equals(""))
            return 0;

        int day = 0;
        try {
            Date today = new Date();
            Date lastday = new Date(time);
            day = (int) ((today.getTime() - lastday.getTime()) / (24 * 60 * 60 * 1000));
        } catch (Exception e) {
        }

        return day;
    }

    /**
     * 将一个时间转成字符串 XX月XX天XX小时XX分
     *
     * @param time
     * @return
     */
    public static String toTimeString(long time) {
        if (time < 0) {
            return "";
        }
        int m, d, h, min;
        m = (int) (time / Month);
        d = (int) ((time - m * Month) / Day);
        h = (int) ((time - m * Month - d * Day) / Hour);
        min = (int) ((time % Hour) / Minute);
        StringBuilder sb = new StringBuilder();
        sb.append(m > 0 ? m + "月" : "");
        sb.append(d > 0 ? d + "天" : "");
        sb.append(h > 0 ? h + "小时" : "");
        sb.append(min > 0 ? min + "分钟" : "");
        return sb.toString();
    }

    /**
     * 格式化时间为 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String toFormatDayTime(long date) {
        return yyyyMMddHHmmssFormater.format(date);
    }

    public static String toFormatDayTimeWithNoSecond(long date) {
        return yyyyMMddHHmmFormater.format(date);
    }

    /**
     * 格式化时间为 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String toFormatDay(long date) {
        return yyyyMMddFormater.format(date);
    }

    public static String toFormatMonth(long date) {
        return yyyyMMFormater.format(date);
    }

    /**
     * 把毫秒值 转换为 HH:mm格式
     *
     * @param date
     * @return
     */
    public static String toFormatTime(long date) {
        return hhmmFormater.format(date);
    }

    public static String toFormatOnlyDay(long date) {
        return ddFormater.format(date);
    }

    /**
     * 格式化时间为 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String toFormatDay(Date date) {
        return yyyyMMddFormater.format(date);
    }

    /**
     * 转成格式化的日期 带星期
     *
     * @param date
     * @return
     */
    public static String toFormatDate(long date) {
        return formatDate(new SimpleDateFormat("EEEE", Locale.getDefault()), date);
    }

    /**
     * 转成格式化的时间 带星期
     *
     * @param date
     * @return
     */
    public static String toFormatDateTime(long date) {
        return formatDate(new SimpleDateFormat("HH:mm", Locale.getDefault()), date);
    }


    private static String formatDate(SimpleDateFormat timeFm, long date) {
        String formatDate = "";

        long Today = getTody();
        long day = getday(date);

        SimpleDateFormat weekFm = new SimpleDateFormat("EEEE  HH:mm", Locale.getDefault());
        SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd  HH:mm", Locale.getDefault());
        if (Today - day < 0) {
            // 这是当前时间还要早的时间了,比如说明天
            return dateFm.format(date);
        }
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int dayInWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (Today - day == 0) {
            formatDate = "今天" + Space + timeFm.format(date);
        } else if (Today - day == Day) {
            formatDate = "昨天" + Space + timeFm.format(date);
        } else if (Today - day < dayInWeek * Day) {// 判断是否在本周内
            formatDate = weekFm.format(date);
        } else {
            formatDate = dateFm.format(date);
        }
        return formatDate;

    }

    //格式： hh:mm(当天) 、 昨天、星期x（倒退7天之内且非昨天）、 yy/mm/dd(倒推7天之外)
    public static String formatContacttime(long time) {
        String formatDate = "";
        long Today = getTody();
        long day = getday(time);

        if (Today - day < 0) {
            // 这是当前时间还要早的时间了,比如说明天
            return yyMMddFormater.format(time);
        }

        if (Today - day == 0) {
            formatDate = toFormatTime(time);
        } else if (Today - day == Day) {
            formatDate = "昨天";
        } else if (Today - day < Day * 7) {
            formatDate = getWeek(time, "星期");
        } else {
            formatDate = yyMMddFormater.format(time);
        }
        return formatDate;
    }

    //格式： hh:mm(当天) 、 昨天  hh:mm、星期x  hh:mm（倒退7天之内且非昨天）、 yy/mm/dd  hh:mm(倒推7天之外)
    public static String formatTimstamp(long time) {
        String formatDate = "";
        long Today = getTody();
        long day = getday(time);

        if (Today - day < 0) {
            // 这是当前时间还要早的时间了,比如说明天
            return MMddhhmmFormater.format(time);
        }

        if (Today - day == 0) {
            formatDate = toFormatTime(time);
        } else if (Today - day == Day) {
            formatDate = "昨天 " + toFormatTime(time);
        } else if (Today - day < Day * 7) {
            formatDate = getWeek(time, "星期") + "  " + toFormatTime(time);
        } else {
            formatDate = yyMMddFormater.format(time) + "  " + toFormatTime(time);
            ;
        }
        return formatDate;
    }

    public static String formatTaskTime(long time) {
        String formatDate = "";
        long Today = getTody();
        long day = getday(time);
        Calendar.getInstance().setTime(new Date(time));

        if (day - Today == Day) {
            // 这是当前时间还要早的时间了,比如说明天
            return "明天";
        } else if (day - Today == 0) {
            return "今天";
        }
        if (day - getYearTime() >= 0) {
            formatDate = MMddFormater.format(time);
        } else {
            formatDate = yyMMddFormater.format(time);
        }

        return formatDate;
    }

    //格式： hh:mm(当天) 、 mm/dd(今年的)、 yy/mm/dd(今年之外)
    public static String formatHandleTime(long time, boolean isHHmmFromat) {
        String formatDate = "";
        long Today = getTody();
        long day = getday(time);
        Calendar.getInstance().setTime(new Date(time));


        if (Today - day < 0) {
            // 这是当前时间还要早的时间了,比如说明天
            //如果是不是当年的时间
            if (day - getYearTime() >= 0) {
                if (getYear(time) > Calendar.getInstance().get(Calendar.YEAR)) {
                    return yyMMddFormater.format(time);
                } else {
                    return MMddFormater.format(time);
                }
            } else {
                return yyMMddFormater.format(time);
            }

        }
//		long year = Calendar.getInstance().get(Calendar.YEAR);
        if (Today - day == 0) {
            if (isHHmmFromat)
                formatDate = toFormatTime(time);
            else
                formatDate = MMddFormater.format(time);
        } else if (day - getYearTime() >= 0) {
            formatDate = MMddFormater.format(time);
        } else {
            formatDate = yyMMddFormater.format(time);
        }
        return formatDate;
    }

    //格式： mm/dd(今年的)、 yy/mm/dd(今年之外)
    public static String formatMessageTime(long time) {
        String formatDate = "";
        long Today = getTody();
        long day = getday(time);
        Calendar.getInstance().setTime(new Date(time));
        if (day - getYearTime() >= 0) {
//			formatDate = new  SimpleDateFormat("MM月dd日",Locale.getDefault()).format(time);
            formatDate = subStringFristTime(mmFormater.format(time)) + "月" + subStringFristTime(ddFormater.format(time)) + "日";

        } else {
//			formatDate = new  SimpleDateFormat("yyyy年MM月dd日",Locale.getDefault()).format(time);
//			formatDate = yyMMddFormater.format(time);
            formatDate = subStringFristTime(yyFormater.format(time)) + "年" + subStringFristTime(mmFormater.format(time)) +
                    "月" + subStringFristTime(ddFormater.format(time)) + "日";
        }
        return formatDate;
    }


    /**
     * 把毫秒值 转换为MM-dd HH:mm格式
     *
     * @param date
     * @return
     */
    public static String toFormatItemDateTime(long date) {
        String formatDate = "";

        long Today = getTody();
        long day = getday(date);


        if (Today - day < 0) {
            // 这是当前时间还要早的时间了,比如说明天
            return MMddhhmmFormater.format(date);
        }
        if (Today - day == 0) {
            formatDate = "今天" + Space + hhmmFormater.format(date);
        } else if (Today - day == Day) {
            formatDate = "昨天" + Space + hhmmFormater.format(date);
        } else {
            formatDate = MMddhhmmFormater.format(date);
        }
        return formatDate;
    }

    /**
     * 转成格式化的日期
     *
     * @param date
     * @return
     */
    public static String toFormatMonthDate(long date) {
        String formatDate = "";

        long Today = getTody();
        long day = getday(date);

        if (Today - day < 0) {
            // 这是当前时间还要早的时间了,比如说明天
            return MMddFormater.format(date);
        }
        if (Today - day == 0) {
            formatDate = "今天";
            //formatDate = "今天" + Space + weekFm.format(date);
        } else if (Today - day == Day) {
            formatDate = "昨天";
            //formatDate = "昨天" + Space + weekFm.format(date);
        } else {
            formatDate = MMddFormater.format(date);
        }
        return formatDate;
    }

    /**
     * 默认返回周几
     *
     * @param timeStamp
     * @return
     */
    private static String getWeek(long timeStamp) {
        return getWeek(timeStamp, "周");
    }

    private static String getWeek(long timeStamp, String weekPrefix) {
        int mydate = 0;
        String week = null;
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date(timeStamp));
        mydate = cd.get(Calendar.DAY_OF_WEEK);
        // 获取指定日期转换成星期几
        if (mydate == 1) {
            week = weekPrefix + "日";
        } else if (mydate == 2) {
            week = weekPrefix + "一";
        } else if (mydate == 3) {
            week = weekPrefix + "二";
        } else if (mydate == 4) {
            week = weekPrefix + "三";
        } else if (mydate == 5) {
            week = weekPrefix + "四";
        } else if (mydate == 6) {
            week = weekPrefix + "五";
        } else if (mydate == 7) {
            week = weekPrefix + "六";
        }
        return week;
    }

    private static long getTody() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR, 0);
        todayStart.set(Calendar.AM_PM, Calendar.AM);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime().getTime();
    }


    private static int getYear(long time) {
        Calendar yearCd = Calendar.getInstance();
        yearCd.setTime(new Date(time));
        int year = yearCd.get(Calendar.YEAR);
        return year;
    }

    public static long getYearTime() {
        Calendar cal = Calendar.getInstance();
//		int year = cal.get(Calendar.YEAR);
//		cal.clear();
//		cal.set(Calendar.YEAR, year);
//		cal.set(Calendar.MONTH, 0);
//		cal.set(Calendar.DATE, 0);
//		cal.set(Calendar.HOUR, 0);
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.MILLISECOND, 0);

        cal.set(Calendar.DAY_OF_YEAR, 1);//本年第一天
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime();
    }

    public static long getTodayZeroTime() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static long getday(long time) {
        Calendar dateDay = Calendar.getInstance();
        dateDay.setTime(new Date(time));
        dateDay.set(Calendar.HOUR, 0);
        dateDay.set(Calendar.AM_PM, Calendar.AM);
        dateDay.set(Calendar.MINUTE, 0);
        dateDay.set(Calendar.SECOND, 0);
        dateDay.set(Calendar.MILLISECOND, 0);
        return dateDay.getTime().getTime();
    }


    public static long getdayby24(long time) {
        Calendar dateDay = Calendar.getInstance();
        dateDay.setTime(new Date(time));
        dateDay.set(Calendar.HOUR, 23);
        dateDay.set(Calendar.AM_PM, Calendar.AM);
        dateDay.set(Calendar.MINUTE, 59);
        dateDay.set(Calendar.SECOND, 59);
        dateDay.set(Calendar.MILLISECOND, 0);
        return dateDay.getTime().getTime();
    }

    public static long getTimeFormDate(int day) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -day);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    public static long getDayTimeFormMonth(int month) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.add(Calendar.MONTH, -month);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * @param time :如果获取系统时间 传0    如果传指定值直接传入
     */

    public static String getFormatDateTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm", Locale.getDefault());
        if (time == 0) {
            Calendar cal = Calendar.getInstance();
            return sdf.format(new Date()) + "  " + getWeek(cal.getTimeInMillis());
        } else {
            return sdf.format(time);
        }

    }

    /**
     * @param time :格式化类型  如yyyy-MM-dd  转化成毫秒值
     */

    public static long getTimeMillisecond(String time) {

        if ("".equals(time) || time == null) {
            return 0;
        }
        long mstime = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date;
        try {
            date = simpleDateFormat.parse(time);
            mstime = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mstime;

    }

    /**
     * @param time :格式化类型  如yyyy年MM月dd日  转化成毫秒值
     */

    public static String getFromatTIMTSTAMP(long time) {
        String mstime = "";
        if (time == 0)
            return mstime;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());

        try {
            mstime = simpleDateFormat.format(time);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mstime;

    }


    // long类型转换为String类型
    // strTime要转换的String类型的时间
    // formatType时间格式
    // strTime的时间格式和formatType的时间格式必须相同
    public static String longToString(long time, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType, Locale.getDefault());
        return formatter.format(time);
    }

//    // string类型转换为long类型
//    // strTime要转换的String类型的时间
//    // formatType时间格式
//    // strTime的时间格式和formatType的时间格式必须相同
//    public static long stringToLong(String strTime, String formatType)
//            throws ParseException {
//        Date date = stringToDate(strTime, formatType); // String类型转成date类型
//        if (date == null) {
//            return 0;
//        } else {
//            long currentTime = dateToLong(date); // date类型转成long类型
//            return currentTime;
//        }
//    }
//
// // string类型转换为date类型
//    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
//    // HH时mm分ss秒，
//    // strTime的时间格式必须要与formatType的时间格式相同
//    public static Date stringToDate(String strTime, String formatType)
//            throws ParseException {
//        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
//        Date date = null;
//        date = formatter.parse(strTime);
//        return date;
//    }

    // date类型转换为long类型
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

//	public static String getTimeStamp(String user_time) {
//		if(TextUtils.isEmpty(user_time) || user_time.equals("0")){
//			return "0";
//		}
//		String re_time = "";
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
//		Date d;
//		try {
//			if(user_time.length()>10){
//				d = sdf.parse(user_time);
//				long l = d.getTime();
//				String str = String.valueOf(l);
//				re_time = str.substring(0, 10);
//			}else{
//				re_time=user_time;
//			}
//
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return re_time;
//	}

    //  MM月   或  dd日  第一位是0截取
    private static String subStringFristTime(String time) {

        if (TextUtils.isEmpty(time)) {
            return "";
        } else {
            if ((time.charAt(0) + "").equals("0"))
                return time.substring(1, time.length());
            else
                return time;
        }
    }

    ;

}
