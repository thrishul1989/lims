package com.todaysoft.lims.technical.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;

import com.todaysoft.lims.technical.ons.IMessageConsumer;
import com.todaysoft.lims.technical.ons.TechnicalListen;
import com.todaysoft.lims.technical.service.core.TechnicalCreateConsumer;
import com.todaysoft.lims.technical.service.core.UnconcernedMessageConsumer;

@Component
public class MessageConsumerFactory {
	private static Logger log = LoggerFactory.getLogger(MessageConsumerFactory.class);

	@Autowired
	private TechnicalCreateConsumer ngsCreateEventHandler;

	@Autowired
	private UnconcernedMessageConsumer unconcernedEventHandler;

	public IMessageConsumer getConsumer(Message message, ConsumeContext context) {
		String tag = message.getTag();
		return ngsCreateEventHandler;

	}
}
