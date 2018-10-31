package com.todaysoft.lims.ngs.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.ngs.service.INgsTaskService;

@Component
public class NgsSequecingJob {

	@Autowired
	private INgsTaskService ngsTaskService;

	@Scheduled(cron = "0 0/5 * * * ? ")
	public void executeTask() {
		
		ngsTaskService.sequecingState();

	}
}
