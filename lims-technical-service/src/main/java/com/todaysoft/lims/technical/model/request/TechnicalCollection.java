package com.todaysoft.lims.technical.model.request;

import java.util.List;

import com.todaysoft.lims.technical.model.TechnicalAnalyVerifySample;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalyRecord;

public class TechnicalCollection
{
    
    private TechnicalAnalyRecord record;
    
    private List<TechnicalAnalyVerifySample> samples;
    
    public TechnicalAnalyRecord getRecord()
    {
        return record;
    }
    
    public void setRecord(TechnicalAnalyRecord record)
    {
        this.record = record;
    }
    
    public List<TechnicalAnalyVerifySample> getSamples()
    {
        return samples;
    }
    
    public void setSamples(List<TechnicalAnalyVerifySample> samples)
    {
        this.samples = samples;
    }
    
}
