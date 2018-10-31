package com.todaysoft.lims.testing.samplesotck.searcher;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStockin;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStockinDetails;

public class SampleStockinDetailsSeacher implements ISearcher<SampleStockinDetails>
{
    
    private SampleStockin record;
    
    private TestingSample sampleId;
    
    private String sampleLocation;
    
    public SampleStockin getRecord()
    {
        return record;
    }
    
    public void setRecord(SampleStockin record)
    {
        this.record = record;
    }
    
    public TestingSample getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(TestingSample sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public String getSampleLocation()
    {
        return sampleLocation;
    }
    
    public void setSampleLocation(String sampleLocation)
    {
        this.sampleLocation = sampleLocation;
    }
    
    @Override
    public NamedQueryer toQuery()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Class<SampleStockinDetails> getEntityClass()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
}
