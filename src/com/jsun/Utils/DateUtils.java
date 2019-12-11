package com.jsun.Utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author guochunyuan
 * @create on  2018-09-03 14:13
 */
public class DateUtils {
    public static final String COMPACT_DATE_FORMAT = "yyyyMMdd";
    public static final String YM = "yyyyMM";
    public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";
    public static final String NORMAL_DATE_FORMAT_CH = "yyyy年MM月dd日";
    public static final String NORMAL_DATE_FORMAT_NEW = "yyyy-mm-dd hh24:mi:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_FORMAT_CH = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String DATETIME_FORMAT2 = "yyyyMMddHHmmss";//(0-24)
    public static final String DATETIME_FORMAT_12 = "yyyyMMddhhmmss";//(1-12)
    public static final String DATE_ALL = "yyyyMMddHHmmssS";
    private static final SimpleDateFormat DEFAULT_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DateUtils() {
    }

    public static String format(Date date) {
        return DEFAULT_DATEFORMAT.format(date);
    }

    public static String format(Date date, String format) {
        if (date == null) {
            date = new Date();
        }

        if (format == null) {
            return format(date);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        }
    }

    public static Long strDateToNum(String paramString) throws Exception {
        if (paramString == null) {
            return null;
        } else {
            String[] arrayOfString = null;
            String str = "";
            if (!paramString.contains("-")) {
                return Long.parseLong(paramString);
            } else {
                arrayOfString = paramString.split("-");
                String[] arr$ = arrayOfString;
                int len$ = arrayOfString.length;
                for(int i$ = 0; i$ < len$; ++i$) {
                    String anArrayOfString = arr$[i$];
                    str = str + anArrayOfString;
                }

                return Long.parseLong(str);
            }
        }
    }

