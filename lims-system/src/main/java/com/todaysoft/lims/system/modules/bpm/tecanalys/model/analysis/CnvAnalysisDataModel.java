package com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.tecanalys.model.base.Pager;
import com.todaysoft.lims.system.modules.bpm.tecanalys.model.base.Pagination;

public class CnvAnalysisDataModel {

    private Integer status;

    private Pager<CapCnvData> capCnvDataPager;

    private Pagination<CapCnvData> capCnvDataPagination;

    private List<CnvAnalysisPicResultList> cnvAnalysisPicResultList;

    private Long geneSize;

    private Long areaSize;
    
    private BiologyDivisionFastqData biologyDivisionFastqData;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Pager<CapCnvData> getCapCnvDataPager() {
        return capCnvDataPager;
    }

    public void setCapCnvDataPager(Pager<CapCnvData> capCnvDataPager) {
        this.capCnvDataPager = capCnvDataPager;
    }

    public Pagination<CapCnvData> getCapCnvDataPagination() {
        Pagination<CapCnvData> pagination = null;
        if(null != capCnvDataPager){
            pagination = new Pagination<CapCnvData>(capCnvDataPager.getPageNo(), capCnvDataPager.getPageSize(), capCnvDataPager.getTotalCount());
            pagination.setRecords(capCnvDataPager.getRecords());
        }
        return pagination;
    }

    public void setCapCnvDataPagination(Pagination<CapCnvData> capCnvDataPagination) {
        this.capCnvDataPagination = capCnvDataPagination;
    }



    public Long getGeneSize() {
        return geneSize;
    }

    public void setGeneSize(Long geneSize) {
        this.geneSize = geneSize;
    }

    public Long getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(Long areaSize) {
        this.areaSize = areaSize;
    }

	public List<CnvAnalysisPicResultList> getCnvAnalysisPicResultList() {
		return cnvAnalysisPicResultList;
	}

	public void setCnvAnalysisPicResultList(List<CnvAnalysisPicResultList> cnvAnalysisPicResultList) {
		this.cnvAnalysisPicResultList = cnvAnalysisPicResultList;
	}

	public BiologyDivisionFastqData getBiologyDivisionFastqData() {
		return biologyDivisionFastqData;
	}

	public void setBiologyDivisionFastqData(BiologyDivisionFastqData biologyDivisionFastqData) {
		this.biologyDivisionFastqData = biologyDivisionFastqData;
	}
}
