package com.todaysoft.lims.technical.service;

import com.todaysoft.lims.technical.model.request.ReportExportCnvAnalysisPicResultListRequest;
import com.todaysoft.lims.technical.model.request.ReportExportMutationInfoRequest;
import com.todaysoft.lims.technical.model.request.UpdateMutationSourceRequest;
import com.todaysoft.lims.technical.model.response.ReportExportCnvAnalysisPicResultList;
import com.todaysoft.lims.technical.mybatis.entity.ReportExportMutationInfo;

import java.util.List;
import java.util.Map;

public interface IReportExportService {
    Map<String, List<ReportExportMutationInfo>> getReportExportMutationInfo(ReportExportMutationInfoRequest request);
    Map<String, List<ReportExportCnvAnalysisPicResultList>> getReportExportCnvAnalysisPicResultList(ReportExportCnvAnalysisPicResultListRequest reportCollectionCapcnvDataRequest);
    List<ReportExportMutationInfo> searchReportExportMutationInfoListByObjectIds(List<String> objectIds);
    void updateMutationSource(UpdateMutationSourceRequest mutationSourceRequest);

}
