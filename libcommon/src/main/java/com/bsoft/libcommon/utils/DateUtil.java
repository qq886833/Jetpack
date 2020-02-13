package com.bsoft.libcommon.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN1 = "yyyy年MM月dd日";
    public static final String PATTERN2 = "yyyy年MM月";
    public static final String PATTERN3 = "yyyy-MM-dd";
    public static final String PATTERN4 = "MM-dd";
    public static final String PATTERN5 = "yyyy.MM.dd";
    public static final String PATTERN6 = "yyyy.MM.dd HH:mm";
    public static final String PATTERN7 = "yyyy-MM-dd HH:mm";
    public static final String PATTERN8 = "HH:mm";


    public static final int SECOND = 1000;
    public static final int MINITE = SECOND * 60;
    public static final int HOUR = MINITE * 60;
    public static final int DAY = 24 * HOUR;

    /**
     * 根据format格式返回当前时间
     *
     * @param format
     * @return
     */
    public static String getCurTime(String format) {
        String dateString = null;
        try {
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            dateString = formatter.format(currentTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;

    }

    /**
     * 去掉时分秒，保留当天
     *
     * @return
     */
    public static Long getDayTime(Long time) {
        if (time == null) {
            return null;
        }
        try {
            String timeStr = getDateTime(PATTERN3, time);
            Date date = getDateTime(PATTERN3, timeStr);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回当前时间
     *
     * @return
     */
    public static long getCurTime() {
        return System.currentTimeMillis();

    }

    /**
     * @param date
     * @return
     */
    public static String formateDate(Long date) {
        if (date == null) {
            return "";
        }

        //Cur
        long curTime = System.currentTimeMillis();
        Calendar curCalendar = Calendar.getInstance();
        int curDay = curCalendar.get(Calendar.DAY_OF_YEAR);
        int curYear = curCalendar.get(Calendar.YEAR);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);

        long diffSecond = (curTime - date) / 1000L;

        if (diffSecond < 60) {
            return "刚刚";
        } else if (diffSecond >= 60 && diffSecond < 60 * 60) {
            return diffSecond / 60 + "分钟前";
        } else if (curYear == year && curDay == day) {
            return diffSecond / (60 * 60) + "小时前";
        } else if (curYear == year && curDay - day == 1) {
            return "昨天" + getDateTime("HH:mm", date);
        } else if (curYear == year && curDay - day == 2) {
            return "前天" + getDateTime("HH:mm", date);
        } else if (curYear == year) {
            return getDateTime("MM-dd", date);
        } else {
            return getDateTime("yyyy-MM-dd", date);
        }
    }

    /**
     * @param strDate
     * @param oldFormat
     * @param newFormat
     * @return
     */
    public static String formatDateStr(String strDate, String oldFormat, String newFormat) {
        if (strDate == null || strDate.equals("")) {
            return null;
        }
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(oldFormat, Locale.getDefault());
        try {
            date = sdf.parse(strDate);
            sdf.applyPattern(newFormat);
        } catch (Exception ex) {
            date = null;
            return strDate;
        }
        return sdf.format(date);

    }

    public static int getAge(Long birthday) {
        if (birthday == null) {
            return 0;
        }
        Date birth = new Date(birthday + Calendar.getInstance().get(Calendar.ZONE_OFFSET));
        Date now = new Date();
        //不到月份-1
        int offset = 0;

        if(now.getMonth() < birth.getMonth()){
            offset = -1;
        }else if (now.getMonth() == birth.getMonth()){
            if (now.getDate() < birth.getDate()){
                offset = -1;
            }
        }

        return now.getYear() - birth.getYear() + offset;
    }

    public static String getDateTime(String format, Long d) {
        if (d == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(d));
    }


    public static Date getDateTime(String formate, String d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(formate);
            return sdf.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Calendar getDateTime(Long date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        return calendar;
    }


    private static String[] weeks = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    /**
     * 获取日期的 周几
     *
     * @param date
     * @return
     */
    public static String getWeek(Long date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);//设置sunday是一周的第一天
        int week = calendar.get(Calendar.DAY_OF_WEEK);//按sunday为1 saturday为7
        return weeks[week - 1];

    }

    private static String[] weeks2 = new String[]{"日", "一", "二", "三", "四", "五", "六"};

    /**
     * 获取日期的 周几
     *
     * @param date
     * @return
     */
    public static String getWeek2(Long date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);//设置sunday是一周的第一天
        int week = calendar.get(Calendar.DAY_OF_WEEK);//按sunday为1 saturday为7
        return weeks2[week - 1];

    }

    private static String[] weeks3 = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    /**
     * 获取日期的 周几
     *
     * @param date
     * @return
     */
    public static String getWeek3(Long date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);//设置sunday是一周的第一天
        int week = calendar.get(Calendar.DAY_OF_WEEK);//按sunday为1 saturday为7
        return weeks3[week - 1];

    }

    /**
     * 是否同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Long date1, Long date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTimeInMillis(date1);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTimeInMillis(date2);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB
                .get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 通过差值换算成时分秒
     * xx:xx:xx
     *
     * @param diffSecond
     * @return
     */
    public static String getDiffInSecond(Long diffSecond) {
        if (diffSecond == null) {
            return null;
        }
        int hour = (int) (diffSecond / HOUR);
        diffSecond = diffSecond % HOUR;

        int minite = (int) (diffSecond / MINITE);
        diffSecond = diffSecond % MINITE;

        int second = (int) (diffSecond / SECOND);

        return hour + ":" + minite + ":" + second;
    }


}

