package com.todaysoft.lims.technical.model.response;


import java.util.List;

import com.todaysoft.lims.technical.entity.CapCnvData;
import com.todaysoft.lims.technical.mybatis.entity.BiologyDivisionFastqData;
import com.todaysoft.lims.technical.utils.Pager;

public class CnvAnalysisDataModel {

    private Integer status;

    private Pager<CapCnvData> capCnvDataPager;

    private Long geneSize;

    private Long areaSize;
   
    private List<CnvAnalysisPicResultList> cnvAnalysisPicResultList;
    
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
