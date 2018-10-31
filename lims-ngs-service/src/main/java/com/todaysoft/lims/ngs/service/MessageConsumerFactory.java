package com.todaysoft.lims.ngs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.todaysoft.lims.ngs.ons.IMessageConsumer;
import com.todaysoft.lims.ngs.ons.NgsConsumer;
import com.todaysoft.lims.ngs.service.core.NgsCreateConsumer;
import com.todaysoft.lims.ngs.service.core.UnconcernedMessageConsumer;

@Component
public class MessageConsumerFactory {
	private static Logger log = LoggerFactory.getLogger(MessageConsumerFactory.class);

	@Autowired
	private NgsCreateConsumer ngsCreateEventHandler;
	@Autowired
	private UnconcernedMessageConsumer unconcernedEventHandler;

	public IMessageConsumer getConsumer(Message message, ConsumeContext context) {
		String tag = message.getTag();

		if (NgsConsumer.TAG_NGS_CREATE_MESSAGE.equals(tag)) {
			return ngsCreateEventHandler;
		} else {
			String body = new String(message.getBody());
			log.warn("Message is unconcerned, topic {}, tag {}, id {}, key {}, body {}.", message.getTopic(), tag, message.getMsgID(),
					message.getKey(), body);
			return unconcernedEventHandler;
		}
	}
}
