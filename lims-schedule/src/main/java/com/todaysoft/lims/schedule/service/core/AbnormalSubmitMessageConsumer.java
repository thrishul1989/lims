package com.todaysoft.lims.schedule.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.ITestingScheduleService;
import com.todaysoft.lims.schedule.service.core.event.AbnormalSolveSubmitEvent;
import com.todaysoft.lims.utils.StringUtils;

@Component
public class AbnormalSubmitMessageConsumer extends AbstractMessageConsumer<AbnormalSolveSubmitEvent>
{

    @Autowired
    private ITestingScheduleService testingScheduleService;
    
    @Override
    protected void handle(AbnormalSolveSubmitEvent event)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to handle the abnormal solve event, taskId {}", event.getTaskId());
        }
        String taskId = event.getTaskId();

        synchronized (this)
        {
            try {
                log.info("Received handle the abnormal solve plan, taskId {}", event.getTaskId());

                if(StringUtils.isNotBlank(event.getSemantic())) {//生信注释，新技术分析异常任务处理时，需要传递semantic(BIOLOGY-ANALY，TECHNICAL-ANALY)
                    testingScheduleService.updateAbnormalSolveForNewBiology(taskId,event.getSemantic());
                }else {
                    testingScheduleService.updateAbnormalSolve(taskId); 
                }
                if (log.isDebugEnabled())
                {
                    log.debug("Handle the abnormal solve event successful, taskId {}", taskId);
                } 
            }catch (Exception e) {
               log.error(e.getMessage(),e);
            }

        }
    } 
    
}
