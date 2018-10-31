package com.todaysoft.lims.system.modules.bpm.extrasample.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bpm.extrasample.model.ExtraSampleTask;
import com.todaysoft.lims.system.modules.bpm.extrasample.model.ExtraSampleTaskDetails;
import com.todaysoft.lims.system.modules.bpm.extrasample.model.ExtraSampleTaskHandleRequest;
import com.todaysoft.lims.system.modules.bpm.extrasample.model.ExtraSampleTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.extrasample.service.IExtraSampleService;
import com.todaysoft.lims.system.modules.bpm.extrasample.service.request.ExtraSampleTaskPagingRequest;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class ExtraSampleService extends RestService implements IExtraSampleService
{
    @Override
    public Pagination<ExtraSampleTask> paging(ExtraSampleTaskSearcher searcher, int pageNo, int pageSize)
    {
        ExtraSampleTaskPagingRequest request = new ExtraSampleTaskPagingRequest();
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        BeanUtils.copyProperties(searcher, request);
        String url = GatewayService.getServiceUrl("/bpm/extraSample/tasks");
        ResponseEntity<Pagination<ExtraSampleTask>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ExtraSampleTaskPagingRequest>(request),
                new ParameterizedTypeReference<Pagination<ExtraSampleTask>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public ExtraSampleTaskDetails getDetails(String id)
    {
        String url = GatewayService.getServiceUrl("/bpm/extraSample/tasks/{id}");
        return template.getForObject(url, ExtraSampleTaskDetails.class, id);
    }
    
    @Override
    public void handle(ExtraSampleTaskHandleRequest request)
    {
        String url = GatewayService.getServiceUrl("/bpm/extraSample/tasks/handle");
        template.postForLocation(url, request);
    }
}
