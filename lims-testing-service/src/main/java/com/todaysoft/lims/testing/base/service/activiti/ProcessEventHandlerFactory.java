package com.todaysoft.lims.testing.base.service.activiti;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

public class ProcessEventHandlerFactory
{
    private static final String PROCESS_NODE_SCHEDULE = "node-schedule";
    
    public static ProcessEventHandler getHandler(ExecutionEntity entity)
    {
        String processDefinitionKey = entity.getProcessDefinitionId().split(":")[0];
        
        if (PROCESS_NODE_SCHEDULE.equals(processDefinitionKey))
        {
            return new NodeScheduleProcessHandler();
        }
        else
        {
            return new NothingHandler();
        }
    }
}
