package com.todaysoft.lims.technical.model.request;

import java.util.List;

import com.google.common.collect.Lists;
import com.todaysoft.lims.technical.model.TechnicalAnalySubmitRecord;

public class TechnicalAnalySubmitRequest
{
    
    private String familyGroupId;//提交的任务id
    
    private List<TechnicalAnalySubmitRecord> records = Lists.newArrayList();
    
    public String getFamilyGroupId()
    {
        return familyGroupId;
    }
    
    public void setFamilyGroupId(String familyGroupId)
    {
        this.familyGroupId = familyGroupId;
    }
    
    public List<TechnicalAnalySubmitRecord> getRecords()
    {
        return records;
    }
    
    public void setRecords(List<TechnicalAnalySubmitRecord> records)
    {
        this.records = records;
    }
    
}
