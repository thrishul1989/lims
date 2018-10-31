package com.todaysoft.lims.system.util;

import java.io.Serializable;

public class CommonResult implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private boolean success = false;// 是否成功
    
    private String msg = "";// 提示信息
    
    private Object data = null;// 数据
    
    public String getMsg()
    {
        return msg;
    }
    
    public void setMsg(String msg)
    {
        this.msg = msg;
    }
    
    public Object getData()
    {
        return data;
    }
    
    public void setData(Object data)
    {
        this.data = data;
    }
    
    public boolean isSuccess()
    {
        return success;
    }
    
    public void setSuccess(boolean success)
    {
        this.success = success;
    }
}
