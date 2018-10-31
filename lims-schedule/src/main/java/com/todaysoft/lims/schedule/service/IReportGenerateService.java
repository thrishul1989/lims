package com.todaysoft.lims.schedule.service;


public interface IReportGenerateService
{
    void updatePlanWithReportGenerate(String orderId);
    
    void openReportGenerate(String orderId,String productId);
}
