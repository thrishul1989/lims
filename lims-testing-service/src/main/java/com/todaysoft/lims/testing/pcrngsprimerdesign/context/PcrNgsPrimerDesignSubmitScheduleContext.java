package com.todaysoft.lims.testing.pcrngsprimerdesign.context;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.entity.TestingScheduleActive;

public class PcrNgsPrimerDesignSubmitScheduleContext
{
    private TestingSchedule testingScheduleEntity;
    
    private TestingScheduleActive primerPrepareNodeEntity;
    
    public TestingSchedule getTestingScheduleEntity()
    {
        return testingScheduleEntity;
    }
    
    public void setTestingScheduleEntity(TestingSchedule testingScheduleEntity)
    {
        this.testingScheduleEntity = testingScheduleEntity;
    }
    
    public TestingScheduleActive getPrimerPrepareNodeEntity()
    {
        return primerPrepareNodeEntity;
    }
    
    public void setPrimerPrepareNodeEntity(TestingScheduleActive primerPrepareNodeEntity)
    {
        this.primerPrepareNodeEntity = primerPrepareNodeEntity;
    }
}
