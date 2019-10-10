/*
 * Decompiled with CFR 0.145.
 */
package com.baidu.tts.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTool {
    public static String simpleFormatCurrentDate() {
        return DateTool.formatCurrentDate("yyyy\u5e74M\u6708d\u65e5 HH:mm:ss:SSS");
    }

    public static String formatCurrentDate(String template) {
        return DateTool.format(new Date(), template);
    }

    public static String formatInChinaDate(long millisecond) {
        return DateTool.format(millisecond, "yyyy\u5e74M\u6708d\u65e5");
    }

    public static String formatInyyyyMMdd(long millisecond) {
        return DateTool.format(millisecond, "yyyy.MM.dd");
    }

    public static String formatInHHmm(long millisecond) {
        return DateTool.format(millisecond, "HH:mm");
    }

    public static String format(long millisecond, String template) {
        Date date = new Date(millisecond);
        return DateTool.format(date, template);
    }

    public static String format(Date date, String template) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(template, Locale.CHINA);
        return simpleDateFormat.format(date);
    }

    public static String format(Calendar calendar, String template) {
        try {
            Date date = calendar.getTime();
            return DateTool.format(date, template);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static String format(String src, String srcTemplate, String desTemplate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(srcTemplate, Locale.CHINA);
            Date date = simpleDateFormat.parse(src);
            return DateTool.format(date, desTemplate);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static Calendar getCalendar(String date, String template) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(template, Locale.CHINA);
            Date date2 = simpleDateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date2);
            return calendar;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static String[] getDateRange(String date, String template, int range) {
        Calendar calendar = DateTool.getCalendar(date, template);
        Date date2 = calendar.getTime();
        String[] arrstring = new String[range];
        for (int i2 = 0; i2 < range; ++i2) {
            calendar.add(5, -(range - i2 - 1));
            calendar.getTime();
            arrstring[i2] = String.valueOf(calendar.get(5));
            calendar.setTime(date2);
        }
        return arrstring;
    }

    public static Date getDate(String date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.CHINA);
        try {
            return simpleDateFormat.parse(date);
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
            return null;
        }
    }
}

