package com.todaysoft.lims.report.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.model.ConcludingReportFormModel;
import com.todaysoft.lims.report.model.ConcludingReportModel;
import com.todaysoft.lims.report.model.ConcludingReportSearcher;
import com.todaysoft.lims.report.model.ConcludingReportUploadModel;
import com.todaysoft.lims.report.service.core.event.ConcludingReportEvent;

public interface IConcludingReportService
{
    
    void createConcludingReport(String orderId);
    
    Pagination<ConcludingReportModel> paging(ConcludingReportSearcher request);
    
    ConcludingReportFormModel getDetail(String orderId);
    
    List<ConcludingReportModel> upload(ConcludingReportUploadModel request, String token);
    
    void deleteReport(String fileCode);
    
    void reportCallBackStatus(String orderId, String productId, String tag);

    void extraSendReport(ConcludingReportEvent event);
    
}
