package com.todaysoft.lims.system.modules.bpm.competeTasks.model.fluo;

import java.util.Date;
import java.util.List;

public class FluoAnalyseSheet
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String description;
    
    private String tester;
    
    private List<FluoAnalyseSheetTask> tasks;
    
    private Date submitTime;

    private List<String> resultNames;

    private List<List<String>> resultList;//新的模板导入结果 ;
    
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
    
    public String getTester()
    {
        return tester;
    }
    
    public void setTester(String tester)
    {
        this.tester = tester;
    }
    
    public List<FluoAnalyseSheetTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FluoAnalyseSheetTask> tasks)
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

    public List<String> getResultNames() {
        return resultNames;
    }

    public void setResultNames(List<String> resultNames) {
        this.resultNames = resultNames;
    }

    public List<List<String>> getResultList() {
        return resultList;
    }

    public void setResultList(List<List<String>> resultList) {
        this.resultList = resultList;
    }
}
