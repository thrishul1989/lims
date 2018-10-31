package com.todaysoft.lims.system.modules.reportexport.entity;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis.CnvAnalysisPic;

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
