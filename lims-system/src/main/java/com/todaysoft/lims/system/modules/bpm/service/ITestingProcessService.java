package com.todaysoft.lims.system.modules.bpm.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.model.process.BiAnalysisTask;
import com.todaysoft.lims.system.modules.bpm.model.process.TechnicalAnalysisTask;
import com.todaysoft.lims.system.modules.bpm.model.process.searcher.BiAnalysisTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.model.process.searcher.OnTestingTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.model.process.searcher.TechnicalAnalysisTaskSearcher;

public interface ITestingProcessService
{
//    List<OnTestingTask> onTestingTasks(OnTestingTaskSearcher searcher);//上机实验
    
    List<BiAnalysisTask> biAnalysisTasks(BiAnalysisTaskSearcher searcher);//生信分析
    
    List<TechnicalAnalysisTask> technicalAnalysisTasks(TechnicalAnalysisTaskSearcher searcher);//技术分析
    
    String startProcess(String id);
}
