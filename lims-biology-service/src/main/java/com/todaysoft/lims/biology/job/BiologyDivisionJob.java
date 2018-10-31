package com.todaysoft.lims.biology.job;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.biology.adapter.impl.HttpRequestAPI;
import com.todaysoft.lims.biology.model.BiologyDivisionMonitor;
import com.todaysoft.lims.biology.model.api.divisioncallback.BiologyDivisionCallBackModel;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyDivisionMonitorMapper;
import com.todaysoft.lims.biology.service.IBiologyAnnotationService;
import com.todaysoft.lims.biology.service.IBiologyService;
import com.todaysoft.lims.biology.service.IMessageSendService;
import com.todaysoft.lims.biology.service.core.event.ScheduleTaskActiveEvent;
import com.todaysoft.lims.biology.util.DateUtil;
import com.todaysoft.lims.utils.StringUtils;

@Component
public class BiologyDivisionJob
{
    
    @Autowired
    private BiologyDivisionMonitorMapper monitorMapper;
    
    @Autowired
    private IBiologyService biologyService;
    
    @Autowired
    private IBiologyAnnotationService annotationService;
    
    @Autowired
    private IMessageSendService messageSendService;
    
    private static Logger log = LoggerFactory.getLogger(BiologyDivisionJob.class);
    
    private static String tag = "old";
    
    @Value("${mygeno.method.tag}")
    public void set(String tag)
    {
        this.tag = tag;
        System.out.println("tag:" + tag);
    }
    
    @Scheduled(cron = "0 0/2 * * * ? ")
    public void executeTask()
    {
        //定时查询 超过一天还没结束的的拆分任务  接口还没调试  暂时去掉一天的逻辑 直接查询未完成的
        //		Date oneDayAgo = DateUtil.getSpecifiedDayBefore(new Date(),1);
        String date = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        List<BiologyDivisionMonitor> records = monitorMapper.searchUncompleteTasks(date);
        log.info("waiting division task size is:" + records.size());
        for (BiologyDivisionMonitor monitor : records)
        {
            String taskId = monitor.getTaskId();
            BiologyDivisionCallBackModel result = HttpRequestAPI.getResultByTaskId(taskId);
            
            if ("new".equals(tag))
            {
                String taskReturnId = biologyService.resultCallBack(result);
                if (StringUtils.isNotEmpty(taskReturnId))
                {
                    // 生成注释任务
                    log.info("start create annotation task.");
                    annotationService.createAnnotationTask(taskReturnId);
                    log.info("create annotation task success.");
                }
            }
            else if ("old".equals(tag))
            {
                String taskReturnId = biologyService.resultCallBackTemp(result);
                if (StringUtils.isNotEmpty(taskReturnId))
                {
                    log.info("send create biologyAnalysis message!!!");
                    ScheduleTaskActiveEvent event = new ScheduleTaskActiveEvent();
                    event.setTaskId(taskReturnId);
                    messageSendService.sendBiologyAnalysisCommentMessage(event);
                    log.info("divide success,go to old biologyAnalysis!!!");
                }
            }
            
        }
    }
}
