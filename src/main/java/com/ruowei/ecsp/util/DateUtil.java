package com.ruowei.ecsp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateUtil extends org.apache.commons.lang3.time.DateUtils {

    /**
     * 获取某月有几天
     *
     * @param year  年份
     * @param month 月分
     * @return 天数
     */
    public static int getMonthHasDays(String year, String month) {
        String day31 = ",01,03,05,07,08,10,12,";
        String day30 = "04,06,09,11";
        int day = 0;
        if (day31.contains(month)) {
            day = 31;
        } else if (day30.contains(month)) {
            day = 30;
        } else {
            int y = Integer.parseInt(year);
            if ((y % 4 == 0 && (y % 100 != 0)) || y % 400 == 0) {
                day = 29;
            } else {
                day = 28;
            }
        }
        return day;
    }

    /**
     * @param date start date
     * @return start to now, not include start
     * @apiNote author: czz;
     */
    public static List<String> getDateStrList_In(LocalDate date) {
        List<String> dateList = new ArrayList<>();
        while (date.isBefore(LocalDate.now())) {
            date = date.plusDays(1);
            dateList.add(defaultFormat(date));
        }
        return dateList;
    }

    /**
     * @param year 年份start
     * @return start to end, include start and end
     * @apiNote author: czz;
     */
    public static List<String> getYearStrList_Both(int year) {
        List<String> yearList = new ArrayList<>();
        for (int i = year; i <= LocalDate.now().getYear(); i++) {
            yearList.add(String.valueOf(i));
        }
        return yearList;
    }

    /**
     * 将String时间格式为: [yyyy-MM-dd HH:mm:ss]转为Instant
     *
     * @param str string of time
     * @return instant time
     * @author czz
     */
    public static Instant stringToInstant(String str) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(str, df);
        ZoneId zone = ZoneId.systemDefault();
        return localDateTime.atZone(zone).toInstant();
    }

    public static List<String> getEachYearAndMonth(String startTime, String endTime) {
        List<String> dateList = new ArrayList<>();
        dateList.add(startTime);
        startTime = startTime.concat("-01 00:00:00");
        endTime = endTime.concat("-01 00:00:00");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat easySdf = new SimpleDateFormat("yyyy-MM");
        try {
            calendar.setTime(sdf.parse(startTime));
            while (true) {
                calendar.add(Calendar.MONTH, 1);
                if (sdf.parse(endTime).after(calendar.getTime())) {
                    dateList.add(easySdf.format(calendar.getTime()));
                } else {
                    break;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!startTime.equals(endTime)) {
            dateList.add(endTime.substring(0, 7));
        }
        return dateList;
    }

    /**
     * @param instant 时间
     * @param pattern 格式Str
     * @return Instant 转 String
     * @apiNote author: czz;
     */
    public static String instantToString(Instant instant, String pattern) {
        if (instant == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault());
        return formatter.format(instant);
    }

    /**
     * @param date 日期
     * @return default: yyyy-MM-dd
     * @apiNote author: czz; 日期格式化为yyyy-MM-dd
     */
    public static String defaultFormat(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String format(LocalDate localDate, String patternStr) {
        return localDate.format(DateTimeFormatter.ofPattern(patternStr));
    }

    /**
     * @param date
     * @return
     * @apiNote author: czz; trans LocalDate to string of DateTimeFormatter: yyyy年M月d日
     */
    public static String formatDateToStr(LocalDate date) {
        return DateTimeFormatter.ofPattern("yyyy年M月d日").format(date);
    }

    /**
     * @param date   projectStartDate
     * @param addNum index year of project
     * @return string value of one year's period
     * @apiNote author: czz; carbonSinkResult's year period, like: 2005年6月30日-2006年6月29日
     */
    public static String formatYearPeriod(LocalDate date, int addNum) {
        LocalDate dateStart = date.plusYears(addNum - 1);
        LocalDate dateEnd = date.plusYears(addNum).minusDays(1);
        return formatDateToStr(dateStart) + "-" + formatDateToStr(dateEnd);
    }

    public static String resetDataStr(String str) {
        str = str.replaceAll("-", "");
        str = str.replaceAll("/", "");
        return str;
    }

    public static String getDateSeparator(String dateStr) {
        if (dateStr.contains("-")) {
            return "-";
        } else if (dateStr.contains("/")) {
            return "/";
        }
        return "";
    }

    /**
     * @param str 日期字符串
     * @return 转为LocalDate
     * @apiNote author: czz;  形如【yyyy-MM-dd, yyyy-M-d, yyyy/MM/dd, yyyy/M/d, yyyyMMdd, yyyyMd】
     */
    public static LocalDate extractTradeDate(String str) {
        /**
         * yyyy-MM-dd
         * yyyy-M-d
         * yyyy/MM/dd
         * yyyy/M/d
         * yyyyMMdd
         * yyyyMd
         */
        int length = str.length();
        String prefix = "yyyy";
        String middle = "MM";
        String suffix = "dd";
        String separator = getDateSeparator(str);
        if ((separator.equals("") && length < 8) || (length < 10 && !separator.equals(""))) {
            middle = "M";
            suffix = "d";
        }
        String pattern = prefix + separator + middle + separator + suffix;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(str, formatter);
    }

    public static String extractDateType(String dateStr) {
        /**
         * yyyy-MM-dd
         * yyyy-MM
         * yyyy
         */
        String type;
        int length = dateStr.length();
        if (length > 7) {
            type = "DAY";
        } else if (length > 4) {
            type = "MONTH";
        } else {
            type = "YEAR";
        }
        return type;
    }
}
