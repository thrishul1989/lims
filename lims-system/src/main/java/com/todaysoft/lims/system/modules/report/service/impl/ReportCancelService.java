package com.todaysoft.lims.system.modules.report.service.impl;

import java.util.HashMap;

import org.hibernate.mapping.Map;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.modules.report.service.IReportCancelService;
import com.todaysoft.lims.system.service.impl.GatewayService;
import com.todaysoft.lims.system.service.impl.RestService;

@Service
public class ReportCancelService extends RestService implements IReportCancelService
{

    @Override
    public void cancelReport(String scheduleId,String reportId)
    {
        String url = GatewayService.getServiceUrl("/bpm/report/reportCancel/cancel/{scheduleId}/{reportId}");
        template.postForLocation(url, new HashMap(),scheduleId,reportId);
        
    }
    
}
