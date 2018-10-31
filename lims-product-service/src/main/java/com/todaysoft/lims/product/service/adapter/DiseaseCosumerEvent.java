package com.todaysoft.lims.product.service.adapter;

public class DiseaseCosumerEvent
{
    private String id; //产品id
    
    private String actionType; //操作类型
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getActionType()
    {
        return actionType;
    }
    
    public void setActionType(String actionType)
    {
        this.actionType = actionType;
    }
    
}
