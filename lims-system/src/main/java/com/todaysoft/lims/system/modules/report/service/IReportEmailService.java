package com.todaysoft.lims.system.modules.report.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.report.model.ReportEmailAssignRequest;
import com.todaysoft.lims.system.modules.report.model.ReportEmailForOrderStatusModel;
import com.todaysoft.lims.system.modules.report.model.ReportEmailModel;
import com.todaysoft.lims.system.modules.report.model.ReportEmailSearcher;
import com.todaysoft.lims.system.modules.report.model.TestingReportEmail;
import com.todaysoft.lims.system.modules.report.model.WhetherEmailModel;

public interface IReportEmailService
{

    Pagination<ReportEmailModel> paging(ReportEmailSearcher searcher);

    void assign(ReportEmailAssignRequest re);

    WhetherEmailModel whetherCanEmail(WhetherEmailModel m);

    List<ReportEmailModel> getByIds(String reportEmailIds);

    void reportEmail(ReportEmailModel request);

    void noReport(String reportEmailIds);

    void createReport(String orderId);

    List<ReportEmailModel> getByOrderId(String id);

    TestingReportEmail getById(String id);

    List<ReportEmailForOrderStatusModel> getListForStatusByOrderId(String orderId);
    
}
