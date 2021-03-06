package com.todaysoft.lims.system.modules.bpm.competeTasks.model.longpcr;

import java.util.Date;
import java.util.List;

public class LongqcSheet
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String description;
    
    private String qcReagentKitName;
    
    private String qcTester;
    
    private List<LongqcTaskModel> tasks;
    
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
    
    public String getQcReagentKitName()
    {
        return qcReagentKitName;
    }
    
    public void setQcReagentKitName(String qcReagentKitName)
    {
        this.qcReagentKitName = qcReagentKitName;
    }
    
    public String getQcTester()
    {
        return qcTester;
    }
    
    public void setQcTester(String qcTester)
    {
        this.qcTester = qcTester;
    }
    
    public List<LongqcTaskModel> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<LongqcTaskModel> tasks)
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
