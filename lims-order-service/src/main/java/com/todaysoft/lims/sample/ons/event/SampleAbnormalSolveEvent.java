package com.todaysoft.lims.sample.ons.event;

public class SampleAbnormalSolveEvent
{
    private String abnormalSampleId;
    
    private String strategy;
    
    private String resendSampleId;
    
    public String getAbnormalSampleId()
    {
        return abnormalSampleId;
    }
    
    public void setAbnormalSampleId(String abnormalSampleId)
    {
        this.abnormalSampleId = abnormalSampleId;
    }
    
    public String getStrategy()
    {
        return strategy;
    }
    
    public void setStrategy(String strategy)
    {
        this.strategy = strategy;
    }
    
    public String getResendSampleId()
    {
        return resendSampleId;
    }
    
    public void setResendSampleId(String resendSampleId)
    {
        this.resendSampleId = resendSampleId;
    }
}
