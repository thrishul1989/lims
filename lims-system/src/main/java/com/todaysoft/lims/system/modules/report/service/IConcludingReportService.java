package com.todaysoft.lims.system.modules.report.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.report.model.ConcludingReportFormModel;
import com.todaysoft.lims.system.modules.report.model.ConcludingReportModel;
import com.todaysoft.lims.system.modules.report.model.ConcludingReportSearcher;
import com.todaysoft.lims.system.modules.report.model.ConcludingReportUploadModel;

public interface IConcludingReportService
{
    
    Pagination<ConcludingReportModel> paging(ConcludingReportSearcher searcher);

    ConcludingReportFormModel getDetail(String orderId);

    void upload(ConcludingReportUploadModel re);

    void deleteReport(String fileCode);
    
}
