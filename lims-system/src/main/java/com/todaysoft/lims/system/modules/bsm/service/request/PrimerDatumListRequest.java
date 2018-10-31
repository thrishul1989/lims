package com.todaysoft.lims.system.modules.bsm.service.request;

public class PrimerDatumListRequest
{
    private String chromosomeNumber;

    private String gene;

    private Long pcrPoint;
    
    private Integer pageNo;
    
    private Integer pageSize;

    public String getChromosomeNumber() {
        return chromosomeNumber;
    }

    public void setChromosomeNumber(String chromosomeNumber) {
        this.chromosomeNumber = chromosomeNumber;
    }

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
    }

    public Long getPcrPoint() {
        return pcrPoint;
    }

    public void setPcrPoint(Long pcrPoint) {
        this.pcrPoint = pcrPoint;
    }

    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
}
