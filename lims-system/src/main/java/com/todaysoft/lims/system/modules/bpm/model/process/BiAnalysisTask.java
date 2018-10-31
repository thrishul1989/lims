package com.todaysoft.lims.system.modules.bpm.model.process;

public class BiAnalysisTask
{
    private String id;
    
    private String onTestingCode;//上机实验编号
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOnTestingCode()
    {
        return onTestingCode;
    }
    
    public void setOnTestingCode(String onTestingCode)
    {
        this.onTestingCode = onTestingCode;
    }
    
}
