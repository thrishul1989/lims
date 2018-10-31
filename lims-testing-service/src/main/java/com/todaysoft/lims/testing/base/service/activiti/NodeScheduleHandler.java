package com.todaysoft.lims.testing.base.service.activiti;

import org.activiti.engine.delegate.DelegateTask;

public class NodeScheduleHandler implements TaskEventHandler
{
    @Override
    public void create(DelegateTask task)
    {
        
    }
    
    @Override
    public void complete(DelegateTask task)
    {
        // do nothing
    }
}
