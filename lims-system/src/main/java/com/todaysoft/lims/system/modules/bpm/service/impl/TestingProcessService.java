package com.todaysoft.lims.system.modules.bpm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.lims.system.modules.bpm.model.StartOrderTestingRequest;
import com.todaysoft.lims.system.modules.bpm.model.process.BiAnalysisTask;
import com.todaysoft.lims.system.modules.bpm.model.process.TechnicalAnalysisTask;
import com.todaysoft.lims.system.modules.bpm.model.process.searcher.BiAnalysisTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.model.process.searcher.OnTestingTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.model.process.searcher.TechnicalAnalysisTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.service.ITestingProcessService;
import com.todaysoft.lims.system.service.impl.GatewayService;

@Service
public class TestingProcessService implements ITestingProcessService
{
    @Autowired
    private RestTemplate template;
    
    //生物信息分析
    @Override
    public List<BiAnalysisTask> biAnalysisTasks(BiAnalysisTaskSearcher searcher)
    {
        List<BiAnalysisTask> tasks = new ArrayList<BiAnalysisTask>();
        
        BiAnalysisTask task;
        
        for (int i = 0; i < 100; i++)
        {
            task = new BiAnalysisTask();
            task.setId(String.valueOf(i));
            task.setOnTestingCode("sj0000" + i);
            tasks.add(task);
        }
        
        return tasks;
    }
    
    //技术分析
    @Override
    public List<TechnicalAnalysisTask> technicalAnalysisTasks(TechnicalAnalysisTaskSearcher searcher)
    {
        List<TechnicalAnalysisTask> tasks = new ArrayList<TechnicalAnalysisTask>();
        
        TechnicalAnalysisTask task;
        
        for (int i = 0; i < 100; i++)
        {
            task = new TechnicalAnalysisTask();
            task.setId(String.valueOf(i));
            task.setMethod("检查方法" + i);
            task.setSampleCode("技术分析" + i);
            tasks.add(task);
        }
        
        return tasks;
    }
    
    @Override
    public String startProcess(String id)
    {
        StartOrderTestingRequest request = new StartOrderTestingRequest();
        request.setId(id);
        String url = GatewayService.getServiceUrl("/bpm/testing/order/start");
        return template.postForObject(url, request, String.class);
    }
    
//    @Override
//    public List<OnTestingTask> onTestingTasks(OnTestingTaskSearcher searcher)
//    {
//        return null;
//    }
}