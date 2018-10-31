package com.todaysoft.lims.system.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.system.modules.bpm.report.service.IReportService;

@Component
public class ReportCompleteJob
{
    
    private static Logger logger = LoggerFactory.getLogger(ReportCompleteJob.class);
    
    @Autowired
    private IReportService service;
    
    @Scheduled(cron = "0 0/5 * * * ? ")
    public void executeTask()
    {
        
        Thread current = Thread.currentThread();
        service.updateReportStateJob();
        logger.info("ReportCompleteJob.executeSearchFindReportTask 定时任务1:" + current.getId() + ",name:" + current.getName());
    }
    
}
