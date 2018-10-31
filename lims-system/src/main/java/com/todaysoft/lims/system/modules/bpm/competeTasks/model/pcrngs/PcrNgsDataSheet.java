package com.todaysoft.lims.system.modules.bpm.competeTasks.model.pcrngs;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataSubmitTaskArgs;
import com.todaysoft.lims.system.modules.bpm.pcrngsdata.model.PcrNgsDataTask;

public class PcrNgsDataSheet
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String description;
    
    private List<PcrNgsDataTask> tasks;
    
    private List<PcrNgsDataSubmitTaskArgs> submitTasks;
    
    private String testerName;
    
    private Date submitTime;
    
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
    
    public List<PcrNgsDataTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PcrNgsDataTask> tasks)
    {
        this.tasks = tasks;
    }
    
    public List<PcrNgsDataSubmitTaskArgs> getSubmitTasks()
    {
        return submitTasks;
    }
    
    public void setSubmitTasks(List<PcrNgsDataSubmitTaskArgs> submitTasks)
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
