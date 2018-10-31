package com.todaysoft.lims.sample.service.impl;

public class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = -2507236132032711662L;
    
    private String errorCode;
    
    public ServiceException(String errorCode)
    {
        this.errorCode = errorCode;
    }
    
    public ServiceException(String errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
    }
    
    public ServiceException(String errorCode, String message, Throwable cause)
    {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public ServiceException(Throwable cause)
    {
        super(cause.getMessage(), cause);
        this.errorCode = ErrorCode.UNDEFINED_ERROR;
    }
    
    public String getErrorCode()
    {
        return errorCode;
    }
}
