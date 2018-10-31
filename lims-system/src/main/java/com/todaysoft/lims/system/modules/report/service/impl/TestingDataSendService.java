package com.todaysoft.lims.system.modules.report.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.modules.report.model.ReportEmailModel;
import com.todaysoft.lims.system.modules.report.model.ReportEmailSearcher;
import com.todaysoft.lims.system.modules.report.model.TestingDataSendFormModel;
import com.todaysoft.lims.system.modules.report.model.TestingDataSendModel;
import com.todaysoft.lims.system.modules.report.model.TestingDataSendSearcher;
import com.todaysoft.lims.system.modules.report.service.ITestingDataSendService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class TestingDataSendService extends RestService implements ITestingDataSendService
{
    
    @Override
    public Pagination<TestingDataSendModel> paging(TestingDataSendSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/report/testingDataSend/assignedList");
        ResponseEntity<Pagination<TestingDataSendModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<TestingDataSendSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<TestingDataSendModel>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public List<TestingDataSendFormModel> getDetail(String orderId)
    {
        String url = GatewayService.getServiceUrl("/report/testingDataSend/getDetail/{orderId}");
        List<TestingDataSendFormModel> list = template.getForObject(url, List.class, orderId);
        return list;
    }
    
    @Override
    public void send(String dataSendIds)
    {
        String url = GatewayService.getServiceUrl("/report/testingDataSend/send/{dataSendIds}");
        template.postForLocation(url, new HashMap(), dataSendIds);
        
    }
    
}
