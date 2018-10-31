package com.todaysoft.lims.sample.model.order;

import java.util.Set;

public class OrderSampleDetailsResponse
{
    private Set<String> totalSampleCodes;
    
    private Set<String> storagedSampleCodes;
    
    private Set<String> abnormalSolvedSampleCodes;
    
    public Set<String> getTotalSampleCodes()
    {
        return totalSampleCodes;
    }
    
    public void setTotalSampleCodes(Set<String> totalSampleCodes)
    {
        this.totalSampleCodes = totalSampleCodes;
    }
    
    public Set<String> getStoragedSampleCodes()
    {
        return storagedSampleCodes;
    }
    
    public void setStoragedSampleCodes(Set<String> storagedSampleCodes)
    {
        this.storagedSampleCodes = storagedSampleCodes;
    }
    
    public Set<String> getAbnormalSolvedSampleCodes()
    {
        return abnormalSolvedSampleCodes;
    }
    
    public void setAbnormalSolvedSampleCodes(Set<String> abnormalSolvedSampleCodes)
    {
        this.abnormalSolvedSampleCodes = abnormalSolvedSampleCodes;
    }
}
