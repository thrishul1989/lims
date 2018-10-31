package com.todaysoft.lims.activiti.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

import com.todaysoft.lims.testing.base.service.activiti.ProcessEventHandler;
import com.todaysoft.lims.testing.base.service.activiti.ProcessEventHandlerFactory;


public class ProcessEventDispatcher implements ExecutionListener
{
    private static final long serialVersionUID = 7360721459938921186L;
    
    @Override
    public void notify(DelegateExecution execution)
        throws Exception
    {
        ExecutionEntity entity = (ExecutionEntity)execution;
        
        ProcessEventHandler handler = ProcessEventHandlerFactory.getHandler(entity);
        
        if (null != handler)
        {
            String event = null;
            
            if (entity.isActive())
            {
                event = EVENTNAME_START;
            }
            else if (entity.isEnded())
            {
                event = EVENTNAME_END;
            }
            
            if (EVENTNAME_START.equals(event))
            {
                handler.start(entity);
            }
            else if (EVENTNAME_END.equals(event))
            {
                handler.end(entity);
            }
        }
    }
}
