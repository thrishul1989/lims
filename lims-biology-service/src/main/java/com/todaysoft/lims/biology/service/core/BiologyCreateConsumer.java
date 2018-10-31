package com.todaysoft.lims.biology.service.core;

import com.todaysoft.lims.biology.service.IBiologyService;
import com.todaysoft.lims.biology.service.IMessageSendService;
import com.todaysoft.lims.biology.service.core.event.ScheduleTaskActiveEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.biology.ons.AbstractMessageConsumer;
import com.todaysoft.lims.biology.service.core.event.BiologyCreateEvent;


@Component
public class BiologyCreateConsumer extends AbstractMessageConsumer<BiologyCreateEvent>{

	@Autowired
	private IBiologyService biologyService;

	@Autowired
	private IMessageSendService messageSendService;

	private static final String TASK_REFER = "BIOLOGY_DIVISION_TASK";

	@Override
	protected void handle(BiologyCreateEvent event) {
		log.info("Start to handle the biology task create event,  sequencingCode is {}", event.getSequencingCode());

		String taskId = biologyService.insert(event);

		// 发送激活任务消息
		ScheduleTaskActiveEvent activeEvent = new ScheduleTaskActiveEvent();
		activeEvent.setPreTaskId(event.getTaskId());
		activeEvent.setTaskId(taskId);
		activeEvent.setTaskRefer(TASK_REFER);
		messageSendService.sendActiveTaskMessage(activeEvent);

		log.info("handle the biology task create event successful,  sequencingCode is {}", event.getSequencingCode());
	}

}
