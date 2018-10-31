package com.todaysoft.lims.technical.model.response;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisPic;
import com.todaysoft.lims.technical.mybatis.entity.ReportExportCapcnvResult;

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
