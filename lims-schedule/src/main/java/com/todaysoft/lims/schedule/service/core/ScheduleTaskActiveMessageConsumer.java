package com.todaysoft.lims.schedule.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.schedule.ons.AbstractMessageConsumer;
import com.todaysoft.lims.schedule.service.IScheduleActiveTaskService;
import com.todaysoft.lims.schedule.service.ITestingScheduleService;
import com.todaysoft.lims.schedule.service.core.event.ScheduleTaskActiveEvent;

@Component
public class ScheduleTaskActiveMessageConsumer extends AbstractMessageConsumer<ScheduleTaskActiveEvent> {

	@Autowired
	private IScheduleActiveTaskService scheduleActiveTaskService;

	@Override
	protected void handle(ScheduleTaskActiveEvent event) {
		if (log.isDebugEnabled()) {
			log.debug("NEW_TASK_CREATE_START");
		}

		scheduleActiveTaskService.activeTask(event);

		
		if (log.isDebugEnabled()) {
			log.debug("NEW_TASK_CREATE_START successful, orderId {} ,productId {}");
		}

	}

}
