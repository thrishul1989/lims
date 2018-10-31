package com.todaysoft.lims.testing.resampling.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.testing.base.action.RequestHeaders;
import com.todaysoft.lims.testing.base.entity.TestingSchedule;
import com.todaysoft.lims.testing.base.model.TaskConfig;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.base.service.ITestingScheduleService;
import com.todaysoft.lims.testing.ons.EventPublisher;
import com.todaysoft.lims.testing.resampling.model.ResamplingCancelRecordHandleRequest;
import com.todaysoft.lims.testing.resampling.model.ResamplingSchedule;
import com.todaysoft.lims.testing.resampling.service.IResamplingService;
import com.todaysoft.lims.utils.StringUtils;

@RestController
@RequestMapping("/bpm/testing/resampling")
public class ResamplingController {
	@Autowired
	private IResamplingService service;

	@Autowired
	private ITestingScheduleService testingScheduleService;
	@Autowired
	private EventPublisher eventPublisher;

	@RequestMapping(value = "/{id}/schedules", method = RequestMethod.GET)
	public List<ResamplingSchedule> schedules(@PathVariable String id) {
		return service.getSchedules(id);
	}

	@RequestMapping(value = "/cancel_handle", method = RequestMethod.POST)
	public void handleCancelRecord(@RequestBody ResamplingCancelRecordHandleRequest request, @RequestHeader(RequestHeaders.MEMBER_TOKEN) String token) {
		VariableModel model = new VariableModel();
		service.handleCancelRecord(request, token, model);
		if (StringUtils.isNotEmpty(model.getScheduleIds())) {
			testingScheduleService.sendReportMessage(model);

			TestingSchedule schedule = testingScheduleService.getById(model.getScheduleIds().split("\\,")[0]);
			if (null != schedule) {
				// 发送订单取消消息
				eventPublisher.fireOrderIsCancel(schedule.getOrderId());
			}

		}
	}

	@RequestMapping(value = "/{id}/cancel_risk_testing_node", method = RequestMethod.GET)
	public List<TaskConfig> getRiskTestingNode(@PathVariable String id) {
		return service.getRiskTestingNode(id);
	}
}
