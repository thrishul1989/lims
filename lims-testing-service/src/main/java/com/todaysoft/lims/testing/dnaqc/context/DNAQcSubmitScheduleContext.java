package com.todaysoft.lims.testing.dnaqc.context;

import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.model.TaskConfig;

public class DNAQcSubmitScheduleContext
{
    private String id;
    
    private boolean qcQualified;
    
    private String qcUnqualifiedRemark;
    
    private String qcUnqualifiedStrategy;
    
    private String nextNodeInputSampleId;
    
    private TaskConfig nextNodeConfig;
    
    private TestingSchedule testingScheduleEntity;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public boolean isQcQualified()
    {
        return qcQualified;
    }
    
    public void setQcQualified(boolean qcQualified)
    {
        this.qcQualified = qcQualified;
    }
    
    public String getQcUnqualifiedRemark()
    {
        return qcUnqualifiedRemark;
    }
    
    public void setQcUnqualifiedRemark(String qcUnqualifiedRemark)
    {
        this.qcUnqualifiedRemark = qcUnqualifiedRemark;
    }
    
    public String getQcUnqualifiedStrategy()
    {
        return qcUnqualifiedStrategy;
    }
    
    public void setQcUnqualifiedStrategy(String qcUnqualifiedStrategy)
    {
        this.qcUnqualifiedStrategy = qcUnqualifiedStrategy;
    }
    
    public String getNextNodeInputSampleId()
    {
        return nextNodeInputSampleId;
    }
    
    public void setNextNodeInputSampleId(String nextNodeInputSampleId)
    {
        this.nextNodeInputSampleId = nextNodeInputSampleId;
    }
    
    public TaskConfig getNextNodeConfig()
    {
        return nextNodeConfig;
    }
    
    public void setNextNodeConfig(TaskConfig nextNodeConfig)
    {
        this.nextNodeConfig = nextNodeConfig;
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
