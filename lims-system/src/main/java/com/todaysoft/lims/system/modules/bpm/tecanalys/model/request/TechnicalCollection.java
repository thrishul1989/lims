package com.todaysoft.lims.system.modules.bpm.tecanalys.model.request;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyRecord;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.TechnicalAnalyVerifySample;

public class TechnicalCollection
{
    
    private  TechnicalAnalyRecord record;
    
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
