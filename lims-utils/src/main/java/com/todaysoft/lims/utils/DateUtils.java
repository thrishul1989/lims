/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.todaysoft.lims.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.google.common.collect.Lists;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    
    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss",
        "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};
    
    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate()
    {
        return getDate("yyyy-MM-dd");
    }
    
    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern)
    {
        return DateFormatUtils.format(new Date(), pattern);
    }
    
    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern)
    {
        String formatDate = "";
        if (StringUtils.isNotEmpty(date))
        {
            
            if (pattern != null && pattern.length > 0)
            {
                formatDate = DateFormatUtils.format(date, pattern[0].toString());
            }
            else
            {
                formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
            }
        }
        
        return formatDate;
    }
    
    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date)
    {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime()
    {
        return formatDate(new Date(), "HH:mm:ss");
    }
    
    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime()
    {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear()
    {
        return formatDate(new Date(), "yyyy");
    }
    
    public static String getYear(Date date)
    {
        return formatDate(date, "yyyy");
    }
    
    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth()
    {
        return formatDate(new Date(), "MM");
    }
    
    public static String getMonth(Date date)
    {
        return formatDate(date, "MM");
    }
    
    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay()
    {
        return formatDate(new Date(), "dd");
    }
    
    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek()
    {
        return formatDate(new Date(), "E");
    }
    
    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
     *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str)
    {
        
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
    
    /**
     * 获取过去的天数
     * @param date
     * @return
     */
    public static long pastDays(Date date)
    {
        long t = new Date().getTime() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }
    
    /**
     * 获取过去的小时
     * @param date
     * @return
     */
    public static long pastHour(Date date)
    {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 60 * 1000);
    }
    
    /**
     * 获取过去的分钟
     * @param date
     * @return
     */
    public static long pastMinutes(Date date)
    {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000);
    }
    
    /**
     * 获取过去的秒
     * @param date
     * @return
     */
    public static long pastSeconds(Date date)
    {
        long t = new Date().getTime() - date.getTime();
        return t / (60 * 1000 * 60);
    }
    
    /**
     * 获取过去的秒
     * @param date
     * @return
     */
    public static long pastMills(Date date)
    {
        return pastSeconds(date) * 1000;
    }
    
    /**
     * 获取后面的日期跟现在的时间的毫秒数
     * @param date
     * @return
     */
    public static long getMills(Date date)
    {
        long t = date.getTime() - new Date().getTime();
        return t;
    }
    
    /**
     * 转换为时间（天,时:分:秒.毫秒）
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis)
    {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }
    
    /**
     * 获取两个日期之间的天数
     * 
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after)
    {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }
    
    public static Date getDateStart(Date date)
    {
        if (date == null)
        {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 00:00:00");
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }
    
    public static Date getDateEnd(Date date)
    {
        if (date == null)
        {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 23:59:59");
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }
    
    // long转为Date
    public static Date transferLongToDate(Long millSec)
    {
        Date date = new Date(millSec);
        return date;
    }
    
    /**
     * 得到指定月的天数
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDayNum(int year, int month)
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
    
    /**
     * 得到指定月的所有日期
     * @return
     */
    public static List<Date> getMonthDays(Date date)
    {
        boolean isNowDate = false;
        if (null == date)
        {
            date = new Date();
            isNowDate = true;
        }
        List<Date> list = Lists.newArrayList();
        int days = getMonthDayNum(Integer.parseInt(getYear(date)), Integer.parseInt(getMonth(date)));
        Calendar a = Calendar.getInstance();
        a.setTime(date);
        if (isNowDate)
        {
            days = a.get(Calendar.DAY_OF_MONTH);
        }
        for (int i = 0; i < days; i++)
        {
            a.set(Calendar.DATE, i + 1);
            list.add(a.getTime());
        }
        return list;
    }
    
    public static String nowDate()
    {
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH:mm");
        
        return df.format(new Date());
    }
    
    public static boolean isSameDay(Date day1, Date day2)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ds1 = sdf.format(day1);
        String ds2 = sdf.format(day2);
        if (ds1.equals(ds2))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public static boolean isSameMonth(Date day1, Date day2)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String ds1 = sdf.format(day1);
        String ds2 = sdf.format(day2);
        if (ds1.equals(ds2))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public static Date getFirstDay(Date date)
    {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = ca.getTime();
        return firstDate;
    }
    
    public static Date getEndDay(Date date)
    {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date); //  someDate 为你要获取的那个月的时间
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDate = ca.getTime();
        return lastDate;
    }
    
    public static List<Date> getBetweenDates(Date date)
    {
        Calendar p_start = Calendar.getInstance();
        Calendar p_end = Calendar.getInstance();
        p_start.setTime(DateUtils.getFirstDay(date));
        p_end.setTime(DateUtils.getEndDay(date));
        
        List<Date> result = new ArrayList<Date>();
        result.add(p_start.getTime());
        //Calendar temp = p_start.getInstance();
        p_start.add(Calendar.DAY_OF_YEAR, 1);
        while (p_start.before(p_end))
        {
            result.add(p_start.getTime());
            p_start.add(Calendar.DAY_OF_YEAR, 1);
        }
        result.add(p_end.getTime());
        return result;
    }
    
    public static List<Date> getBetweenDates(Date startDate, Date endDate)
    {
        Calendar p_start = Calendar.getInstance();
        Calendar p_end = Calendar.getInstance();
        p_start.setTime(startDate);
        p_end.setTime(endDate);
        
        List<Date> result = new ArrayList<Date>();
        result.add(p_start.getTime());
        p_start.add(Calendar.DAY_OF_YEAR, 1);
        while (p_start.before(p_end))
        {
            result.add(p_start.getTime());
            p_start.add(Calendar.DAY_OF_YEAR, 1);
        }
        result.add(p_end.getTime());
        return result;
    }
    
    public static Date getStartDayOfWeek(String date)
    {
        LocalDate now = LocalDate.parse(date);
        return getStartDayOfWeek(now);
    }
    
    public static Date getStartDayOfWeek()
    {
        LocalDate now = LocalDate.now();
        return getStartDayOfWeek(now);
    }
    
    public static Date getStartDayOfWeek(TemporalAccessor date)
    {
        TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
        LocalDate localDate = LocalDate.from(date);
        localDate = localDate.with(fieldISO, 1);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).plusDays(2L).minusNanos(2L).toInstant());
    }
    
    //获取周最后一天
    public static Date getEndDayOfWeek(String date)
    {
        LocalDate localDate = LocalDate.parse(date);
        return getEndDayOfWeek(localDate);
    }
    
    //获取周最后一天
    public static Date getEndDayOfWeek()
    {
        LocalDate localDate = LocalDate.now();
        return getEndDayOfWeek(localDate);
    }
    
    public static Date getEndDayOfWeek(TemporalAccessor date)
    {
        TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
        LocalDate localDate = LocalDate.from(date);
        localDate = localDate.with(fieldISO, 7);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).plusDays(2L).minusNanos(2L).toInstant());
    }
    
    //一天的开始
    public static Date getStartOfDay(String date)
    {
        LocalDate localDate = LocalDate.parse(date);
        return getStartOfDay(localDate);
    }
    
    public static Date getStartOfDay(TemporalAccessor date)
    {
        LocalDate localDate = LocalDate.from(date);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    //一天的结束
    public static Date getEndOfDay(String date)
    {
        LocalDate localDate = LocalDate.parse(date);
        return getEndOfDay(localDate);
    }
    
    public static Date getEndOfDay(TemporalAccessor date)
    {
        LocalDate localDate = LocalDate.from(date);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).plusDays(1L).minusNanos(1L).toInstant());
    }
    
    //验证时间格式是否正确并符合yy-mm-dd
    public static boolean isValidDate(String str)
    {
        //String str = "2007-01-02";  
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date date = formatter.parse(str);
            return str.equals(formatter.format(date));
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
