package com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumnField
{
    int index();
    
    ExcelColumnValueType type() default ExcelColumnValueType.STRING;
}
