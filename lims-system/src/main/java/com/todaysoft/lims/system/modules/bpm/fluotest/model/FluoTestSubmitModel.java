package com.todaysoft.lims.system.modules.bpm.fluotest.model;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongPcrTask;
import com.todaysoft.lims.system.modules.bpm.longpcr.model.LongpcrAssignModel;

public class FluoTestSubmitModel
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String description;
    
    private String reagentKitName;
    
    private String tester;
    
    private List<FluoTestTask> tasks;
    
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
    
    
    
    public String getReagentKitName()
    {
        return reagentKitName;
    }

    public void setReagentKitName(String reagentKitName)
    {
        this.reagentKitName = reagentKitName;
    }

    public String getTester()
    {
        return tester;
    }

    public void setTester(String tester)
    {
        this.tester = tester;
    }

    public List<FluoTestTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FluoTestTask> tasks)
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
