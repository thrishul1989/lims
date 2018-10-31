package com.todaysoft.lims.testing.listener.model;

public class SampleAbnormalSolvedEvent
{
    public static final String SOLVED_RESEND_SAMPLE = "1";
    
    public static final String SOLVED_NOSEND_SAMPLE = "2";
    
    private String abnormalSampleId;
    
    private String solvedStrategy;
    
    private String resendSampleId;
    
    public String getAbnormalSampleId()
    {
        return abnormalSampleId;
    }
    
    public void setAbnormalSampleId(String abnormalSampleId)
    {
        this.abnormalSampleId = abnormalSampleId;
    }
    
    public String getSolvedStrategy()
    {
        return solvedStrategy;
    }
    
    public void setSolvedStrategy(String solvedStrategy)
    {
        this.solvedStrategy = solvedStrategy;
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
