package com.todaysoft.lims.schedule.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.schedule.service.IReportVerifyService;
import com.todaysoft.lims.schedule.service.ISchedulePlanService;

@Service
public class ReportVerifyService implements IReportVerifyService
{
    private static final String REPORT_VERIFY = "REPORT_VERIFY";
    
    @Autowired
    private ISchedulePlanService schedulePlanService;
    
    @Override
    public void updatePlanWithReportVerify(String orderId)
    {
        schedulePlanService.updatePlanFinish(orderId, REPORT_VERIFY, null);
    }
    
}
