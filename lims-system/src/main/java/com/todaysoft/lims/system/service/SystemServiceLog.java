package com.todaysoft.lims.system.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})      
@Retention(RetentionPolicy.RUNTIME)      
@Documented
public @interface SystemServiceLog
{
    /** 
    * 日志描述 
    */  
    String description()  default "";   
      
    /** 
    * 操作类型 
    */  
    int type() default 0; 
}
