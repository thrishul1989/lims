package com.todaysoft.lims.schedule.service;

import com.todaysoft.lims.schedule.entity.TaskConfig;

public interface ITaskConfigService
{
    TaskConfig getTaskConfig(String semantic);
}
