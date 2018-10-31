package com.todaysoft.lims.system.modules.bpm.tecanalys.model.request;

import org.apache.commons.lang3.StringUtils;

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
        this.mutationId = StringUtils.isNotEmpty(mutationId) ? mutationId.trim() : "";
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
        this.method = StringUtils.isNotEmpty(method) ? method.trim() : "";
    }
    
}
