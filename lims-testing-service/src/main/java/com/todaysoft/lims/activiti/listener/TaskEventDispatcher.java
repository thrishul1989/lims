package com.todaysoft.lims.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.testing.base.service.activiti.TaskEventHandler;
import com.todaysoft.lims.testing.base.service.activiti.TaskEventHandlerFactory;


@Component
public class TaskEventDispatcher implements TaskListener
{
    private static final long serialVersionUID = 6949695138552533902L;
    
    @Override
    @Transactional
    public void notify(DelegateTask delegateTask)
    {
        TaskEventHandler handler = TaskEventHandlerFactory.getHandler(delegateTask);
        
        if (null != handler)
        {
            String event = delegateTask.getEventName();
            
            if ("create".equalsIgnoreCase(event))
            {
                handler.create(delegateTask);
            }
            else if ("complete".equalsIgnoreCase(event))
            {
                handler.complete(delegateTask);
            }
        }
    }
}
