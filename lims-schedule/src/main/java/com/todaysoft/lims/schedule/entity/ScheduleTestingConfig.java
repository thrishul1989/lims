package com.todaysoft.lims.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SCHEDULE_TESTING_CONFIG")
public class ScheduleTestingConfig extends UuidKeyEntity
{
    private static final long serialVersionUID = -4737868499061004391L;
    
    private Integer threshold;
    
    private String testingMethodId;
    @Column(name = "TESTING_METHOD_ID")
    public String getTestingMethodId()
    {
        return testingMethodId;
    }
    
    public void setTestingMethodId(String testingMethodId)
    {
        this.testingMethodId = testingMethodId;
    }
    
    @Column(name = "THRESHOLD")
    public Integer getThreshold()
    {
        return threshold;
    }
    
    public void setThreshold(Integer threshold)
    {
        this.threshold = threshold;
    }
}
