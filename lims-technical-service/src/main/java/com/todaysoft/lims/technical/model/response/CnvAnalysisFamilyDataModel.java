package com.todaysoft.lims.technical.model.response;

import java.util.List;
import java.util.Map;

import com.todaysoft.lims.technical.entity.CapCnvData;
import com.todaysoft.lims.technical.mybatis.entity.BiologyDivisionFastqData;
import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisPic;
import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisResult;
import com.todaysoft.lims.technical.utils.Pager;
import com.todaysoft.lims.technical.utils.Pagination;



public class CnvAnalysisFamilyDataModel {
	private Integer status;

	private Pager<Map> capCnvDataPager;

	private Pagination<CapCnvData> capCnvDataPagination;

    private List<CnvAnalysisPicResultList> cnvAnalysisPicResultList;

	private List<CnvAnalysisResult> cnvAnalysisResultList;

	private Long geneSize;

	private Long areaSize;

    private BiologyDivisionFastqData biologyDivisionFastqData;
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Pager<Map> getCapCnvDataPager() {
		return capCnvDataPager;
	}

	public void setCapCnvDataPager(Pager<Map> capCnvDataPager) {
		this.capCnvDataPager = capCnvDataPager;
	}

	public Pagination<CapCnvData> getCapCnvDataPagination() {
		return capCnvDataPagination;
	}

	public void setCapCnvDataPagination(Pagination<CapCnvData> capCnvDataPagination) {
		this.capCnvDataPagination = capCnvDataPagination;
	}

	public List<CnvAnalysisResult> getCnvAnalysisResultList() {
		return cnvAnalysisResultList;
	}

	public void setCnvAnalysisResultList(List<CnvAnalysisResult> cnvAnalysisResultList) {
		this.cnvAnalysisResultList = cnvAnalysisResultList;
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

    public BiologyDivisionFastqData getBiologyDivisionFastqData()
    {
        return biologyDivisionFastqData;
    }

    public void setBiologyDivisionFastqData(BiologyDivisionFastqData biologyDivisionFastqData)
    {
        this.biologyDivisionFastqData = biologyDivisionFastqData;
    }

    public List<CnvAnalysisPicResultList> getCnvAnalysisPicResultList()
    {
        return cnvAnalysisPicResultList;
    }

    public void setCnvAnalysisPicResultList(List<CnvAnalysisPicResultList> cnvAnalysisPicResultList)
    {
        this.cnvAnalysisPicResultList = cnvAnalysisPicResultList;
    }
}
