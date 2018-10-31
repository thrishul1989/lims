package com.todaysoft.lims.system.modules.report.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.report.model.ReportEmailAssignRequest;
import com.todaysoft.lims.system.modules.report.model.ReportEmailForOrderStatusModel;
import com.todaysoft.lims.system.modules.report.model.ReportEmailModel;
import com.todaysoft.lims.system.modules.report.model.ReportEmailSearcher;
import com.todaysoft.lims.system.modules.report.model.TestingReportEmail;
import com.todaysoft.lims.system.modules.report.model.WhetherEmailModel;
import com.todaysoft.lims.system.modules.report.service.IReportEmailService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class ReportEmailService extends RestService implements IReportEmailService
{
    
    @Override
    public Pagination<ReportEmailModel> paging(ReportEmailSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/report/reportEmail/assignedList");
        ResponseEntity<Pagination<ReportEmailModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ReportEmailSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<ReportEmailModel>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public void assign(ReportEmailAssignRequest re)
    {
        String url = GatewayService.getServiceUrl("/report/reportEmail/assign");
        ResponseEntity<Integer> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<ReportEmailAssignRequest>(re), new ParameterizedTypeReference<Integer>()
            {
            });
        
    }
    
    @Override
    public WhetherEmailModel whetherCanEmail(WhetherEmailModel m)
    {
        String url = GatewayService.getServiceUrl("/report/reportEmail/whetherCanEmail");
        ResponseEntity<WhetherEmailModel> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<WhetherEmailModel>(m), new ParameterizedTypeReference<WhetherEmailModel>()
            {
            });
        return exchange.getBody();
    }
    
    @Override
    public List<ReportEmailModel> getByIds(String reportEmailIds)
    {
        Map req = new HashMap<>();
        req.put("reportEmailIds", reportEmailIds);
        String url = GatewayService.getServiceUrl("/report/reportEmail/getByIds/" + reportEmailIds);
        ResponseEntity<List<ReportEmailModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Map>(req), new ParameterizedTypeReference<List<ReportEmailModel>>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public void reportEmail(ReportEmailModel request)
    {
        String url = GatewayService.getServiceUrl("/report/reportEmail/reportEmail");
        template.postForLocation(url, request);
        
    }
    
    @Override
    public void noReport(String reportEmailIds)
    {
        String url = GatewayService.getServiceUrl("/report/reportEmail/noReport/{reportEmailIds}");
        template.delete(url, reportEmailIds);
        
    }
    
    @Override
    public void createReport(String orderId)
    {
        String url = GatewayService.getServiceUrl("/report/reportEmail/createReport/{orderId}");
        template.delete(url, orderId);
        
    }
    
    @Override
    public List<ReportEmailModel> getByOrderId(String id)
    {
        Map req = new HashMap<>();
        req.put("orderId", id);
        String url = GatewayService.getServiceUrl("/report/reportEmail/getByOrderId/" + id);
        ResponseEntity<List<ReportEmailModel>> exchange =
            template.exchange(url, HttpMethod.POST, new HttpEntity<Map>(req), new ParameterizedTypeReference<List<ReportEmailModel>>()
            {
            });
        
        return exchange.getBody();
    }
    
    @Override
    public TestingReportEmail getById(String reportEmailId)
    {
        String url = GatewayService.getServiceUrl("/report/reportEmail/getById/{reportEmailId}");
        TestingReportEmail model = template.getForObject(url, TestingReportEmail.class, reportEmailId);
        return model;
    }

    @Override
    public List<ReportEmailForOrderStatusModel> getListForStatusByOrderId(String orderId)
    {
        String url = GatewayService.getServiceUrl("/report/reportEmail/getListForStatusByOrderId/{orderId}");
        ResponseEntity<List<ReportEmailForOrderStatusModel>> exchange =
            template.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<ReportEmailForOrderStatusModel>>()
            {
            }, Collections.singletonMap("orderId", orderId));
        return exchange.getBody();
    }
    
}
