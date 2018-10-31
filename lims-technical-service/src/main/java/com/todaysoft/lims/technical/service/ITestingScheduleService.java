package com.todaysoft.lims.technical.service;

import java.util.List;
import java.util.Set;

import com.todaysoft.lims.technical.mybatis.entity.TestingTask;

public interface ITestingScheduleService
{
    void sendOrderVerifyTestingStartMessage(List<String> scheduleIds, Set<String> orderIds);

    void updateRedundantTask(List<TestingTask> tasks);
}
