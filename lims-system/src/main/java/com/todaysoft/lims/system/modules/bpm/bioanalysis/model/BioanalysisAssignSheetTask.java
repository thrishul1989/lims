package com.todaysoft.lims.system.modules.bpm.bioanalysis.model;

import java.util.Date;

public class BioanalysisAssignSheetTask
{
    private String id;
    
    private String testingCode;
    
    private Date onTestingDate;
    
    private String fourHtOneValue;
    
    private String oneEightHtOneValue;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    public Date getOnTestingDate()
    {
        return onTestingDate;
    }
    
    public void setOnTestingDate(Date onTestingDate)
    {
        this.onTestingDate = onTestingDate;
    }
    
    public String getFourHtOneValue()
    {
        return fourHtOneValue;
    }
    
    public void setFourHtOneValue(String fourHtOneValue)
    {
        this.fourHtOneValue = fourHtOneValue;
    }
    
    public String getOneEightHtOneValue()
    {
        return oneEightHtOneValue;
    }
    
    public void setOneEightHtOneValue(String oneEightHtOneValue)
    {
        this.oneEightHtOneValue = oneEightHtOneValue;
    }
}
