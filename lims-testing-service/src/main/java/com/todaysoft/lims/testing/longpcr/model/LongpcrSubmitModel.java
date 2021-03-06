package com.todaysoft.lims.testing.longpcr.model;

import java.util.Date;
import java.util.List;



public class LongpcrSubmitModel
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String description;
    
    private String pcrReagentKitName;
    
    private String pcrTester;
    
    private List<LongpcrAssignModel> tasks;
    
    private Date submitTime;
    
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
    
    public String getPcrReagentKitName()
    {
        return pcrReagentKitName;
    }
    
    public void setPcrReagentKitName(String pcrReagentKitName)
    {
        this.pcrReagentKitName = pcrReagentKitName;
    }
    
 
    
    public String getPcrTester()
    {
        return pcrTester;
    }

    public void setPcrTester(String pcrTester)
    {
        this.pcrTester = pcrTester;
    }

    public List<LongpcrAssignModel> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<LongpcrAssignModel> tasks)
    {
        this.tasks = tasks;
    }
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
}
