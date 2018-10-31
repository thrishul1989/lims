package com.todaysoft.lims.exception;

public class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = 6152256123529232835L;
    
    private String errorCode;
    
    private String errorMessage;
    
    public ServiceException()
    {
        this(null, null);
    }
    
    public ServiceException(String errorCode)
    {
        this(errorCode, null);
    }
    
    public ServiceException(String errorCode, String errorMessage)
    {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    public static ServiceException innerError()
    {
        return new ServiceException("CMM-0101", "系统内部错误");
    }
    
    public String getErrorCode()
    {
        return errorCode;
    }
    
    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }
    
    public String getErrorMessage()
    {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}
