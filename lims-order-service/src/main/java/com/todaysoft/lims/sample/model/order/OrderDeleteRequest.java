package com.todaysoft.lims.sample.model.order;

public class OrderDeleteRequest
{
    private String id;
    
    private String cancelId;
    
    private String cancelName;
    
    private String remark;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCancelId()
    {
        return cancelId;
    }
    
    public void setCancelId(String cancelId)
    {
        this.cancelId = cancelId;
    }
    
    public String getCancelName()
    {
        return cancelName;
    }
    
    public void setCancelName(String cancelName)
    {
        this.cancelName = cancelName;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
}
