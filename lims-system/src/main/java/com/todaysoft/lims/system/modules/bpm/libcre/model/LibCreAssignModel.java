package com.todaysoft.lims.system.modules.bpm.libcre.model;

import com.todaysoft.lims.system.modules.bpm.model.internal.QualityControlMethod;
import com.todaysoft.lims.system.modules.bpm.model.internal.ReagentKits;
import com.todaysoft.lims.system.modules.bpm.model.internal.Tester;

import java.util.Date;
import java.util.List;

public class LibCreAssignModel
{
    private String taskCode;
    
    private Date timestamp;
    
    private List<Tester> extractTesters;
    
    private List<QualityControlMethod> qualityControlMethods;
    
    private List<ReagentKits> reagentKits;
    
    private List<LibraryCreateTask> tasks;
    
    public String getTaskCode()
    {
        return taskCode;
    }
    
    public void setTaskCode(String taskCode)
    {
        this.taskCode = taskCode;
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
    
    public List<ReagentKits> getReagentKits()
    {
        return reagentKits;
    }
    
    public void setReagentKits(List<ReagentKits> reagentKits)
    {
        this.reagentKits = reagentKits;
    }
    
    public List<LibraryCreateTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<LibraryCreateTask> tasks)
    {
        this.tasks = tasks;
    }
}
