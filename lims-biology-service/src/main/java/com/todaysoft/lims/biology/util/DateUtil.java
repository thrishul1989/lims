package com.todaysoft.lims.biology.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    /**
     * 获得指定日期的前N天
     * @param
     * @return
     * @throws Exception
     */
    public static Date getSpecifiedDayBefore(Date date,int beforeDays){
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(date);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -beforeDays);  //设置为前一天
        return calendar.getTime();
    }
}
