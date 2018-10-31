package com.todaysoft.lims.order.model;

import java.util.Set;

public class CallBackSampleModel
{
    private Set<String> sampleIds;
    
    private Set<String> reportIds;
    
    public Set<String> getSampleIds()
    {
        return sampleIds;
    }
    
    public void setSampleIds(Set<String> sampleIds)
    {
        this.sampleIds = sampleIds;
    }
    
    public Set<String> getReportIds()
    {
        return reportIds;
    }
    
    public void setReportIds(Set<String> reportIds)
    {
        this.reportIds = reportIds;
    }
    
}
