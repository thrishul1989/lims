package com.todaysoft.lims.biology.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.biology.adapter.impl.HttpRequestAPI;
import com.todaysoft.lims.biology.model.BiologyAnnotationMonitor;
import com.todaysoft.lims.biology.model.api.annotationcallback.BiologyReAnalysisDataResponse;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyAnnotationMonitorMapper;
import com.todaysoft.lims.biology.service.IBiologyAnnotationService;

@Component
public class BiologyAnnotationJob
{
    
    @Autowired
    private BiologyAnnotationMonitorMapper monitorMapper;
    
    @Autowired
    private IBiologyAnnotationService annotationService;
    
   // @Scheduled(cron = "0 0/2 * * * ? ")
    public void executeTask()
    {
        List<BiologyAnnotationMonitor> records = monitorMapper.searchUncompleteTasks();
        for (BiologyAnnotationMonitor monitor : records)
        {
            String taskId = monitor.getMonitorTaskId();
            BiologyReAnalysisDataResponse result =
                HttpRequestAPI.oldBiologyHttpGetByTaskId(HttpRequestAPI.GET_RESULT_BIOLOGY_URL, taskId, BiologyReAnalysisDataResponse.class);
            annotationService.resultCallBack(monitor, result);
        }
    }
}
