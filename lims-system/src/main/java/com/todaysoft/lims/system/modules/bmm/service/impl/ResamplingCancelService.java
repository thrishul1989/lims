package com.todaysoft.lims.system.modules.bmm.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.vo.TaskConfig;
import com.todaysoft.lims.system.modules.bmm.model.ResamplingCancelRecord;
import com.todaysoft.lims.system.modules.bmm.model.ResamplingCancelRecordDetails;
import com.todaysoft.lims.system.modules.bmm.model.ResamplingCancelRecordHandleForm;
import com.todaysoft.lims.system.modules.bmm.model.ResamplingCancelRecordSearcher;
import com.todaysoft.lims.system.modules.bmm.model.ResamplingSchedule;
import com.todaysoft.lims.system.modules.bmm.service.IResamplingCancelService;
import com.todaysoft.lims.system.modules.bmm.service.request.ResamplingCancelRecordHandleRequest;
import com.todaysoft.lims.system.modules.bmm.service.request.ResamplingCancelRecordPagingRequest;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class ResamplingCancelService extends RestService implements IResamplingCancelService
{
    @Override
    public Pagination<ResamplingCancelRecord> paging(ResamplingCancelRecordSearcher searcher, int pageNo, int pageSize)
    {
        ResamplingCancelRecordPagingRequest request = new ResamplingCancelRecordPagingRequest();
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        request.setOrderCode(searcher.getOrderCode());
        request.setSampleCode(searcher.getSampleCode());
        request.setDataAuthoritySearcher(searcher.getDataAuthoritySearcher());
        request.setProjectManager(searcher.getProjectManager());
        String url = GatewayService.getServiceUrl("/bmm/resampling/cancel_list");
        ResponseEntity<Pagination<ResamplingCancelRecord>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ResamplingCancelRecordPagingRequest>(request),
                new ParameterizedTypeReference<Pagination<ResamplingCancelRecord>>()
                {
                });
        
        return exchange.getBody();
    }
    
    @Override
    public ResamplingCancelRecordDetails getDetails(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/resampling/cancel_details/{id}");
        ResamplingCancelRecordDetails details = template.getForObject(url, ResamplingCancelRecordDetails.class, id);
        
        url = GatewayService.getServiceUrl("/bpm/testing/resampling/{id}/schedules");
        ResponseEntity<List<ResamplingSchedule>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ResamplingSchedule>>()
            {
            }, id);
        List<ResamplingSchedule> schedules = exchange.getBody();
        
        url = GatewayService.getServiceUrl("/bpm/testing/resampling/{id}/cancel_risk_testing_node");
        List<TaskConfig> riskTestingNodes = template.getForObject(url, List.class, id);
        
        details.setRiskTestingNodes(riskTestingNodes);
        details.setSchedules(schedules);
        return details;
    }
    
    @Override
    public void handle(ResamplingCancelRecordHandleForm data)
    {
        
        ResamplingCancelRecordHandleRequest request = new ResamplingCancelRecordHandleRequest();
        request.setId(data.getId());
        request.setStrategy(data.getStrategy());
        
        request.setRemark(data.getRemark());
        String url = GatewayService.getServiceUrl("/bpm/testing/resampling/cancel_handle");
        template.postForLocation(url, request);
        
    }

    @Override
    public Pagination<ResamplingCancelRecord> solvePaging(ResamplingCancelRecordSearcher searcher, int pageNo, int pageSize)
    {
        ResamplingCancelRecordPagingRequest request = new ResamplingCancelRecordPagingRequest();
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        request.setOrderCode(searcher.getOrderCode());
        request.setSampleCode(searcher.getSampleCode());
        request.setDataAuthoritySearcher(searcher.getDataAuthoritySearcher());
        request.setProjectManager(searcher.getProjectManager());
        
        String url = GatewayService.getServiceUrl("/bmm/resampling/cancel_list_solve");
        ResponseEntity<Pagination<ResamplingCancelRecord>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ResamplingCancelRecordPagingRequest>(request),
                new ParameterizedTypeReference<Pagination<ResamplingCancelRecord>>()
                {
                });
        
        return exchange.getBody();
    }

    @Override
    public ResamplingCancelRecordDetails getSolveDetails(String id)
    {
        String url = GatewayService.getServiceUrl("/bmm/resampling/cancel_solve_details/{id}");
        ResamplingCancelRecordDetails details = template.getForObject(url, ResamplingCancelRecordDetails.class, id);
        
        url = GatewayService.getServiceUrl("/bpm/testing/resampling/{id}/schedules");
        ResponseEntity<List<ResamplingSchedule>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ResamplingSchedule>>()
            {
            }, details.getSolveRecord().getCancelRecordId());
        List<ResamplingSchedule> schedules = exchange.getBody();
        
        url = GatewayService.getServiceUrl("/bpm/testing/resampling/{id}/cancel_risk_testing_node");
        List<TaskConfig> riskTestingNodes = template.getForObject(url, List.class, details.getSolveRecord().getCancelRecordId());
        
        details.setRiskTestingNodes(riskTestingNodes);
        details.setSchedules(schedules);
        return details;
    }
}
