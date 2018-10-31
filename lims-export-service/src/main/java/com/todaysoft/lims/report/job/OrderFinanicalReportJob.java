package com.todaysoft.lims.report.job;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.report.service.IFinanceReportService;

@Component
public class OrderFinanicalReportJob
{
    
    @Autowired
    private IFinanceReportService service;
    
    private Logger logger = Logger.getLogger(this.getClass());
    
    /**
     * 每隔三天的凌晨1点执行清表数据
     */
    
    @Scheduled(cron = "0 0 1 1/3 * ?")
    public void executeOrder()
    {
        logger.info("--------开始清空订单相关报表数据----------");
        service.delete();
        logger.info("--------结束清空----------");
    }
}
