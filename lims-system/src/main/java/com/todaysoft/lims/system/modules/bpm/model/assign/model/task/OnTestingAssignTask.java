package com.todaysoft.lims.system.modules.bpm.model.assign.model.task;

import java.util.Date;

public class OnTestingAssignTask
{
    private String id;
    
    private Date onTestingDate;//时间
    
    private String testCode;//MG_NO.
    
    private String concentration;//Concentration(nM)
    
    private String cluster;//
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTestCode()
    {
        return testCode;
    }
    
    public void setTestCode(String testCode)
    {
        this.testCode = testCode;
    }
    
    public String getConcentration()
    {
        return concentration;
    }
    
    public void setConcentration(String concentration)
    {
        this.concentration = concentration;
    }
    
    public String getCluster()
    {
        return cluster;
    }
    
    public void setCluster(String cluster)
    {
        this.cluster = cluster;
    }
    
    public Date getOnTestingDate()
    {
        return onTestingDate;
    }
    
    public void setOnTestingDate(Date onTestingDate)
    {
        this.onTestingDate = onTestingDate;
    }
    
}
