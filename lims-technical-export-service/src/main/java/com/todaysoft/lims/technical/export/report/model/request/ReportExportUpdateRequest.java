package com.todaysoft.lims.technical.export.report.model.request;

import java.util.HashMap;
import java.util.List;

import com.todaysoft.lims.technical.export.mybatis.model.ReportExportCapcnvResult;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportDetectionResult;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportMutationInfo;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportSampleBaseInfo;

public class ReportExportUpdateRequest
{
    private List<ReportExportCapcnvResult> reportExportCapcnvResults;
    private List<ReportExportMutationInfo> reportExportMutationInfos;
    private ReportExportSampleBaseInfo reportExportSampleBaseInfo;
    private List<ReportExportCapcnvResult> reportExportCapcnvResultExplains;
    private List<ReportExportDetectionResult> mlpaDetectionResults;
    private List<ReportExportDetectionResult> dtmutationDetectionResults;
    public List<ReportExportCapcnvResult> getReportExportCapcnvResults()
    {
        return reportExportCapcnvResults;
    }
    public void setReportExportCapcnvResults(List<ReportExportCapcnvResult> reportExportCapcnvResults)
    {
        this.reportExportCapcnvResults = reportExportCapcnvResults;
    }
    public List<ReportExportMutationInfo> getReportExportMutationInfos()
    {
        return reportExportMutationInfos;
    }
    public void setReportExportMutationInfos(List<ReportExportMutationInfo> reportExportMutationInfos)
    {
        this.reportExportMutationInfos = reportExportMutationInfos;
    }
    public ReportExportSampleBaseInfo getReportExportSampleBaseInfo()
    {
        return reportExportSampleBaseInfo;
    }
    public void setReportExportSampleBaseInfo(ReportExportSampleBaseInfo reportExportSampleBaseInfo)
    {
        this.reportExportSampleBaseInfo = reportExportSampleBaseInfo;
    }
    public List<ReportExportCapcnvResult> getReportExportCapcnvResultExplains()
    {
        return reportExportCapcnvResultExplains;
    }
    public void setReportExportCapcnvResultExplains(List<ReportExportCapcnvResult> reportExportCapcnvResultExplains)
    {
        this.reportExportCapcnvResultExplains = reportExportCapcnvResultExplains;
    }
    public List<ReportExportDetectionResult> getMlpaDetectionResults()
    {
        return mlpaDetectionResults;
    }
    public void setMlpaDetectionResults(List<ReportExportDetectionResult> mlpaDetectionResults)
    {
        this.mlpaDetectionResults = mlpaDetectionResults;
    }
    public List<ReportExportDetectionResult> getDtmutationDetectionResults()
    {
        return dtmutationDetectionResults;
    }
    public void setDtmutationDetectionResults(List<ReportExportDetectionResult> dtmutationDetectionResults)
    {
        this.dtmutationDetectionResults = dtmutationDetectionResults;
    }
    
}
