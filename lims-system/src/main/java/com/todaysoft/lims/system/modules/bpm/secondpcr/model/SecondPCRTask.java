package com.todaysoft.lims.system.modules.bpm.secondpcr.model;

import java.util.Date;

public class SecondPCRTask
{
    private String id;

    private String sampleName;

    private String sampleCode;

    private String forwardPrimerCode;

    private String pcrTaskCode;//pcr任务编号

    private String pcrTestCode;//pcr试验编号

    private String spcrTestCode;//二次pcr实验编号

    private String sixNineInfo;//96孔板信息

    private boolean resubmit;

    private Integer resubmitCount;

    private String forwardPrimerLocationTemp;

    private String reversePrimerLocationTemp;

    private String reversePrimerCode;

    private String description;//说明-

    private String combineCode;

    private String ngsSeqCode;//上机号

    private Integer flag;//1 一次PCR直接到二次PCR  2是二次PCR下达过来的
    
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
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getPcrTestCode()
    {
        return pcrTestCode;
    }
    
    public void setPcrTestCode(String pcrTestCode)
    {
        this.pcrTestCode = pcrTestCode;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getForwardPrimerLocationTemp()
    {
        return forwardPrimerLocationTemp;
    }
    
    public void setForwardPrimerLocationTemp(String forwardPrimerLocationTemp)
    {
        this.forwardPrimerLocationTemp = forwardPrimerLocationTemp;
    }
    
    public String getForwardPrimerCode()
    {
        return forwardPrimerCode;
    }
    
    public void setForwardPrimerCode(String forwardPrimerCode)
    {
        this.forwardPrimerCode = forwardPrimerCode;
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
    
    public String getPcrTaskCode()
    {
        return pcrTaskCode;
    }
    
    public void setPcrTaskCode(String pcrTaskCode)
    {
        this.pcrTaskCode = pcrTaskCode;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getSpcrTestCode() {
        return spcrTestCode;
    }

    public void setSpcrTestCode(String spcrTestCode) {
        this.spcrTestCode = spcrTestCode;
    }

    public String getSixNineInfo() {
        return sixNineInfo;
    }

    public void setSixNineInfo(String sixNineInfo) {
        this.sixNineInfo = sixNineInfo;
    }

    public String getNgsSeqCode() {
        return ngsSeqCode;
    }

    public void setNgsSeqCode(String ngsSeqCode) {
        this.ngsSeqCode = ngsSeqCode;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
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
