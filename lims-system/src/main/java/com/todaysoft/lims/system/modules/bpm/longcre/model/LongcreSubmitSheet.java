package com.todaysoft.lims.system.modules.bpm.longcre.model;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrAssignModel;

public class LongcreSubmitSheet
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String description;
    
    private String creReagentKitName;
    
    private String creTester;
    
    private List<LongcreSubmitModel> tasks;
    
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
    
    public String getCreReagentKitName()
    {
        return creReagentKitName;
    }
    
    public void setCreReagentKitName(String creReagentKitName)
    {
        this.creReagentKitName = creReagentKitName;
    }
    
    public String getCreTester()
    {
        return creTester;
    }
    
    public void setCreTester(String creTester)
    {
        this.creTester = creTester;
    }
    
    public List<LongcreSubmitModel> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<LongcreSubmitModel> tasks)
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
