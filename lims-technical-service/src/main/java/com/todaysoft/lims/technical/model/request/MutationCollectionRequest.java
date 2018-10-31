package com.todaysoft.lims.technical.model.request;

public class MutationCollectionRequest
{
    
    private String mutationId;
    
    private Integer ifCollection;
    
    private String analysisSampleId;
    
    private String method;
    
    public String getMutationId()
    {
        return mutationId;
    }
    
    public void setMutationId(String mutationId)
    {
        this.mutationId = mutationId;
    }
    
    public Integer getIfCollection()
    {
        return ifCollection;
    }
    
    public void setIfCollection(Integer ifCollection)
    {
        this.ifCollection = ifCollection;
    }
    
    public String getAnalysisSampleId()
    {
        return analysisSampleId;
    }
    
    public void setAnalysisSampleId(String analysisSampleId)
    {
        this.analysisSampleId = analysisSampleId;
    }
    
    public String getMethod()
    {
        return method;
    }
    
    public void setMethod(String method)
    {
        this.method = method;
    }
    
}
