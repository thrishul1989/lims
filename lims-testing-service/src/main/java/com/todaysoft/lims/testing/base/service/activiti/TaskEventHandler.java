package com.todaysoft.lims.testing.base.service.activiti;

import org.activiti.engine.delegate.DelegateTask;

public interface TaskEventHandler
{
    void create(DelegateTask task);
    
    void complete(DelegateTask task);
}
