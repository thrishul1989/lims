package com.todaysoft.lims.technical.export.report.service;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.technical.export.mybatis.model.MutationFamilySangerResultInfo;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportDetectionResult;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportDetectionResultPicture;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportMethodColumnConfig;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportMutationInfo;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportSampleBaseInfo;
import com.todaysoft.lims.technical.export.report.dto.ReportExportCnvAnalysisPicResultList;
import com.todaysoft.lims.technical.export.report.model.request.ReportExportDetectionResultPictureRequest;
import com.todaysoft.lims.technical.export.report.model.request.ReportExportUpdateRequest;


public interface IReportExportService
{
    ReportExportSampleBaseInfo getReportExportSampleBaseInfoByTaskId(String taskId);
    
    Map<String, List<ReportExportMutationInfo>> getReportExportMutationInfo(String taskId);

    Map<String, List<ReportExportCnvAnalysisPicResultList>> getReportExportCnvAnalysisPicResultList(String taskId);

    void updateReportExport(ReportExportUpdateRequest request);
    
    String generateReport(String taskId);

    List<ReportExportDetectionResult> getReportExportDetectionResult(ReportExportDetectionResult reportExportDetectionResult);
    
    List<ReportExportMethodColumnConfig> getReportExportMethodColumnConfig(String method);
    
    List<MutationFamilySangerResultInfo> getMutationFamilySangerResultInfoByTaskId(String taskId);

    List<ReportExportDetectionResultPicture> getReportExportDetectionResultPictureByTaskIdAndMethod(ReportExportDetectionResultPictureRequest reportExportDetectionResultPictureRequest);
}
