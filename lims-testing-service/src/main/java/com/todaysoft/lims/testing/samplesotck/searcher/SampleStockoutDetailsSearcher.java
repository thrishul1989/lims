package com.todaysoft.lims.testing.samplesotck.searcher;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStockin;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStockout;
import com.todaysoft.lims.testing.samplesotck.entity.SampleStockoutDetails;

public class SampleStockoutDetailsSearcher implements ISearcher<SampleStockoutDetails>
{
    
    private SampleStockout record;
    
    private TestingSample sampleId;
    
    private String sampleLocation;
    
    @Override
    public NamedQueryer toQuery()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    public SampleStockout getRecord()
    {
        return record;
    }

    public void setRecord(SampleStockout record)
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
    public Class<SampleStockoutDetails> getEntityClass()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
}
