package com.todaysoft.lims.testing.qt.model;

import java.util.Date;
import java.util.List;

public class QtSubmitModel
{
    private String id;
    
    private String code;
    
    private String reagentKitName;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String description;
    
    private String sampleCode;
    
    private List<QtAssignReference> references;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getReagentKitName()
    {
        return reagentKitName;
    }
    
    public void setReagentKitName(String reagentKitName)
    {
        this.reagentKitName = reagentKitName;
    }
    
    public String getAssignerName()
    {
        return assignerName;
    }
    
    public void setAssignerName(String assignerName)
    {
        this.assignerName = assignerName;
    }
    
    public Date getAssignTime()
    {
        return assignTime;
    }
    
    public void setAssignTime(Date assignTime)
    {
        this.assignTime = assignTime;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public List<QtAssignReference> getReferences()
    {
        return references;
    }
    
    public void setReferences(List<QtAssignReference> references)
    {
        this.references = references;
    }
}
