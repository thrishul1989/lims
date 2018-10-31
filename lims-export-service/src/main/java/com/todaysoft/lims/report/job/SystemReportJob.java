package com.todaysoft.lims.report.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.report.service.ISystemReportService;

@Component
public class SystemReportJob
{
    @Autowired
    private ISystemReportService systemReportService;
    
    @Scheduled(cron = "0 0 0 1/3 * ? ")
    public void execute()
    {
        System.out.println("-------->>>start：开始清空系统报表缓存数据----------");
        systemReportService.truncateSystemReport();
        System.out.println("<<<--------end：系统报表缓存数据清空结束 ----------");
    }
}
