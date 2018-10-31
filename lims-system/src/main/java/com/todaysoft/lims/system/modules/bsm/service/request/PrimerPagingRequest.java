package com.todaysoft.lims.system.modules.bsm.service.request;

import java.io.Serializable;

public class PrimerPagingRequest implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String gene;
    
    private String applicationType;
    
    private String productCode;
    
    private String chromosomeNumber;
    
    private Long pcrPoint;
    
    private int pageNo;
    
    private int pageSize;
    
    private String forwardPrimerName;
    
    private String reversePrimerName;
    
    private String ids;
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getGene()
    {
        return gene;
    }
    
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
    public String getApplicationType()
    {
        return applicationType;
    }
    
    public void setApplicationType(String applicationType)
    {
        this.applicationType = applicationType;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public String getChromosomeNumber()
    {
        return chromosomeNumber;
    }
    
    public void setChromosomeNumber(String chromosomeNumber)
    {
        this.chromosomeNumber = chromosomeNumber;
    }
    
    public Long getPcrPoint()
    {
        return pcrPoint;
    }
    
    public void setPcrPoint(Long pcrPoint)
    {
        this.pcrPoint = pcrPoint;
    }
    
    public String getForwardPrimerName()
    {
        return forwardPrimerName;
    }
    
    public void setForwardPrimerName(String forwardPrimerName)
    {
        this.forwardPrimerName = forwardPrimerName;
    }
    
    public String getReversePrimerName()
    {
        return reversePrimerName;
    }
    
    public void setReversePrimerName(String reversePrimerName)
    {
        this.reversePrimerName = reversePrimerName;
    }

    public String getIds()
    {
        return ids;
    }

    public void setIds(String ids)
    {
        this.ids = ids;
    }
    
}
