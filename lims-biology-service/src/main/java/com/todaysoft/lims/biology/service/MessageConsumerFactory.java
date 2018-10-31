package com.todaysoft.lims.biology.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.todaysoft.lims.biology.ons.IMessageConsumer;
import com.todaysoft.lims.biology.ons.BiologyConsumer;
import com.todaysoft.lims.biology.service.core.BiologyCreateConsumer;
import com.todaysoft.lims.biology.service.core.UnconcernedMessageConsumer;

@Component
public class MessageConsumerFactory {
	private static Logger log = LoggerFactory.getLogger(MessageConsumerFactory.class);

	@Autowired
	private BiologyCreateConsumer biologyCreateEventHandler;
	@Autowired
	private UnconcernedMessageConsumer unconcernedEventHandler;

	public IMessageConsumer getConsumer(Message message, ConsumeContext context) {
		String tag = message.getTag();

		if (BiologyConsumer.TAG_BIOLOGY_CREATE_MESSAGE.equals(tag)) {
			return biologyCreateEventHandler;
		} else {
			String body = new String(message.getBody());
			log.warn("Message is unconcerned, topic {}, tag {}, id {}, key {}, body {}.", message.getTopic(), tag, message.getMsgID(),
					message.getKey(), body);
			return unconcernedEventHandler;
		}
	}
}
