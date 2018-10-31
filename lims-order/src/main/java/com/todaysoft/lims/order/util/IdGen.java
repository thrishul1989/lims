/**
 * Copyright &copy; 2012-2015 <a href="http://www.bmwm.cn">SunrySoft</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.todaysoft.lims.order.util;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * @author SunrySoft
 * @version 2013-01-15
 */

public class IdGen
{
    
    private static SecureRandom random = new SecureRandom();
    
    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
     * 使用SecureRandom随机生成Long. 
     */
    public static long randomLong()
    {
        return Math.abs(random.nextLong());
    }
    
    /**
     * Activiti ID 生成
     */
    
    public String getNextId()
    {
        return IdGen.uuid();
    }
    
}
