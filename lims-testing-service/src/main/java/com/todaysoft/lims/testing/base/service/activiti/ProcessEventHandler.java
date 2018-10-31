package com.todaysoft.lims.testing.base.service.activiti;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

public interface ProcessEventHandler
{
    void start(ExecutionEntity entity);
    
    void end(ExecutionEntity entity);
}
