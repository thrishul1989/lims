package com.todaysoft.lims.system.modules.reportexport.service;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.system.modules.reportexport.entity.MutationFamilySangerResultInfo;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportCnvAnalysisPicResultList;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportDetectionResult;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportDetectionResultPicture;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportMethodColumnConfig;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportMutationInfo;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportSampleBaseInfo;
import com.todaysoft.lims.system.modules.reportexport.model.request.GetReportExportSampleBaseInfoRequest;
import com.todaysoft.lims.system.modules.reportexport.model.request.ReportExportCnvAnalysisPicResultListRequest;
import com.todaysoft.lims.system.modules.reportexport.model.request.ReportExportDetectionResultInfoRequest;
import com.todaysoft.lims.system.modules.reportexport.model.request.ReportExportDetectionResultPictureRequest;
import com.todaysoft.lims.system.modules.reportexport.model.request.ReportExportMutationInfoRequest;
import com.todaysoft.lims.system.modules.reportexport.model.request.ReportExportUpdateRequest;
import com.todaysoft.lims.system.modules.reportexport.model.response.GenerateWordReportReponse;

public interface IReportExportPreviewService
{
    ReportExportSampleBaseInfo getReportExportSampleBaseInfo(GetReportExportSampleBaseInfoRequest request);
    
    Map<String, List<ReportExportMutationInfo>> getReportExportMutationInfo(ReportExportMutationInfoRequest request);
    
    Map<String, List<ReportExportCnvAnalysisPicResultList>> getReportExportCnvAnalysisPicResultList(ReportExportCnvAnalysisPicResultListRequest request);

    void updateReportExport(ReportExportUpdateRequest request);
    
    GenerateWordReportReponse generateReport(String taskId);

    byte[] downLoadReport(String fileName);

    void downLoad1(String fileName);
    
    Map<String,List<ReportExportDetectionResult>> getReportExportDetectionResultInfo(ReportExportDetectionResultInfoRequest request);
    
    public List<ReportExportMethodColumnConfig>  getReportExportMethodColumnConfig(ReportExportDetectionResultInfoRequest request);

    List<MutationFamilySangerResultInfo> getMutationFamilySangerResultInfoByTaskId(String taskId);

    List<ReportExportDetectionResultPicture> getReportExportDetectionResultPictureByTaskIdAndMethod(ReportExportDetectionResultPictureRequest request);
    
}
