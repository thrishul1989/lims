package com.todaysoft.lims.technical.service.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.technical.mybatis.TechnicalAnalysisTaskMapper;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalysisTask;
import com.todaysoft.lims.technical.ons.AbstractMessageConsumer;
import com.todaysoft.lims.technical.service.IMessageSendService;
import com.todaysoft.lims.technical.service.ITechnicalService;
import com.todaysoft.lims.technical.service.core.event.ScheduleTaskActiveEvent;
import com.todaysoft.lims.technical.service.core.event.TechnicalCreateEvent;

@Component
public class TechnicalCreateConsumer extends AbstractMessageConsumer<TechnicalCreateEvent>
{
    
    @Autowired
    private IMessageSendService messageSendService;
    
    private static final String TASK_REFER = "TECHNICAL-ANALYSIS";
    
    private static final String TASK_REFER_OLD = "TECHNICAL-ANALY";
    
    @Autowired
    private ITechnicalService technicalService;
    
    @Autowired
    private TechnicalAnalysisTaskMapper technicalAnalysisTaskMapper;
    
    private static final Logger logger = LoggerFactory.getLogger(TechnicalCreateConsumer.class);
    
    @Override
    protected void handle(TechnicalCreateEvent event)
    {
        // 生成数据分析任务
        logger.info("new technical task create.biologyTaskId is:" + event.getBiologyTaskId() + ".dataCode is:" + event.getDataCode());
        String taskId = technicalService.createTechnicalTask(event);
        logger.info("create technical task success.taskId is:" + taskId);
        // 发送激活任务消息
        ScheduleTaskActiveEvent activeEvent = new ScheduleTaskActiveEvent();
        activeEvent.setPreTaskId(event.getBiologyTaskId());
        activeEvent.setTaskId(taskId);
        TechnicalAnalysisTask task = technicalAnalysisTaskMapper.selectByPrimaryKey(taskId);//只有在新技术分析的时候才给它赋值
        if (task != null)
        {
            activeEvent.setTaskRefer(TASK_REFER);
        }
        else
        { //老的技术分析
            activeEvent.setTaskRefer(TASK_REFER_OLD);
        }
        messageSendService.sendActiveTaskMessage(activeEvent);
        
    }
}
