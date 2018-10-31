package com.todaysoft.lims.sample.util;

public class ObjectResponse<E> extends Response
{
    public E info;
    
    private String token;
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public E getInfo()
    {
        return info;
    }
    
    private void setInfo(E info)
    {
        this.info = info;
    }
    
    private ObjectResponse()
    {
        
    }
    
    public static ObjectResponse newInstance()
    {
        return new ObjectResponse();
    }
    
    public ObjectResponse info(E info)
    {
        setInfo(info);
        return this;
    }
    
    public ObjectResponse status(Integer status)
    {
        setStatus(status);
        return this;
    }
    
    public ObjectResponse message(String message)
    {
        setMessage(message);
        return this;
    }
    
    public ObjectResponse success(boolean success)
    {
        setSuccess(success);
        return this;
    }
    
    public ObjectResponse token(String token)
    {
        setToken(token);
        return this;
    }
    
}
