package com.todaysoft.lims.testing.report.service;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.testing.base.entity.report.ReportExportDetectionResult;
import com.todaysoft.lims.testing.base.entity.report.ReportExportMethodColumnConfig;
import com.todaysoft.lims.testing.base.entity.report.ReportExportSampleBaseInfo;
import com.todaysoft.lims.testing.report.request.GetReportExportSampleBaseInfoRequest;
import com.todaysoft.lims.testing.report.request.ReportExportDetectionResultInfoRequest;

public interface IReportExportService {
    ReportExportSampleBaseInfo getReportExportSampleBaseInfo(GetReportExportSampleBaseInfoRequest request);

    Map<String,List<ReportExportDetectionResult>> getReportExportDetectionResultInfo(ReportExportDetectionResultInfoRequest request);
    
    List<ReportExportMethodColumnConfig> getReportExportMethodColumnConfig(String method);
}
