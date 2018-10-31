package com.todaysoft.lims.technical.service;

import java.util.List;

import com.todaysoft.lims.technical.model.VariableModel;
import com.todaysoft.lims.technical.mybatis.entity.TechnicalAnalysisTask;
import com.todaysoft.lims.technical.service.core.event.ScheduleTaskActiveEvent;

public interface IMessageSendService
{
    
    void sendActiveTaskMessage(ScheduleTaskActiveEvent activeEvent);
    
    void sendReportMessage(VariableModel model);

    void sendProgramMonitorNewBiologyMessage(List<TechnicalAnalysisTask> analysisTasks);
    
}
