package com.todaysoft.lims.system.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.time.DateFormatUtils;

public abstract class DateUtil
{
    public static String format(Date date, String pattern)
    {
        if (date == null)
        {
            return "";
        }
        return new SimpleDateFormat(pattern).format(date);
    }
    
    public static Date toStartDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
    
    public static Date toEndDate(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }
    
    public static int getInterval(Date base, Date date)
    {
        base = toStartDate(base);
        date = toStartDate(date);
        GregorianCalendar baseCalendar = new GregorianCalendar();
        GregorianCalendar calendar = new GregorianCalendar();
        baseCalendar.setTime(base);
        calendar.setTime(date);
        return (int)((calendar.getTimeInMillis() - baseCalendar.getTimeInMillis()) / (1000 * 3600 * 24));
    }
    
    public static String getOneMonthBefore(Date base, String pattern)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        return formatDate(m, pattern);
    }
    
    public static String formatDate(Date date, Object... pattern)
    {
        String formatDate = null;
        if (pattern != null && pattern.length > 0)
        {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        }
        else
        {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }
}
