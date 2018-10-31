package com.todaysoft.lims.system.util;

public interface RedisKey
{
    
    String of(Object key);
    
    default String noneOf()
    {
        return of(null);
    }
    
}
