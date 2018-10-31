package com.todaysoft.lims.technical.export.report.dto;

import java.util.List;

import com.todaysoft.lims.technical.export.mybatis.model.CnvAnalysisPic;
import com.todaysoft.lims.technical.export.mybatis.model.ReportExportCapcnvResult;


public class ReportExportCnvAnalysisPicResultList {
	private CnvAnalysisPic cnvAnalysisPic;
    private List<ReportExportCapcnvResult> reportExportCapcnvResultList;
    
	public CnvAnalysisPic getCnvAnalysisPic() {
		return cnvAnalysisPic;
	}
	public void setCnvAnalysisPic(CnvAnalysisPic cnvAnalysisPic) {
		this.cnvAnalysisPic = cnvAnalysisPic;
	}
    public List<ReportExportCapcnvResult> getReportExportCapcnvResultList()
    {
        return reportExportCapcnvResultList;
    }
    public void setReportExportCapcnvResultList(List<ReportExportCapcnvResult> reportExportCapcnvResultList)
    {
        this.reportExportCapcnvResultList = reportExportCapcnvResultList;
    }
    
}
