package com.todaysoft.lims.schedule.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.service.ISchedulePlanService;

@Component
public class ScheduleMonitorJob
{
    @Autowired
    private ISchedulePlanService service;
    
    @Scheduled(cron = "0 0 */1 * * ? ")
    public void execute()
    {
        service.updatePlanForMonitorJob();
    }
}
