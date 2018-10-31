package com.todaysoft.lims.maintain.job;

import com.todaysoft.lims.maintain.testing.service.ITestingOptimizeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ReportCheckJob {

	private static Logger log = LoggerFactory.getLogger(ReportCheckJob.class);

	@Autowired
	private ITestingOptimizeService optimizeService;

	@Scheduled(cron = "0 0 1,13 * * ?")
	public void executeTask() {
		log.info("now time is:"+new Date().toString());
		optimizeService.validateData();
	}
}
