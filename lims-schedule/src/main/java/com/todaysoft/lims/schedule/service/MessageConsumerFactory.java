package com.todaysoft.lims.schedule.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.todaysoft.lims.schedule.ons.IMessageConsumer;
import com.todaysoft.lims.schedule.ons.ScheduleConsumer;
import com.todaysoft.lims.schedule.service.core.AbnormalSubmitMessageConsumer;
import com.todaysoft.lims.schedule.service.core.ContractOrderTestingConfirmedMessageConsumer;
import com.todaysoft.lims.schedule.service.core.OpenReportGenerateMessageConsumer;
import com.todaysoft.lims.schedule.service.core.OrderCancelMessageConsumer;
import com.todaysoft.lims.schedule.service.core.OrderModifyMessageConsumer;
import com.todaysoft.lims.schedule.service.core.OrderPaymentConfirmedMessageConsumer;
import com.todaysoft.lims.schedule.service.core.OrderPaymentDelayedAgreedMessageConsumer;
import com.todaysoft.lims.schedule.service.core.OrderPaymentReducedAgreedMessageConsumer;
import com.todaysoft.lims.schedule.service.core.OrderSubmitMessageConsumer;
import com.todaysoft.lims.schedule.service.core.OrderTestingStartMessageConsumer;
import com.todaysoft.lims.schedule.service.core.OrderVerifyStartMessageConsumer;
import com.todaysoft.lims.schedule.service.core.ProgramMonitorNewBiologyConsumer;
import com.todaysoft.lims.schedule.service.core.ReportDeliverMessageConsumer;
import com.todaysoft.lims.schedule.service.core.ReportGenerateMessageConsumer;
import com.todaysoft.lims.schedule.service.core.ReportVerifyMessageConsumer;
import com.todaysoft.lims.schedule.service.core.SampleAbnormalSolveMessageConsumer;
import com.todaysoft.lims.schedule.service.core.SampleReceiveMessageConsumer;
import com.todaysoft.lims.schedule.service.core.SampleStoragedMessageConsumer;
import com.todaysoft.lims.schedule.service.core.ScheduleTaskActiveMessageConsumer;
import com.todaysoft.lims.schedule.service.core.StatusSearchCancelMessageConsumer;
import com.todaysoft.lims.schedule.service.core.SubmitSheetMessageConsumer;

@Component
public class MessageConsumerFactory {
	private static Logger log = LoggerFactory
			.getLogger(MessageConsumerFactory.class);

	@Autowired
	private OrderSubmitMessageConsumer orderSubmitEventHandler;

	@Autowired
	private OrderCancelMessageConsumer orderCancelEventHandler;

	@Autowired
	private OrderModifyMessageConsumer orderModifyEventHandler;

	@Autowired
	private OrderTestingStartMessageConsumer orderTestingStartEventHandler;

	@Autowired
	private SampleReceiveMessageConsumer sampleReceivedEventHandler;

	@Autowired
	private SampleStoragedMessageConsumer sampleStoragedEventHandler;

	@Autowired
	private SampleAbnormalSolveMessageConsumer sampleAbnormalSolveEventHandler;

	@Autowired
	private OrderPaymentConfirmedMessageConsumer paymentConfirmedEventHandler;

	@Autowired
	private OrderPaymentDelayedAgreedMessageConsumer orderPaymentDelayedAgreedEventHandler;

	@Autowired
	private OrderPaymentReducedAgreedMessageConsumer orderPaymentReducedAgreedEventHandler;

	@Autowired
	private ContractOrderTestingConfirmedMessageConsumer contractOrderTestingConfirmedEventHandler;

	@Autowired
	private ReportGenerateMessageConsumer reportGenerateEventHandler;

	@Autowired
	private ReportVerifyMessageConsumer reportVerifyEventHandler;

	@Autowired
	private ReportDeliverMessageConsumer reportDeliverEventHandler;

	@Autowired
	private SubmitSheetMessageConsumer submitSheetEventHandler;

	@Autowired
	private UnconcernedMessageConsumer unconcernedEventHandler;

	@Autowired
	private OrderVerifyStartMessageConsumer verifuScheduleEventHandler;

