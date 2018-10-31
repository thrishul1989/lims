package com.todaysoft.lims.testing.firstpcrsanger.model;

import java.math.BigDecimal;
import java.util.Date;

public class FirstPcrSangerTask
{
    private String id;
    
    private String sampleName;
    
    private String sampleCode;
    
    private String forwardPrimerCode;
    
    private String sampleLocationTemp;
    
    private String dnaLocation;
    
    private String verifyScheme;//验证方案
    
    private boolean resubmit;
    
    private Integer resubmitCount;
    
    private String forwardPrimerLocationTemp;
    
    private String reversePrimerLocationTemp;
    
    private String reversePrimerCode;
    
    private BigDecimal concentration;
    
    private BigDecimal volume;
    
    private String pcrTestCode;
    
    private String combineCode;
    
    private String product;
    
    private String primerId;
    
    private String gene;
    
    private Integer runningStatus;//0-正常 3-已取消
    
    private String clinicalRecommend;//临床推荐理由
    
    private Integer storageStatus;
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private Date plannedFinishDate;
    
    private Date startTime;
    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    public String getClinicalRecommend()
    {
        return clinicalRecommend;
    }
    
    public void setClinicalRecommend(String clinicalRecommend)
    {
        this.clinicalRecommend = clinicalRecommend;
    }
    
    public Integer getRunningStatus()
    {
        return runningStatus;
    }
    
    public void setRunningStatus(Integer runningStatus)
    {
        this.runningStatus = runningStatus;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getVerifyScheme()
    {
        return verifyScheme;
    }
    
    public void setVerifyScheme(String verifyScheme)
    {
        this.verifyScheme = verifyScheme;
    }
    
    public boolean isResubmit()
    {
        return resubmit;
    }
    
    public void setResubmit(boolean resubmit)
    {
        this.resubmit = resubmit;
    }
    
    public Integer getResubmitCount()
    {
        return resubmitCount;
    }
    
    public void setResubmitCount(Integer resubmitCount)
    {
        this.resubmitCount = resubmitCount;
    }
    
    public String getDnaLocation()
    {
        return dnaLocation;
    }
    
    public void setDnaLocation(String dnaLocation)
    {
        this.dnaLocation = dnaLocation;
    }
    
    public String getForwardPrimerCode()
    {
        return forwardPrimerCode;
    }
    
    public void setForwardPrimerCode(String forwardPrimerCode)
    {
        this.forwardPrimerCode = forwardPrimerCode;
    }
    
    public String getSampleLocationTemp()
    {
        return sampleLocationTemp;
    }
    
    public void setSampleLocationTemp(String sampleLocationTemp)
    {
        this.sampleLocationTemp = sampleLocationTemp;
    }
    
    public String getForwardPrimerLocationTemp()
    {
        return forwardPrimerLocationTemp;
    }
    
    public void setForwardPrimerLocationTemp(String forwardPrimerLocationTemp)
    {
        this.forwardPrimerLocationTemp = forwardPrimerLocationTemp;
    }
    
    public String getReversePrimerLocationTemp()
    {
        return reversePrimerLocationTemp;
    }
    
    public void setReversePrimerLocationTemp(String reversePrimerLocationTemp)
    {
        this.reversePrimerLocationTemp = reversePrimerLocationTemp;
    }
    
    public String getReversePrimerCode()
    {
        return reversePrimerCode;
    }
    
    public void setReversePrimerCode(String reversePrimerCode)
    {
        this.reversePrimerCode = reversePrimerCode;
    }
    
    public BigDecimal getConcentration()
    {
        return concentration;
    }
    
    public void setConcentration(BigDecimal concentration)
    {
        this.concentration = concentration;
    }
    
    public BigDecimal getVolume()
    {
        return volume;
    }
    
    public void setVolume(BigDecimal volume)
    {
        this.volume = volume;
    }
    
    public String getPcrTestCode()
    {
        return pcrTestCode;
    }
    
    public void setPcrTestCode(String pcrTestCode)
    {
        this.pcrTestCode = pcrTestCode;
    }
    
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }
    
    public String getProduct()
    {
        return product;
    }
    
    public void setProduct(String product)
    {
        this.product = product;
    }
    
    public String getPrimerId()
    {
        return primerId;
    }
    
    public void setPrimerId(String primerId)
    {
        this.primerId = primerId;
    }
    
    public String getGene()
    {
        return gene;
    }
    
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
    public Integer getStorageStatus()
    {
        return storageStatus;
    }
    
    public void setStorageStatus(Integer storageStatus)
    {
        this.storageStatus = storageStatus;
    }

    public Integer getIfUrgent()
    {
        return ifUrgent;
    }

    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }

    public Date getUrgentUpdateTime()
    {
        return urgentUpdateTime;
    }

    public void setUrgentUpdateTime(Date urgentUpdateTime)
    {
        this.urgentUpdateTime = urgentUpdateTime;
    }

    public String getUrgentName()
    {
        return urgentName;
    }

    public void setUrgentName(String urgentName)
    {
        this.urgentName = urgentName;
    }

    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }

    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }
    
}
