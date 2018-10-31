package com.todaysoft.lims.system.modules.report.service.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.report.model.ConcludingReportFormModel;
import com.todaysoft.lims.system.modules.report.model.ConcludingReportModel;
import com.todaysoft.lims.system.modules.report.model.ConcludingReportSearcher;
import com.todaysoft.lims.system.modules.report.model.ConcludingReportUploadModel;
import com.todaysoft.lims.system.modules.report.model.TestingDataSendFormModel;
import com.todaysoft.lims.system.modules.report.model.TestingDataSendModel;
import com.todaysoft.lims.system.modules.report.model.TestingDataSendSearcher;
import com.todaysoft.lims.system.modules.report.service.IConcludingReportService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class ConcludingReportService extends RestService implements IConcludingReportService
{
    
    @Override
    public Pagination<ConcludingReportModel> paging(ConcludingReportSearcher searcher)
    {
        String url = GatewayService.getServiceUrl("/report/concludingReport/assignedList");
        ResponseEntity<Pagination<ConcludingReportModel>> exchange =
            template.exchange(url,
                HttpMethod.POST,
                new HttpEntity<ConcludingReportSearcher>(searcher),
                new ParameterizedTypeReference<Pagination<ConcludingReportModel>>()
                {
                });
        return exchange.getBody();
    }
    
    @Override
    public ConcludingReportFormModel getDetail(String orderId)
    {
        String url = GatewayService.getServiceUrl("/report/concludingReport/getDetail/{orderId}");
        ConcludingReportFormModel res = template.getForObject(url, ConcludingReportFormModel.class, orderId);
        return res;
    }
    
    @Override
    public void upload(ConcludingReportUploadModel re)
    {
        String url = GatewayService.getServiceUrl("/report/concludingReport/upload");
        template.postForLocation(url, re);
        
    }
    
    @Override
    public void deleteReport(String fileCode)
    {
        String url = GatewayService.getServiceUrl("/report/concludingReport/deleteReport/{fileCode}");
        template.delete(url, fileCode);
        
    }
    
}
