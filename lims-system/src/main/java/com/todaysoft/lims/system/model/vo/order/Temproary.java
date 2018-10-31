package com.todaysoft.lims.system.model.vo.order;

public class Temproary
{
    private String sourceId;
    
    private String targetId;
    
    private String scheduleId;
    
    private String methedId;
    
    private String verifyKey;
    
    public String getVerifyKey()
    {
        return verifyKey;
    }
    
    public void setVerifyKey(String verifyKey)
    {
        this.verifyKey = verifyKey;
    }
    
    public String getScheduleId()
    {
        return scheduleId;
    }
    
    public void setScheduleId(String scheduleId)
    {
        this.scheduleId = scheduleId;
    }
    
    public String getMethedId()
    {
        return methedId;
    }
    
    public void setMethedId(String methedId)
    {
        this.methedId = methedId;
    }
    
    public String getSourceId()
    {
        return sourceId;
    }
    
    public void setSourceId(String sourceId)
    {
        this.sourceId = sourceId;
    }
    
    public String getTargetId()
    {
        return targetId;
    }
    
    public void setTargetId(String targetId)
    {
        this.targetId = targetId;
    }
    
}
