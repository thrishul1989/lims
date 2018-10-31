package com.todaysoft.lims.sample.util;

import java.util.Random;

import com.todaysoft.lims.utils.DateUtils;

public class CodeUtils
{
    
    /** 
    * 生成code：当前年月日时分秒+五位随机数 
    *  
    * @return 
    */
    public static String getSampleReceiveCode()
    {
        
        String str = DateUtils.getDate("yyyyMMdd");
        
        Random random = new Random();
        
        int rannum = (int)(random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数  
        
        return "SR" + str + rannum;// 当前时间  
    }
    
    public static String getSampleOrderCode()
    {
        
        String str = DateUtils.getDate("yyyyMMdd");
        
        Random random = new Random();
        
        int rannum = (int)(random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数  
        
        return "OR" + str + rannum;// 当前时间  
    }
    
    public static String getDNAOrRNAExtractCode(String semantic)
    {
        
        String str = DateUtils.getDate("yyyyMMdd");
        
        Random random = new Random();
        
        int rannum = (int)(random.nextDouble() * (999 - 100 + 1)) + 100;// 获取3位随机数  
        
        return semantic + str + rannum;// 当前时间
    }
    
    public static String getDNAQCCode()
    {
        
        String str = DateUtils.getDate("yyyyMMdd");
        
        Random random = new Random();
        
        int rannum = (int)(random.nextDouble() * (999 - 100 + 1)) + 100;// 获取3位随机数  
        
        return "QC" + str + rannum;// 当前时间  
    }
    
    public static String getTargetSampleCode()
    {
        
        String str = DateUtils.getDate("yyyyMMdd");
        
        Random random = new Random();
        
        int rannum = random.nextInt(90) + 10;// 获取2位随机数  
        
        return "TS" + str + rannum;
    }
    
    public static String getTargetlibraryCode()
    {
        
        String str = DateUtils.getDate("yyyyMMdd");
        
        Random random = new Random();
        
        int rannum = random.nextInt(90) + 10;// 获取2位随机数  
        
        return "LDC" + str + rannum;
    }
    
    public static String getTaskCode()
    {
        
        String str = DateUtils.getDate("yyyyMMdd");
        
        Random random = new Random();
        
        int rannum = random.nextInt(90) + 10;// 获取2位随机数  
        
        return "JK" + str + rannum;
    }
    
    public static String getSampleReceivingCode()
    {
        
        String str = DateUtils.getDate("yyyyMMdd");
        
        Random random = new Random();
        
        int rannum = (int)(random.nextDouble() * (999 - 100 + 1)) + 100;// 获取3位随机数  
        
        return "入库" + str + rannum;// 当前时间  
    }
    
    public static String getDefaultCode(String prefix)
    {
        
        String str = DateUtils.getDate("yyyyMMdd");
        
        Random random = new Random();
        
        int rannum = (int)(random.nextDouble() * (999 - 100 + 1)) + 100;// 获取3位随机数  
        
        return prefix + str + rannum;// 当前时间  
    }
    
    public static String getContractSettleBillCode()
    {
        
        String str = DateUtils.getDate("yyyyMMddHHmmss");
        return str;// 当前时间  
    }
    
}
