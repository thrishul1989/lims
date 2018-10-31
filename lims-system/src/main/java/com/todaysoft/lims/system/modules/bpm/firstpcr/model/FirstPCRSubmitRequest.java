package com.todaysoft.lims.system.modules.bpm.firstpcr.model;

import java.util.List;

public class FirstPCRSubmitRequest
{
    private String id;
    
    private List<FirstPCRSubmitTaskArgs> tasks;
    
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
    
    public List<FirstPCRSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FirstPCRSubmitTaskArgs> tasks)
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
