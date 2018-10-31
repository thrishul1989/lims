package com.todaysoft.lims.smm.service.impl;

/**
 * 错误码定义规则：5位字符串
 * 第一位为错误类型标识：S-业务错误 C-通用错误
 * 第二、三位为模块编号：用户管理服务模块号01
 * 第四、五位为错误编号：依次递增
 */
public class ErrorCode
{
    public static final String UNDEFINED_ERROR = "C0101";
    
    public static final String ILLEGAL_ARGUMENT = "C0102";
    
    public static final String RECORD_NOT_EXISTS = "C0103";
    
    public static final String OLD_PASSWORD_UNMATCHED = "S0101";
    
    public static final String USERNAME_EXISTS = "S0102";
    
    public static final String LOGIN_BAD_CREDENTIALS = "S0201";
    
    public static final String LOGIN_ACCOUNT_DISABLED = "S0202";
    
    public static final String LOGIN_ACCOUNT_LOCKED = "S0203";
}
