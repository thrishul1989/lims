package com.todaysoft.lims.system.model.vo.contract.reportForm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

public class ReportSystemContractSampleInfo extends UuidKeyEntity
{
    private static final long serialVersionUID = 8069209429558551190L;
    private String taskId;
    private ReportSystemContractInfo contractInfo;
    private String sampleKind;//样本种属
    private String sampleType;//样本类型
    
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public ReportSystemContractInfo getContractInfo()
    {
        return contractInfo;
    }
    public void setContractInfo(ReportSystemContractInfo contractInfo)
    {
        this.contractInfo = contractInfo;
    }
    
    public String getSampleKind()
    {
        return sampleKind;
    }
    public void setSampleKind(String sampleKind)
    {
        this.sampleKind = sampleKind;
    }
    
    public String getSampleType()
    {
        return sampleType;
    }
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
}
