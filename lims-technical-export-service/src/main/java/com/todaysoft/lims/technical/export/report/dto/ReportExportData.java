package com.todaysoft.lims.technical.export.report.dto;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.technical.export.mybatis.model.MutationFamilySangerResultInfo;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportDetectionResult;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportDetectionResultPicture;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportMethodColumnConfig;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportMutationInfo;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportSampleBaseInfo;

public class ReportExportData
{
    private ReportExportSampleBaseInfo reportExportSampleBaseInfo;
    private Map<String, List<ReportExportMutationInfo>> clinicalJudgmentToReportExportMutationInfoList;
    private Map<String, List<ReportExportCnvAnalysisPicResultList>> clinicalJudgmentToReportExportCnvAnalysisPicResultLists;
    private List<ReportExportMethodColumnConfig> mlpaDetectionResultColumns;
    private List<ReportExportDetectionResult> mlpaHighCorrelationReportExportDetectionResults;
    private List<ReportExportDetectionResult> mlpaCorrelationReportExportDetectionResults;
    private List<ReportExportMethodColumnConfig> dtDetectionResultColumns;
    private List<ReportExportDetectionResult> dtHighCorrelationReportExportDetectionResults;
    private List<ReportExportDetectionResult> dtCorrelationReportExportDetectionResults;
    private List<MutationFamilySangerResultInfo> mutationFamilySangerResultInfos;
    private List<ReportExportDetectionResultPicture> mlpaReportExportDetectionResultPictures;
    private List<ReportExportDetectionResultPicture> dtReportExportDetectionResultPictures;
    
    public ReportExportSampleBaseInfo getReportExportSampleBaseInfo()
    {
        return reportExportSampleBaseInfo;
    }
    public void setReportExportSampleBaseInfo(ReportExportSampleBaseInfo reportExportSampleBaseInfo)
    {
        this.reportExportSampleBaseInfo = reportExportSampleBaseInfo;
    }
    public Map<String, List<ReportExportMutationInfo>> getClinicalJudgmentToReportExportMutationInfoList()
    {
        return clinicalJudgmentToReportExportMutationInfoList;
    }
    public void setClinicalJudgmentToReportExportMutationInfoList(Map<String, List<ReportExportMutationInfo>> clinicalJudgmentToReportExportMutationInfoList)
    {
        this.clinicalJudgmentToReportExportMutationInfoList = clinicalJudgmentToReportExportMutationInfoList;
    }
    public Map<String, List<ReportExportCnvAnalysisPicResultList>> getClinicalJudgmentToReportExportCnvAnalysisPicResultLists()
    {
        return clinicalJudgmentToReportExportCnvAnalysisPicResultLists;
    }
    public void setClinicalJudgmentToReportExportCnvAnalysisPicResultLists(Map<String, List<ReportExportCnvAnalysisPicResultList>> clinicalJudgmentToReportExportCnvAnalysisPicResultLists)
    {
        this.clinicalJudgmentToReportExportCnvAnalysisPicResultLists = clinicalJudgmentToReportExportCnvAnalysisPicResultLists;
    }
    public List<ReportExportDetectionResult> getMlpaHighCorrelationReportExportDetectionResults()
    {
        return mlpaHighCorrelationReportExportDetectionResults;
    }
    public void setMlpaHighCorrelationReportExportDetectionResults(List<ReportExportDetectionResult> mlpaHighCorrelationReportExportDetectionResults)
    {
        this.mlpaHighCorrelationReportExportDetectionResults = mlpaHighCorrelationReportExportDetectionResults;
    }
    public List<ReportExportDetectionResult> getMlpaCorrelationReportExportDetectionResults()
    {
        return mlpaCorrelationReportExportDetectionResults;
    }
    public void setMlpaCorrelationReportExportDetectionResults(List<ReportExportDetectionResult> mlpaCorrelationReportExportDetectionResults)
    {
        this.mlpaCorrelationReportExportDetectionResults = mlpaCorrelationReportExportDetectionResults;
    }
    public List<ReportExportDetectionResult> getDtHighCorrelationReportExportDetectionResults()
    {
        return dtHighCorrelationReportExportDetectionResults;
    }
    public void setDtHighCorrelationReportExportDetectionResults(List<ReportExportDetectionResult> dtHighCorrelationReportExportDetectionResults)
    {
        this.dtHighCorrelationReportExportDetectionResults = dtHighCorrelationReportExportDetectionResults;
    }
    public List<ReportExportDetectionResult> getDtCorrelationReportExportDetectionResults()
    {
        return dtCorrelationReportExportDetectionResults;
    }
    public void setDtCorrelationReportExportDetectionResults(List<ReportExportDetectionResult> dtCorrelationReportExportDetectionResults)
    {
        this.dtCorrelationReportExportDetectionResults = dtCorrelationReportExportDetectionResults;
    }
    public List<ReportExportMethodColumnConfig> getMlpaDetectionResultColumns()
    {
        return mlpaDetectionResultColumns;
    }
    public void setMlpaDetectionResultColumns(List<ReportExportMethodColumnConfig> mlpaDetectionResultColumns)
    {
        this.mlpaDetectionResultColumns = mlpaDetectionResultColumns;
    }
    public List<ReportExportMethodColumnConfig> getDtDetectionResultColumns()
    {
        return dtDetectionResultColumns;
    }
    public void setDtDetectionResultColumns(List<ReportExportMethodColumnConfig> dtDetectionResultColumns)
    {
        this.dtDetectionResultColumns = dtDetectionResultColumns;
    }
    public List<MutationFamilySangerResultInfo> getMutationFamilySangerResultInfos()
    {
        return mutationFamilySangerResultInfos;
    }
    public void setMutationFamilySangerResultInfos(List<MutationFamilySangerResultInfo> mutationFamilySangerResultInfos)
    {
        this.mutationFamilySangerResultInfos = mutationFamilySangerResultInfos;
    }
    public List<ReportExportDetectionResultPicture> getMlpaReportExportDetectionResultPictures()
    {
        return mlpaReportExportDetectionResultPictures;
    }
    public void setMlpaReportExportDetectionResultPictures(List<ReportExportDetectionResultPicture> mlpaReportExportDetectionResultPictures)
    {
        this.mlpaReportExportDetectionResultPictures = mlpaReportExportDetectionResultPictures;
    }
    public List<ReportExportDetectionResultPicture> getDtReportExportDetectionResultPictures()
    {
        return dtReportExportDetectionResultPictures;
    }
    public void setDtReportExportDetectionResultPictures(List<ReportExportDetectionResultPicture> dtReportExportDetectionResultPictures)
    {
        this.dtReportExportDetectionResultPictures = dtReportExportDetectionResultPictures;
    }
    
}
