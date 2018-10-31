package com.todaysoft.lims.biology.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.lims.biology.adapter.impl.HttpRequestAPI;
import com.todaysoft.lims.biology.model.BiologyFamilyAnnotationMonitor;
import com.todaysoft.lims.biology.model.response.FamilyAnnotatioResponse;
import com.todaysoft.lims.biology.mybatis.mapper.BiologyFamilyAnnotationMonitorMapper;
import com.todaysoft.lims.biology.service.IBiologyAnnotationService;

@Component
public class BiologyFamilyAnnotationJob
{
    
    @Autowired
    private BiologyFamilyAnnotationMonitorMapper monitorMapper;
    
    @Autowired
    private IBiologyAnnotationService annotationService;
    
    private static Logger log = LoggerFactory.getLogger(BiologyFamilyAnnotationJob.class);
    
    //@Scheduled(cron = "0 0/3 * * * ? ")
    public void executeTask()
    {
        List<BiologyFamilyAnnotationMonitor> records = monitorMapper.searchUncompleteTasks();
        log.info("have many familyAnnotation need to do.size is:" + records.size());
        for (BiologyFamilyAnnotationMonitor monitor : records)
        {
            String taskId = monitor.getMonitorTaskId();
            FamilyAnnotatioResponse result = HttpRequestAPI.httpGetByTaskId(HttpRequestAPI.GET_FAMILY_ANNOTATION_URL, taskId, FamilyAnnotatioResponse.class);
            annotationService.familyResultCallBack(monitor, result);
        }
    }
}
