package com.todaysoft.lims.sample.service.activiti;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

public class NothingHandler implements ProcessEventHandler, TaskEventHandler
{
    @Override
    public void start(ExecutionEntity entity)
    {
        // do nothing
    }
    
    @Override
    public void end(ExecutionEntity entity)
    {
        // do nothing
    }
    
    @Override
    public void create(DelegateTask task)
    {
        // do nothing
    }
    
    @Override
    public void complete(DelegateTask task)
    {
        // do nothing
    }
}
