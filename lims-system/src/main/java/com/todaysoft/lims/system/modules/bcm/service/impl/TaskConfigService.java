package com.todaysoft.lims.system.modules.bcm.service.impl;

import com.todaysoft.lims.system.model.vo.TaskConfig;
import com.todaysoft.lims.system.modules.bcm.model.TaskConfigSimpleModel;
import com.todaysoft.lims.system.modules.bcm.service.ITaskConfigService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskConfigService extends RestService implements ITaskConfigService
{
    @Override
    public List<TaskConfigSimpleModel> getPretestingTaskConfigsByInputSample(String inputSampleId)
    {
        String url = GatewayService.getServiceUrl("/bcm/task/pretesting/input/{inputSampleId}");
        ResponseEntity<List<TaskConfigSimpleModel>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TaskConfigSimpleModel>>()
            {
            }, inputSampleId);
        return exchange.getBody();
    }

    @Override
    public List<TaskConfig> getTaskList() {
        String url = GatewayService.getServiceUrl("/bcm/task/taskList");
        ResponseEntity<List<TaskConfig>> exchange =
                template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TaskConfig>>()
                {
                });
        return exchange.getBody();
    }

    @Override
    public TaskConfig getTaskBySemantic(String semantic) {
        String url = GatewayService.getServiceUrl("/bcm/task/getTaskBySemantic/{semantic}");
        ResponseEntity<TaskConfig> exchange =
                template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<TaskConfig>()
                {
                }, semantic);
        return exchange.getBody();
    }
}
