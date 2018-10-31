package com.todaysoft.lims.testing.report.model;

public class ReportProcessResult
{
    private String id;
    
    private String clinicalJudgment;
    
    private String mutationSource;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getClinicalJudgment()
    {
        return clinicalJudgment;
    }
    
    public void setClinicalJudgment(String clinicalJudgment)
    {
        this.clinicalJudgment = clinicalJudgment;
    }
    
    public String getMutationSource()
    {
        return mutationSource;
    }
    
    public void setMutationSource(String mutationSource)
    {
        this.mutationSource = mutationSource;
    }
}