    public static Long strDateToNum1(String paramString) throws Exception {
        if (paramString == null) {
            return null;
        } else {
            String[] arrayOfString = null;
            String str = "";
            if (paramString.contains("-")) {
                arrayOfString = paramString.split("-");
                String[] arr$ = arrayOfString;
                int len$ = arrayOfString.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    String anArrayOfString = arr$[i$];
                    if (anArrayOfString.length() == 1) {
                        str = str + "0" + anArrayOfString;
                    } else {
                        str = str + anArrayOfString;
                    }
                }
                return Long.parseLong(str);
            } else {
                return Long.parseLong(paramString);
            }
        }
    }

    public static String numDateToStr(Long paramLong) {
        if (paramLong == null) {
            return null;
        } else {
            String str = null;
            str = paramLong.toString().substring(0, 4) + "-" + paramLong.toString().substring(4, 6) + "-" + paramLong.toString().substring(6, 8);
            return str;
        }
    }

    public static Long sysDateToNum() throws Exception {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        localSimpleDateFormat.setLenient(false);
        return strDateToNum(localSimpleDateFormat.format(new Date()));
    }

    public static Date stringToDate(String paramString1, String paramString2) throws Exception {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString2);
        localSimpleDateFormat.setLenient(false);
        try {
            return localSimpleDateFormat.parse(paramString1);
        } catch (ParseException var4) {
            throw new Exception("解析日期字符串时出错！");
        }
    }

    public static String dateToString(Date paramDate, String paramString) {
        if (null == paramDate) {
            paramDate = new Date();
        }
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
        localSimpleDateFormat.setLenient(false);
        return localSimpleDateFormat.format(paramDate);
    }

    public static Date compactStringToDate(String paramString) throws Exception {
        return stringToDate(paramString, "yyyyMMdd");
    }

    public static String dateToCompactString(Date paramDate) {
        return dateToString(paramDate, "yyyyMMdd");
    }

    public static String dateToNormalString(Date paramDate) {
        return dateToString(paramDate, "yyyy-MM-dd");
    }

    public static String compactStringDateToNormal(String paramString) throws Exception {
        return dateToNormalString(compactStringToDate(paramString));
    }

    public static int getDaysBetween(Date paramDate1, Date paramDate2) throws Exception {
        Calendar localCalendar1 = Calendar.getInstance();
        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar1.setTime(paramDate1);
        localCalendar2.setTime(paramDate2);
        if (localCalendar1.after(localCalendar2)) {
            throw new Exception("起始日期小于终止日期!");
        } else {
            int i = localCalendar2.get(6) - localCalendar1.get(6);
            int j = localCalendar2.get(1);
            if (localCalendar1.get(1) != j) {
                localCalendar1 = (Calendar)localCalendar1.clone();

                do {
                    i += localCalendar1.getActualMaximum(6);
                    localCalendar1.add(1, 1);
                } while(localCalendar1.get(1) != j);
            }

            return i;
        }
    }

    public static Date addMinutes(Date paramDate, int paramInt) throws Exception {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        int i = localCalendar.get(12);
        localCalendar.set(12, i + paramInt);
        return localCalendar.getTime();
    }

    public static Date addDays(Date paramDate, int paramInt) throws Exception {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        int i = localCalendar.get(6);
        localCalendar.set(6, i + paramInt);
        return localCalendar.getTime();
    }

    public static Date addDays(String paramString1, String paramString2, int paramInt) throws Exception {
        Calendar localCalendar = Calendar.getInstance();
        Date localDate = stringToDate(paramString1, paramString2);
        localCalendar.setTime(localDate);
        int i = localCalendar.get(6);
        localCalendar.set(6, i + paramInt);
        return localCalendar.getTime();
    }

    public static java.sql.Date getSqlDate(Date paramDate) throws Exception {
        java.sql.Date localDate = new java.sql.Date(paramDate.getTime());
        return localDate;
    }

    public static Date getPreDaysDate(Date date, int number) {
        if (date == null) {
            date = new Date();
        }

        long predaysTime = date.getTime() - 1000L * (long)number * 24L * 3600L;
        return new Date(predaysTime);
    }

    public static Date getPreDayDate(Date date) {
        return getPreDaysDate(date, 1);
    }

    public static Date getNextDaysDate(Date date, int number) {
        return getPreDaysDate(date, -number);
    }

    public static String formatDate(Date paramDate, String paramString) {
        if (null == paramDate) {
            paramDate = new Date();
        }

        if (null == paramString || paramString.trim().length() == 0) {
            paramString = "yyyy-MM-dd";
        }
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
        localSimpleDateFormat.setLenient(false);
        return localSimpleDateFormat.format(paramDate);
    }

    public static String formatDate(Date paramDate) {
        if (paramDate == null) {
            return null;
        } else {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            localSimpleDateFormat.setLenient(false);
            return localSimpleDateFormat.format(paramDate);
        }
    }

    public static String formatDateTime(Date paramDate) {
        if (paramDate == null) {
            return null;
        } else {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            localSimpleDateFormat.setLenient(false);
            return localSimpleDateFormat.format(paramDate);
        }
    }

    public static Date parseDate(String paramString) throws Exception {
        if (paramString != null && !paramString.trim().equals("")) {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            localSimpleDateFormat.setLenient(false);
            try {
                return localSimpleDateFormat.parse(paramString);
            } catch (ParseException var3) {
                throw new Exception("日期解析出错！", var3);
            }
        } else {
            return null;
        }
    }

    public static Date parseDateTime(String paramString) throws Exception {
        if (paramString != null && !paramString.trim().equals("")) {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            localSimpleDateFormat.setLenient(false);
            try {
                return localSimpleDateFormat.parse(paramString);
            } catch (ParseException var3) {
                throw new Exception("时间解析异常！", var3);
            }
        } else {
            return null;
        }
    }

    public static Integer getYM(String paramString) throws Exception {
        if (paramString == null) {
            return null;
        } else {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            localSimpleDateFormat.setLenient(false);

            Date localDate;
            try {
                localDate = localSimpleDateFormat.parse(paramString);
            } catch (ParseException var4) {
                throw new Exception("时间解析异常！", var4);
            }
            return getYM(localDate);
        }
    }

    public static Integer getYM(Date paramDate) {
        if (paramDate == null) {
            return null;
        } else {
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTime(paramDate);
            int i = localCalendar.get(1);
            int j = localCalendar.get(2) + 1;
            return i * 100 + j;
        }
    }

    public static int addMonths(int paramInt1, int paramInt2) {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.set(1, paramInt1 / 100);
        localCalendar.set(2, paramInt1 % 100 - 1);
        localCalendar.set(5, 1);
        localCalendar.add(2, paramInt2);
        return getYM(localCalendar.getTime());
    }

    public static Date addMonths(Date paramDate, int paramInt) {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        localCalendar.add(2, paramInt);
        return localCalendar.getTime();
    }

    public static int monthsBetween(int paramInt1, int paramInt2) {
        int i = paramInt2 / 100 * 12 + paramInt2 % 100 - (paramInt1 / 100 * 12 + paramInt1 % 100);
        return i;
    }

    public static int monthsBetween(Date paramDate1, Date paramDate2) {
        return monthsBetween(getYM(paramDate1), getYM(paramDate2));
    }

    public static String getChineseDate(Calendar paramCalendar) {
        int i = paramCalendar.get(1);
        int j = paramCalendar.get(2);
        int k = paramCalendar.get(5);
        return String.valueOf(i) + "年" + (j + 1) + "月" + k + "日";
    }

    public static String getChineseWeekday(Calendar paramCalendar) {
        switch(paramCalendar.get(7)) {
            case 1:
                return "星期日";
            case 2:
                return "星期一";
            case 3:
                return "星期二";
            case 4:
                return "星期三";
            case 5:
                return "星期四";
            case 6:
                return "星期五";
            case 7:
                return "星期六";
            default:
                return "未知";
        }
    }

    public static String getWeekDayByDateStr(String dateStr) {
        try {
            Date date = parseDate(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int weekDay = calendar.get(7);
            switch(weekDay) {
                case 1:
                    return "周日";
                case 2:
                    return "周一";
                case 3:
                    return "周二";
                case 4:
                    return "周三";
                case 5:
                    return "周四";
                case 6:
                    return "周五";
                case 7:
                    return "周六";
            }
        } catch (Exception ignored) {

        }
        return  null;
    }

    /** @deprecated */
    @Deprecated
    public static String getLastDay() {
        return getLastDay((Date)null, (String)null);
    }

    /** @deprecated */
    @Deprecated
    public static String getLastDayStr() {
        return getLastDay((Date)null, "yyyyMMddHHmmss");
    }

    public static String getLastDay(Date date, String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
            calendar.setTime(date);
        }
        calendar.add(2, 1);
        calendar.set(5, 1);
        calendar.add(5, -1);
        calendar.set(11, 23);
        calendar.set(12, 59);
        calendar.set(13, 59);
        return df.format(calendar.getTime());
    }

    /** @deprecated */
    @Deprecated
    public static String getFirstDay() {
        return getFirstDay((Date)null, (String)null);
    }

    /** @deprecated */
    @Deprecated
    public static String getFirstDayStr() {
        return getFirstDay((Date)null, "yyyyMMddHHmmss");
    }

    public static String getFirstDay(Date date, String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
            calendar.setTime(date);
        }
        calendar.set(5, 1);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        return df.format(calendar.getTime());
    }

}
