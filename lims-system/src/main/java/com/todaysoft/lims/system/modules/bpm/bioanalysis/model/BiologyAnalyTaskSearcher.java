package com.todaysoft.lims.system.modules.bpm.bioanalysis.model;

public class BiologyAnalyTaskSearcher
{
    private String sampleCode;
    
    private Integer ifUrgent;
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }

    public Integer getIfUrgent()
    {
        return ifUrgent;
    }

    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }
    
}
