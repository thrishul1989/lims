package com.todaysoft.lims.report.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    
    public static Double getDaysOfTwoDate(Date before, Date after)
    {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        long distance = afterTime - beforeTime;
        BigDecimal b1 = new BigDecimal(distance);
        BigDecimal b2 = new BigDecimal(1000 * 60 * 60 * 24);
        int i = 1;
        BigDecimal result = b1.divide(b2, i, RoundingMode.HALF_UP);
        BigDecimal resultZero = new BigDecimal(0);
        boolean flag = true;
        while (flag)
        {
            if (resultZero.compareTo(result) == 0)
            {
                if (resultZero.compareTo(b1) == 0)
                {
                    return result.doubleValue();
                }
                else
                {
                    i++;
                    result = b1.divide(b2, i, RoundingMode.HALF_UP);
                }
            }
            else
            {
                flag = false;
            }
        }
        return result.doubleValue();
    }
    
    public static Double getDivByTwoDouble(Double before, Double after)
    {
        BigDecimal b1 = new BigDecimal(before);
        BigDecimal b2 = new BigDecimal(after);
        int i = 2;
        BigDecimal result = b1.divide(b2, i, RoundingMode.HALF_UP);
        BigDecimal resultZero = new BigDecimal(0);
        boolean flag = true;
        while (flag)
        {
            if (resultZero.compareTo(result) == 0)
            {
                if (resultZero.compareTo(b1) == 0)
                {
                    return result.doubleValue();
                }
                else
                {
                    i++;
                    result = b1.divide(b2, i, RoundingMode.HALF_UP);
                }
            }
            else
            {
                flag = false;
            }
        }
        return result.doubleValue();
    }
    
    public static Double getFormatDouble(Double risk)
    {
        
        BigDecimal b = new BigDecimal(risk);
        BigDecimal result = new BigDecimal(0);
        int i = 1;
        if (result.compareTo(b) == 0)
        {
            return risk.doubleValue();
        }
        while (true)
        {
            result = b.setScale(i, BigDecimal.ROUND_HALF_UP);
            if (result.compareTo(new BigDecimal(0)) != 0)
            {
                break;
            }
            else
            {
                i++;
            }
        }
        return result.doubleValue();
    }
    
    public static Date plusDay(Date createDate, int day)
    {
        Date rdate = null;
        try
        {
            Calendar cl = Calendar.getInstance();
            cl.setTime(createDate);
            cl.add(Calendar.DATE, day);
            rdate = cl.getTime();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return rdate;
    }
}
