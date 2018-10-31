package com.todaysoft.lims.ngs.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.lims.ngs.ons.AbstractMessageConsumer;
import com.todaysoft.lims.ngs.service.IMessageSendService;
import com.todaysoft.lims.ngs.service.INgsTaskService;
import com.todaysoft.lims.ngs.service.core.event.NgsCreateEvent;
import com.todaysoft.lims.ngs.service.core.event.ScheduleTaskActiveEvent;

@Component
public class NgsCreateConsumer extends AbstractMessageConsumer<NgsCreateEvent>
{
    
    private static final String TASK_REFER = "NGS_SEQUECING_TASK";
    
    @Autowired
    private INgsTaskService ngsTaskService;
    
    @Autowired
    private IMessageSendService messageSendService;
    
    @Override
    @Transactional(rollbackFor = {Exception.class})
    protected void handle(NgsCreateEvent event)
    {
        // 生成上机测序任务
        
        String taskId = ngsTaskService.createNgsSequecingTask(event);
        
        // 发送激活任务消息
        ScheduleTaskActiveEvent activeEvent = new ScheduleTaskActiveEvent();
        activeEvent.setPreTaskId(event.getQtTaskId());
        activeEvent.setTaskId(taskId);
        activeEvent.setTaskRefer(TASK_REFER);
        messageSendService.sendActiveTaskMessage(activeEvent);
        
    }
    
}
