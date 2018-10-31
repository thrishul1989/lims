package com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalySubmitRecord;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyTask;

public class TechnicalAnalySheet
{
    private String id;
    
    private String code;
    
    private String assignerName;
    
    private Date assignTime;
    
    private String description;
    
    private boolean verified;
    
    private List<TechnicalAnalyTask> tasks;
    
    private List<TechnicalAnalySubmitRecord> records;
    
    private String testerName;
    
    private Date submitTime;
    
    private List<String> columnNames;
    
    private List<List<String>> columnValues;
    
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
    
    public List<TechnicalAnalyTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<TechnicalAnalyTask> tasks)
    {
        this.tasks = tasks;
    }
    
    public boolean isVerified()
    {
        return verified;
    }
    
    public void setVerified(boolean verified)
    {
        this.verified = verified;
    }
    
    public List<TechnicalAnalySubmitRecord> getRecords()
    {
        return records;
    }
    
    public void setRecords(List<TechnicalAnalySubmitRecord> records)
    {
        this.records = records;
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
    
    public List<String> getColumnNames()
    {
        return columnNames;
    }
    
    public void setColumnNames(List<String> columnNames)
    {
        this.columnNames = columnNames;
    }
    
    public List<List<String>> getColumnValues()
    {
        return columnValues;
    }
    
    public void setColumnValues(List<List<String>> columnValues)
    {
        this.columnValues = columnValues;
    }
}
