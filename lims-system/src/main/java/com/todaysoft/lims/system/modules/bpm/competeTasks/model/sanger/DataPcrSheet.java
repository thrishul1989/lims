package com.todaysoft.lims.system.modules.bpm.competeTasks.model.sanger;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.dpcr.model.DataPcrTask;

public class DataPcrSheet
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String description;
    
    private List<DataPcrTask> tasks;
    
    private List<DataPcrSubmitTaskArgs> submitTasks;
    
    private String testerName;
    
    private Date submitTime;
    
    private List<List<String>> resultList;//新的模板导入结果 ;
    
    private List<String> resultNames;
    
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
    
    public List<DataPcrTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<DataPcrTask> tasks)
    {
        this.tasks = tasks;
    }
    
    public List<DataPcrSubmitTaskArgs> getSubmitTasks()
    {
        return submitTasks;
    }
    
    public void setSubmitTasks(List<DataPcrSubmitTaskArgs> submitTasks)
    {
        this.submitTasks = submitTasks;
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
}
