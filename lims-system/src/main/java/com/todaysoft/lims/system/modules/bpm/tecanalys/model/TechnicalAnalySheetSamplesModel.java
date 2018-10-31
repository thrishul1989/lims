package com.todaysoft.lims.system.modules.bpm.tecanalys.model;

import java.util.List;

public class TechnicalAnalySheetSamplesModel
{
    private List<TechnicalAnalySheetReceivedSample> samples;
    
    public List<TechnicalAnalySheetReceivedSample> getSamples()
    {
        return samples;
    }
    
    public void setSamples(List<TechnicalAnalySheetReceivedSample> samples)
    {
        this.samples = samples;
    }
}
