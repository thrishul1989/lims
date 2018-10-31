package com.todaysoft.lims.ngs.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.google.gson.Gson;
import com.todaysoft.lims.ngs.config.Configs;
import com.todaysoft.lims.ngs.service.IMessageSendService;
import com.todaysoft.lims.ngs.service.core.event.ScheduleTaskActiveEvent;





@Service
public class MessageSendService implements IMessageSendService {

	
	@Autowired
	private Configs configs;
	
	@Resource(name = "producer")
	private Producer producer;
	
	public static final String TAG_SCHEDULE_TASK_ACTIVE = "SCHEDULE_TASK_ACTIVE";// 流程任务激活
	 
	public static final String TAG_BIOLOGY_CREATE_MESSAGE = "BIOLOGY_CREATE_MESSAGE";// 下个节点创建消息
	
	public static final String TAG_PROGRAM_MONITOR_NEW_BIOLOGY = "PROGRAM_MONITOR_NEW_BIOLOGY";      //上机测序完成消息，用于项目监控
	
	@Override
	public void sendActiveTaskMessage(ScheduleTaskActiveEvent activeEvent) {
		// TODO Auto-generated method stub
		Message msg = new Message(configs.getAliyunONSTopic(), TAG_SCHEDULE_TASK_ACTIVE, new Gson().toJson(
				activeEvent).getBytes());
		SendResult sendResult = producer.send(msg);
	}

}
