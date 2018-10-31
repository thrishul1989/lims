package com.todaysoft.lims.system.modules.reportexport.model.request;

import java.util.List;

import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportCapcnvResult;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportMutationInfo;
import com.todaysoft.lims.system.modules.reportexport.entity.ReportExportSampleBaseInfo;

public class ReportExportUpdateRequest2
{
   // private List<ReportExportCapcnvResult> reportExportCapcnvResults;
  //  private List<ReportExportMutationInfo> reportExportMutationInfos;
    private ReportExportSampleBaseInfo reportExportSampleBaseInfo;
    
    
/*    public List<ReportExportCapcnvResult> getReportExportCapcnvResults()
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
    }*/
    public ReportExportSampleBaseInfo getReportExportSampleBaseInfo()
    {
        return reportExportSampleBaseInfo;
    }
    public void setReportExportSampleBaseInfo(ReportExportSampleBaseInfo reportExportSampleBaseInfo)
    {
        this.reportExportSampleBaseInfo = reportExportSampleBaseInfo;
    }
    
}
