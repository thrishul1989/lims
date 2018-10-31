package com.todaysoft.lims.sample.util;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class Response<E>
{
    
    private Integer status = 200;
    
    private String message;
    
    private boolean success = true;

    private String orderId;
    
    private List<E> obj;
    
    public Response()
    {
    }
    
    public List<E> getObj()
    {
        return obj;
    }
    
    public void setObj(List<E> obj)
    {
        this.obj = obj;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public boolean isSuccess()
    {
        return success;
    }
    
    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
