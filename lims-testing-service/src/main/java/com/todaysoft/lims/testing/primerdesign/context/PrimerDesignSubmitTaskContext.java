package com.todaysoft.lims.testing.primerdesign.context;

import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.primerdesign.model.PrimerDesignSubmitTaskArgs;

public class PrimerDesignSubmitTaskContext
{
    private String primerId;
    
    private PrimerDesignSubmitTaskArgs args;
    
    private TestingTask testingTaskEntity;
    
    public boolean isPrimerPrepared()
    {
        return this.getArgs().getResult().intValue() == 0;
    }
    
    public TestingTask getTestingTaskEntity()
    {
        return testingTaskEntity;
    }
    
    public void setTestingTaskEntity(TestingTask testingTaskEntity)
    {
        this.testingTaskEntity = testingTaskEntity;
    }
    
    public PrimerDesignSubmitTaskArgs getArgs()
    {
        return args;
    }
    
    public void setArgs(PrimerDesignSubmitTaskArgs args)
    {
        this.args = args;
    }
    
    public String getPrimerId()
    {
        return primerId;
    }
    
    public void setPrimerId(String primerId)
    {
        this.primerId = primerId;
    }
}
