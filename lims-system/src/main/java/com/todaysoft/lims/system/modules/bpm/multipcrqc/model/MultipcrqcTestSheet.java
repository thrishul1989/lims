package com.todaysoft.lims.system.modules.bpm.multipcrqc.model;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.longqc.model.LongqcTestModel;

public class MultipcrqcTestSheet
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String description;
    
    private String qcReagentKitName;
    
    private String qcTester;
    
    private List<MultipcrqcTestModel> tasks;
    
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

    public List<MultipcrqcTestModel> getTasks()
    {
        return tasks;
    }

    public void setTasks(List<MultipcrqcTestModel> tasks)
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

    private Date submitTime;
}
