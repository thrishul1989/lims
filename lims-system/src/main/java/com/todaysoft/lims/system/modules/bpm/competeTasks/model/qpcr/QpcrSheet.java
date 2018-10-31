package com.todaysoft.lims.system.modules.bpm.competeTasks.model.qpcr;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrSubmitTask;
import com.todaysoft.lims.system.modules.bpm.qpcr.model.QpcrTask;

public class QpcrSheet
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String description;
    
    private String reagentKitName;
    
    private String tester;
    
    private List<QpcrTask> tasks;
    
    private Date submitTime;
    
    private List<QpcrSubmitTask> subTasks;
    
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
    
    public List<QpcrTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<QpcrTask> tasks)
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
    
    public List<QpcrSubmitTask> getSubTasks()
    {
        return subTasks;
    }
    
    public void setSubTasks(List<QpcrSubmitTask> subTasks)
    {
        this.subTasks = subTasks;
    }
}
