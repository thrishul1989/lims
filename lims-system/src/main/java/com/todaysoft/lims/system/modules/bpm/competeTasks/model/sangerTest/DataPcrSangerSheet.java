package com.todaysoft.lims.system.modules.bpm.competeTasks.model.sangerTest;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.dpcrsanger.model.DataPcrSangerTask;

public class DataPcrSangerSheet
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String description;
    
    private List<DataPcrSangerTask> tasks;
    
    private String testerName;
    
    private Date submitTime;
    
    private List<DataPcrSangerSubmitTaskArgs> submitTasks;
    
    private List<String> resultNames;
    
    private List<List<String>> resultList;//新的模板导入结果 ;
    
    public List<String> getResultNames()
    {
        return resultNames;
    }
    
    public void setResultNames(List<String> resultNames)
    {
        this.resultNames = resultNames;
    }
    
    public List<List<String>> getResultList()
    {
        return resultList;
    }
    
    public void setResultList(List<List<String>> resultList)
    {
        this.resultList = resultList;
    }
    
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
    
    public List<DataPcrSangerTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<DataPcrSangerTask> tasks)
    {
        this.tasks = tasks;
    }
    
    public String getTesterName()
    {
        return testerName;
    }
    
    public void setTesterName(String testerName)
    {
        this.testerName = testerName;
    }
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
    
    public List<DataPcrSangerSubmitTaskArgs> getSubmitTasks()
    {
        return submitTasks;
    }
    
    public void setSubmitTasks(List<DataPcrSangerSubmitTaskArgs> submitTasks)
    {
        this.submitTasks = submitTasks;
    }
}
