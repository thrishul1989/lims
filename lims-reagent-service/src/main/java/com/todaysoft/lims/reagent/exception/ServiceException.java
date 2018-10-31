package com.todaysoft.lims.reagent.exception;

public class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = -2507236132032711662L;
    
    private String errorCode;
    
    private String errorMessage;
    
    public String getErrorCode()
    {
        return errorCode;
    }
    
    public String getErrorMessage()
    {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
    
    public ServiceException(String errorCode)
    {
        this.errorCode = errorCode;
    }
}
