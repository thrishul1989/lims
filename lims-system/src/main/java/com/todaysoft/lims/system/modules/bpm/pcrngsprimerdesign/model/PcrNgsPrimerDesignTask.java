package com.todaysoft.lims.system.modules.bpm.pcrngsprimerdesign.model;

import java.util.Date;

public class PcrNgsPrimerDesignTask
{
    private String id;
    
    private String gene;
    
    private String codingExon;
    
    private String chromosomeNumber;
    
    private String chromosomeLocation;
    
    private Long pcrStartPoint; //PCR起始点
    
    private Long pcrEndPoint; //PCR终止点
    
    private String forwardPrimerSequence;//正向引物序列
    
    private String reversePrimerSequence;//反向引物序列
    
    private Date createTime;

    private boolean resubmit;

    private Integer resubmitCount;

    private String remark;
    
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
    public boolean isResubmit() {
        return resubmit;
    }

    public void setResubmit(boolean resubmit) {
        this.resubmit = resubmit;
    }

    public Integer getResubmitCount() {
        return resubmitCount;
    }

    public void setResubmitCount(Integer resubmitCount) {
        this.resubmitCount = resubmitCount;
    }

    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getGene()
    {
        return gene;
    }
    
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
    public String getCodingExon()
    {
        return codingExon;
    }
    
    public void setCodingExon(String codingExon)
    {
        this.codingExon = codingExon;
    }
    
    public String getChromosomeNumber()
    {
        return chromosomeNumber;
    }
    
    public void setChromosomeNumber(String chromosomeNumber)
    {
        this.chromosomeNumber = chromosomeNumber;
    }
    
    public String getChromosomeLocation()
    {
        return chromosomeLocation;
    }
    
    public void setChromosomeLocation(String chromosomeLocation)
    {
        this.chromosomeLocation = chromosomeLocation;
    }
    
    public Long getPcrStartPoint()
    {
        return pcrStartPoint;
    }
    
    public void setPcrStartPoint(Long pcrStartPoint)
    {
        this.pcrStartPoint = pcrStartPoint;
    }
    
    public Long getPcrEndPoint()
    {
        return pcrEndPoint;
    }
    
    public void setPcrEndPoint(Long pcrEndPoint)
    {
        this.pcrEndPoint = pcrEndPoint;
    }
    
    public String getForwardPrimerSequence()
    {
        return forwardPrimerSequence;
    }
    
    public void setForwardPrimerSequence(String forwardPrimerSequence)
    {
        this.forwardPrimerSequence = forwardPrimerSequence;
    }
    
    public String getReversePrimerSequence()
    {
        return reversePrimerSequence;
    }
    
    public void setReversePrimerSequence(String reversePrimerSequence)
    {
        this.reversePrimerSequence = reversePrimerSequence;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
