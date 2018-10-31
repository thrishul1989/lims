package com.todaysoft.lims.biology.service;

import com.todaysoft.lims.biology.model.OrderIdModel;
import com.todaysoft.lims.biology.service.core.event.ScheduleTaskActiveEvent;
import com.todaysoft.lims.biology.service.core.event.TechnicalCreateEvent;

public interface IMessageSendService
{
    
    void sendActiveTaskMessage(ScheduleTaskActiveEvent activeEvent);
    
    void sendBiologyAnalysisCommentMessage(ScheduleTaskActiveEvent event);
    
    void sendDataSendMessage(OrderIdModel model);
    
    void sendConcludingReportMessage(OrderIdModel model);
    
    void sendGotoTechnicalMessage(TechnicalCreateEvent event);
    
}
