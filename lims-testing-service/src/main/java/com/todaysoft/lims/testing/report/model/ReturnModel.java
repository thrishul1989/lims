package com.todaysoft.lims.testing.report.model;

import java.util.List;
import java.util.Map;

public class ReturnModel
{
    private String msg;
    
    private List<Map<String,String>> orderProductIds;
 
    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public List<Map<String, String>> getOrderProductIds()
    {
        return orderProductIds;
    }

    public void setOrderProductIds(List<Map<String, String>> orderProductIds)
    {
        this.orderProductIds = orderProductIds;
    }

    
}
