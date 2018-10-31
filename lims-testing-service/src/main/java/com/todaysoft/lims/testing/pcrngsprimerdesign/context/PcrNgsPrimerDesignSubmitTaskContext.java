package com.todaysoft.lims.testing.pcrngsprimerdesign.context;

import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.pcrngsprimerdesign.model.PcrNgsPrimerDesignSubmitTaskArgs;

public class PcrNgsPrimerDesignSubmitTaskContext
{
    private String primerId;
    
    private PcrNgsPrimerDesignSubmitTaskArgs args;
    
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
    
    public PcrNgsPrimerDesignSubmitTaskArgs getArgs()
    {
        return args;
    }
    
    public void setArgs(PcrNgsPrimerDesignSubmitTaskArgs args)
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
