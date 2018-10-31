package com.todaysoft.lims.testing.firstpcr.model;

import java.util.List;

public class FirstPcrSubmitRequest
{
    private String id;
    
    private List<FirstPcrSubmitTaskArgs> tasks;
    
    private String qcPointTestCode;
    
    private String qcPointPrimerLocation;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<FirstPcrSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FirstPcrSubmitTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
    
    public String getQcPointTestCode()
    {
        return qcPointTestCode;
    }
    
    public void setQcPointTestCode(String qcPointTestCode)
    {
        this.qcPointTestCode = qcPointTestCode;
    }
    
    public String getQcPointPrimerLocation()
    {
        return qcPointPrimerLocation;
    }
    
    public void setQcPointPrimerLocation(String qcPointPrimerLocation)
    {
        this.qcPointPrimerLocation = qcPointPrimerLocation;
    }
}
