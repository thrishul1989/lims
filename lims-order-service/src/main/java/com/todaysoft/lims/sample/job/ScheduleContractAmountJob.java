package com.todaysoft.lims.sample.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.sample.service.IOrderService;

@Component
public class ScheduleContractAmountJob
{
    
    private static Logger logger = LoggerFactory.getLogger(ScheduleContractAmountJob.class);
    
    @Autowired
    private IOrderService orderService;
    
    // @Scheduled(cron = "0 0/5 * * * ? ")
    @Scheduled(cron = "0 0 12 * * ? ")
    public void executeTask()
    {
        
        Thread current = Thread.currentThread();
        orderService.updateOrderContractAmountJob();
        logger.info("ScheduledTest.executeFileDownLoadTask 定时任务1:" + current.getId() + ",name:" + current.getName());
    }
    
}
