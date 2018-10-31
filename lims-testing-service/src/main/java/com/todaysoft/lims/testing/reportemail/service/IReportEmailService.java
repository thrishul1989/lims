package com.todaysoft.lims.testing.reportemail.service;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.TestingReportEmail;
import com.todaysoft.lims.testing.reportemail.model.ReportEmailAssignRequest;
import com.todaysoft.lims.testing.reportemail.model.ReportEmailForOrderStatusModel;
import com.todaysoft.lims.testing.reportemail.model.ReportEmailModel;
import com.todaysoft.lims.testing.reportemail.model.ReportEmailSearcher;
import com.todaysoft.lims.testing.reportemail.model.WhetherEmailModel;

public interface IReportEmailService
{
    final static String REPORT_EMAIL_TAG = "REPORT_EMAIL";
    
    final static String DATA_SEND_TAG = "DATA_SEND";
    
    final static String CONLUDING_REPORT_TAG = "CONLUDING_REPORT";
    
    Pagination<ReportEmailModel> paging(ReportEmailSearcher request);
    
    Integer assign(ReportEmailAssignRequest request, String token);
    
    WhetherEmailModel whetherCanEmail(WhetherEmailModel request);
    
    List<ReportEmailModel> getByIds(String reportEmailIds);
    
    List<Map<String, String>> reportEmail(ReportEmailModel request);
    
    List<Map<String, String>> noReport(String reportEmailIds);
    
    void createReport(String orderId);
    
    List<ReportEmailModel> getByOrderId(String orderId);
    
    TestingReportEmail getById(String reportEmailId);
    
    void sendEmailcallBackOrderStatus(String orderId, String productId, String tag);
    
    List<ReportEmailForOrderStatusModel> getListForStatusByOrderId(String orderId);
    
}
