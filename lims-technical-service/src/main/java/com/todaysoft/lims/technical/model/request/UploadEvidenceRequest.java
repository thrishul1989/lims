package com.todaysoft.lims.technical.model.request;

import java.util.List;

public class UploadEvidenceRequest
{
    
    private List<String> evidence;
    
    private List<String> evidenceValue;
    
    private List<String> desc;
    
    private String mongoId;
    
    public String getMongoId()
    {
        return mongoId;
    }
    
    public void setMongoId(String mongoId)
    {
        this.mongoId = mongoId;
    }
    
    public List<String> getDesc()
    {
        return desc;
    }
    
    public void setDesc(List<String> desc)
    {
        this.desc = desc;
    }
    
    public List<String> getEvidence()
    {
        return evidence;
    }
    
    public void setEvidence(List<String> evidence)
    {
        this.evidence = evidence;
    }
    
    public List<String> getEvidenceValue()
    {
        return evidenceValue;
    }
    
    public void setEvidenceValue(List<String> evidenceValue)
    {
        this.evidenceValue = evidenceValue;
    }
    
}
