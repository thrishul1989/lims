package com.todaysoft.lims.reagent.service.adapter;

/**
 * 错误码定义规则：5位字符串
 * 第一位为错误类型标识：S-业务错误 C-通用错误
 * 第二、三位为模块编号：样本管理服务模块号11
 * 第四、五位为错误编号：依次递增
 */
public class ErrorCode
{
    public static final String UNDEFINED_ERROR = "C1101";
    
    public static final String ILLEGAL_ARGUMENT = "C1102";
    
    public static final String RECORD_NOT_EXISTS = "C1103";
    
    public static final String SAMPLE_CODE_EXISTS = "S1101";
    
    public static final String SAMPLE_EXTRACT_SOURCE_NOT_EXISTS = "S1102";
    
    public static final String SAMPLE_EXTRACT_TARGET_NOT_EXISTS = "S1103";
}
