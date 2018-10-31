package com.todaysoft.lims.ngs.service;

import com.todaysoft.lims.ngs.service.core.event.ScheduleTaskActiveEvent;

public interface IMessageSendService {

	void sendActiveTaskMessage(ScheduleTaskActiveEvent activeEvent);

}
