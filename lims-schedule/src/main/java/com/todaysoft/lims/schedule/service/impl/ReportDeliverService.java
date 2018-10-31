package com.todaysoft.lims.schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.schedule.service.IReportDeliverService;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;

@Service
public class ReportDeliverService implements IReportDeliverService
{
    private static final String REPORT_DELIVER = "REPORT_DELIVER";
    
    @Autowired
    private ISchedulePlanService schedulePlanService;
    
    @Override
    public void updatePlanWithReportDeliver(String orderId)
    {
        schedulePlanService.updatePlanFinish(orderId, REPORT_DELIVER, null);
        
    }
    
}
