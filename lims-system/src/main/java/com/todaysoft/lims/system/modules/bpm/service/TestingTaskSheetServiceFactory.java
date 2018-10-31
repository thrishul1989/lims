package com.todaysoft.lims.system.modules.bpm.service;

import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.impl.BiologyAnnotationService;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.TechnicalAnalyService;
import org.springframework.context.annotation.Configuration;

import com.todaysoft.lims.system.config.RootContext;
import com.todaysoft.lims.system.modules.bpm.bioanalysis.service.impl.BiologyAnalyService;
import com.todaysoft.lims.system.modules.bpm.ontest.service.impl.SequencingService;
import com.todaysoft.lims.system.modules.bpm.service.impl.TestingTestTasksService;
import com.todaysoft.lims.utils.StringUtils;

@Configuration
public class TestingTaskSheetServiceFactory {

	public ITestingTaskSheetService getSerivceByTaskRefer(String taskRefer) {
		if (StringUtils.isNotEmpty(taskRefer)) {
			switch (taskRefer) {
			case "NGS_SEQUECING_TASK":
				ITestingTaskSheetService ngsService = RootContext.getBean(SequencingService.class);
				return ngsService;
			case "BIOLOGY_DIVISION_TASK":
				BiologyAnalyService biologyService = RootContext.getBean(BiologyAnalyService.class);
				return biologyService;
			case "BIOLOGY-ANNOTATION":
				BiologyAnnotationService annotationService = RootContext.getBean(BiologyAnnotationService.class);
				return annotationService;
			case "TECHNICAL-ANALYSIS":
				TechnicalAnalyService analysisService = RootContext.getBean(TechnicalAnalyService.class);
				return analysisService;
			}
		}
		return RootContext.getBean(TestingTestTasksService.class);
	}
}
