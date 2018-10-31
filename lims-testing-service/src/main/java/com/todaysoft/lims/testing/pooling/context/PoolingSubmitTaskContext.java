package com.todaysoft.lims.testing.pooling.context;

import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.pooling.model.PoolingTaskVariables;

public class PoolingSubmitTaskContext
{
    private String id;
    
    private boolean poolingable;
    
    private TestingTask testingTaskEntity;
    
    private PoolingTaskVariables testingTaskVariables;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public boolean isPoolingable()
    {
        return poolingable;
    }
    
    public void setPoolingable(boolean poolingable)
    {
        this.poolingable = poolingable;
    }
    
    public TestingTask getTestingTaskEntity()
    {
        return testingTaskEntity;
    }
    
    public void setTestingTaskEntity(TestingTask testingTaskEntity)
    {
        this.testingTaskEntity = testingTaskEntity;
    }
    
    public PoolingTaskVariables getTestingTaskVariables()
    {
        return testingTaskVariables;
    }
    
    public void setTestingTaskVariables(PoolingTaskVariables testingTaskVariables)
    {
        this.testingTaskVariables = testingTaskVariables;
    }
}
