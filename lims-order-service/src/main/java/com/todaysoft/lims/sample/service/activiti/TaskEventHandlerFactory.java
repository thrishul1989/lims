package com.todaysoft.lims.sample.service.activiti;

import org.activiti.engine.delegate.DelegateTask;

public class TaskEventHandlerFactory
{
    //private static final String EVENT_COMPLETE = "complete";
    
    private static final String PROCESS_NODE_SCHEDULE = "node-schedule";
    
    public static TaskEventHandler getHandler(DelegateTask delegateTask)
    {
        String processDefinitionKey = delegateTask.getProcessDefinitionId().split(":")[0];
        
        if (PROCESS_NODE_SCHEDULE.equalsIgnoreCase(processDefinitionKey))
        {
            return new NodeScheduleHandler();
        }
        else
        {
            return new NothingHandler();
        }
    }
}
