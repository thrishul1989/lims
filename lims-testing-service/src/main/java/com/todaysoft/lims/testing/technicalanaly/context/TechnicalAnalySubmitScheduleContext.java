package com.todaysoft.lims.testing.technicalanaly.context;

import java.util.Set;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.technicalanaly.model.TechnicalAnalySubmitRecord;

public class TechnicalAnalySubmitScheduleContext
{
    private Set<TechnicalAnalySubmitRecord> verifyRecords;
    
    private TestingSchedule testingScheduleEntity;
    
    public Set<TechnicalAnalySubmitRecord> getVerifyRecords()
    {
        return verifyRecords;
    }
    
    public void setVerifyRecords(Set<TechnicalAnalySubmitRecord> verifyRecords)
    {
        this.verifyRecords = verifyRecords;
    }
    
    public TestingSchedule getTestingScheduleEntity()
    {
        return testingScheduleEntity;
    }
    
    public void setTestingScheduleEntity(TestingSchedule testingScheduleEntity)
    {
        this.testingScheduleEntity = testingScheduleEntity;
    }
}
