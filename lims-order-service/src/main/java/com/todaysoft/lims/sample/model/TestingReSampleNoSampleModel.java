package com.todaysoft.lims.sample.model;

import java.util.List;

import com.todaysoft.lims.sample.entity.AbnormalSolveRecord;
import com.todaysoft.lims.sample.entity.TestingReSampleNoSampleRecord;

public class TestingReSampleNoSampleModel
{
    private List<TestingReSampleNoSampleRecord> records;
    
    private List<AbnormalSolveRecord> solves;

    public List<TestingReSampleNoSampleRecord> getRecords()
    {
        return records;
    }

    public void setRecords(List<TestingReSampleNoSampleRecord> records)
    {
        this.records = records;
    }

    public List<AbnormalSolveRecord> getSolves()
    {
        return solves;
    }

    public void setSolves(List<AbnormalSolveRecord> solves)
    {
        this.solves = solves;
    }
    
}
