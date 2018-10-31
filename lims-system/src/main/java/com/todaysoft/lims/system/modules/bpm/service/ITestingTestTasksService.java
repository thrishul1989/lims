package com.todaysoft.lims.system.modules.bpm.service;

import java.util.List;

import com.todaysoft.lims.system.model.searcher.SampleExperimentRequest;
import com.todaysoft.lims.system.model.searcher.SampleTestingExportSearch;
import com.todaysoft.lims.system.modules.bpm.model.TestTask;
import com.todaysoft.lims.system.modules.bpm.model.TestTaskSearcher;

public interface ITestingTestTasksService
{
    List<TestTask> testTasks(TestTaskSearcher searcher);//实验

    String exportSampleTesting(SampleExperimentRequest searcher);

    String exportTaskSheet(SampleExperimentRequest searcher);

    String exportTaskSucessRate(SampleExperimentRequest searcher);

    String exportFailTasks(SampleExperimentRequest searcher);
}
