package com.todaysoft.lims.system.modules.bpm.model.assign.model;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.model.assign.model.task.BiAnalysisAssignTask;
import com.todaysoft.lims.system.modules.bpm.model.internal.QualityControlMethod;
import com.todaysoft.lims.system.modules.bpm.model.internal.ReagentKits;
import com.todaysoft.lims.system.modules.bpm.model.internal.Tester;

public class BiAnalysisAssignModel
{
    private String taskCode;
    
    private String loginUsername;
    
    private Date timestamp;
    
    private List<Tester> extractTesters;
    
    private List<QualityControlMethod> qualityControlMethods;
    
    private List<Tester> qualityControlTesters;
    
    private List<ReagentKits> reagentKits;
    
    private List<BiAnalysisAssignTask> tasks;
    
    public String getTaskCode()
    {
        return taskCode;
    }
    
    public void setTaskCode(String taskCode)
    {
        this.taskCode = taskCode;
    }
    
    public String getLoginUsername()
    {
        return loginUsername;
    }
    
    public void setLoginUsername(String loginUsername)
    {
        this.loginUsername = loginUsername;
    }
    
    public Date getTimestamp()
    {
        return timestamp;
    }
    
    public void setTimestamp(Date timestamp)
    {
        this.timestamp = timestamp;
    }
    
    public List<Tester> getExtractTesters()
    {
        return extractTesters;
    }
    
    public void setExtractTesters(List<Tester> extractTesters)
    {
        this.extractTesters = extractTesters;
    }
    
    public List<QualityControlMethod> getQualityControlMethods()
    {
        return qualityControlMethods;
    }
    
    public void setQualityControlMethods(List<QualityControlMethod> qualityControlMethods)
    {
        this.qualityControlMethods = qualityControlMethods;
    }
    
    public List<Tester> getQualityControlTesters()
    {
        return qualityControlTesters;
    }
    
    public void setQualityControlTesters(List<Tester> qualityControlTesters)
    {
        this.qualityControlTesters = qualityControlTesters;
    }
    
    public List<ReagentKits> getReagentKits()
    {
        return reagentKits;
    }
    
    public void setReagentKits(List<ReagentKits> reagentKits)
    {
        this.reagentKits = reagentKits;
    }
    
    public List<BiAnalysisAssignTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<BiAnalysisAssignTask> tasks)
    {
        this.tasks = tasks;
    }
    
}
