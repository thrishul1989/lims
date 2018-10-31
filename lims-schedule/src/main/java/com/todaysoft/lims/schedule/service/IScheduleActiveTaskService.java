package com.todaysoft.lims.schedule.service;

import com.todaysoft.lims.schedule.service.core.event.ScheduleTaskActiveEvent;

public interface IScheduleActiveTaskService {

	void activeTask(ScheduleTaskActiveEvent event);

}
