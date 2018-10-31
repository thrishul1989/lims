package com.todaysoft.lims.testing.base.service.activiti;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

public class NodeScheduleProcessHandler implements ProcessEventHandler
{
    @Override
    public void start(ExecutionEntity entity)
    {
        
    }
    
    @Override
    public void end(ExecutionEntity entity)
    {
        String id = entity.getCurrentActivityId();
        
        if ("end-success".equals(id))
        {
            
        }
        else if ("end-exception".equals(id))
        {
            
        }
    }
}