	@Autowired
	private AbnormalSubmitMessageConsumer abnormalSubmitEventHandler;

	@Autowired
	private StatusSearchCancelMessageConsumer statusSearchCancelEventHandler;

	@Autowired
	private OpenReportGenerateMessageConsumer openReportGenerateEventHandler;

	@Autowired
	private ScheduleTaskActiveMessageConsumer scheduleTaskActiveEventHandler;
	
	@Autowired
    private ProgramMonitorNewBiologyConsumer programMonitorNewBiologyEventHandler;

	public IMessageConsumer getConsumer(Message message, ConsumeContext context) {
		String tag = message.getTag();
		if (ScheduleConsumer.TAG_ORDER_SUBMIT.equals(tag)) {
			return orderSubmitEventHandler;
		} else if (ScheduleConsumer.TAG_ORDER_CANCEL.equals(tag)) {
			return orderCancelEventHandler;
		} else if (ScheduleConsumer.TAG_ORDER_MODIFY.equals(tag)) {
			return orderModifyEventHandler;
		} else if (ScheduleConsumer.TAG_ORDER_TESTING_START.equals(tag)) {
			return orderTestingStartEventHandler;
		} else if (ScheduleConsumer.TAG_ORDER_VERIFY_SCHEDULE_START.equals(tag)) {
			return verifuScheduleEventHandler;
		} else if (ScheduleConsumer.TAG_SAMPLE_RECEIVE.equals(tag)) {
			return sampleReceivedEventHandler;
		} else if (ScheduleConsumer.TAG_SAMPLE_STORAGE.equals(tag)) {
			return sampleStoragedEventHandler;
		} else if (ScheduleConsumer.TAG_SAMPLE_ABNORMAL_SOLVE.equals(tag)) {
			return sampleAbnormalSolveEventHandler;
		} else if (ScheduleConsumer.TAG_ORDER_PAYMENT_CONFIRM.equals(tag)) {
			return paymentConfirmedEventHandler;
		} else if (ScheduleConsumer.TAG_ORDER_PAYMENT_DELAY_AGREED.equals(tag)) {
			return orderPaymentDelayedAgreedEventHandler;
		} else if (ScheduleConsumer.TAG_ORDER_PAYMENT_REDUCE_AGREED.equals(tag)) {
			return orderPaymentReducedAgreedEventHandler;
		} else if (ScheduleConsumer.TAG_CONTRACT_ORDER_TESTING_CONFIRM
				.equals(tag)) {
			return contractOrderTestingConfirmedEventHandler;
		} else if (ScheduleConsumer.TAG_ORDER_REPORT_GENERATE.equals(tag)) {
			return reportGenerateEventHandler;
		} else if (ScheduleConsumer.TAG_ORDER_REPORT_VERIFY.equals(tag)) {
			return reportVerifyEventHandler;
		} else if (ScheduleConsumer.TAG_ORDER_REPORT_DELIVER.equals(tag)) {
			return reportDeliverEventHandler;
		} else if (ScheduleConsumer.TAG_SUBMIT_SHEET.equals(tag)) {
			return submitSheetEventHandler;
		} else if (ScheduleConsumer.TAG_ABNORMAL_SOLVE.equals(tag)) {
			return abnormalSubmitEventHandler;
		} else if (ScheduleConsumer.TAG_STATUS_SEARCH_CANCEL.equals(tag)) {
			return statusSearchCancelEventHandler;
		} else if (ScheduleConsumer.TAG_OPEN_REPORT_GENERATE.equals(tag)) {
			return openReportGenerateEventHandler;
		} else if (ScheduleConsumer.TAG_SCHEDULE_TASK_ACTIVE.equals(tag)) {
			return scheduleTaskActiveEventHandler;
		}else if (ScheduleConsumer.TAG_PROGRAM_MONITOR_NEW_BIOLOGY.equals(tag)) {
            return programMonitorNewBiologyEventHandler;
        }else {
			String body = new String(message.getBody());
			log.warn(
					"Message is unconcerned, topic {}, tag {}, id {}, key {}, body {}.",
					message.getTopic(), tag, message.getMsgID(),
					message.getKey(), body);
			return unconcernedEventHandler;
		}
	}
}
