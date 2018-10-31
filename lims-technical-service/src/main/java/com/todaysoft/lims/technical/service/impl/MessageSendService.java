package com.todaysoft.lims.technical.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.google.gson.Gson;
import com.todaysoft.lims.technical.config.Configs;
import com.todaysoft.lims.technical.model.TaskSemantic;
import com.todaysoft.lims.technical.model.VariableModel;
import com.todaysoft.lims.technical.model.request.ReportEvent;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalysisTask;
import com.todaysoft.lims.technical.service.IMessageSendService;
import com.todaysoft.lims.technical.service.core.event.ProgramMonitorNewBiologyEvent;
import com.todaysoft.lims.technical.service.core.event.ScheduleTaskActiveEvent;
import com.todaysoft.lims.technical.utils.Collections3;

@Service
public class MessageSendService implements IMessageSendService
{
    
    @Autowired
    private Configs configs;
    
    @Resource(name = "producer")
    private Producer producer;
    
    public static final String TAG_SCHEDULE_TASK_ACTIVE = "SCHEDULE_TASK_ACTIVE";// 流程任务激活
    
    public static final String TAG_PROGRAM_MONITOR_NEW_BIOLOGY = "PROGRAM_MONITOR_NEW_BIOLOGY";      //技术分析完成消息，用于项目监控
    
    private static final Logger logger = LoggerFactory.getLogger(MessageSendService.class);
    
    @Override
    public void sendActiveTaskMessage(ScheduleTaskActiveEvent activeEvent)
    {
        logger.info("send new technical task schedule active.");
        Message msg = new Message(configs.getAliyunONSTopic(), TAG_SCHEDULE_TASK_ACTIVE, new Gson().toJson(activeEvent).getBytes());
        SendResult sendResult = producer.send(msg);
    }
    
    @Override
    public void sendReportMessage(VariableModel model)
    {
        ReportEvent event = new ReportEvent();
        event.setModel(model);
        Message msg = new Message(configs.getAliyunONSTopic(), "REPORT", new Gson().toJson(event).getBytes());
        SendResult sendResult = producer.send(msg);
        logger.info("step1:生成报告基础数据消息成功！！！" + sendResult.getMessageId());
    }
    
    @Override
    public void sendProgramMonitorNewBiologyMessage(List<TechnicalAnalysisTask> analysisTasks)
    {
        if (Collections3.isNotEmpty(analysisTasks))
        {
            for (TechnicalAnalysisTask task : analysisTasks)
            {
                ProgramMonitorNewBiologyEvent programMonitorNewBiologyEvent=new ProgramMonitorNewBiologyEvent();
                programMonitorNewBiologyEvent.setTaskId(task.getId());
                programMonitorNewBiologyEvent.setTaskSemantic(TaskSemantic.TECHNICAL_ANALY);
                logger.info("技术复核完成，send new biology monitor message.{}",programMonitorNewBiologyEvent);
                Message programMonitorMsg =
                    new Message(configs.getAliyunONSTopic(), MessageSendService.TAG_PROGRAM_MONITOR_NEW_BIOLOGY, new Gson().toJson(programMonitorNewBiologyEvent).getBytes());
                producer.send(programMonitorMsg);
            }
            
        }
    }
    
}
