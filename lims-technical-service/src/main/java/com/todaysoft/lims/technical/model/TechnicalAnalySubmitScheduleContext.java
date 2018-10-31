package com.todaysoft.lims.technical.model;

import java.util.Set;

import com.todaysoft.lims.technical.mybatis.entity.TestingSchedule;



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
