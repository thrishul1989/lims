package com.todaysoft.lims.sample.entity.sampleBack;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SAMPLE_BACK_PROCESSING")
public class SampleBackProcessing extends UuidKeyEntity
{
    private static final long serialVersionUID = 8620796111843066021L;
    
    private SampleBackApply sampleBackApply;
    
    private String sampleCode;//样本编号
    
    private String sampleId;//样本id
    
    private BigDecimal volume;//样本体积
    
    private String remark;//备注
    
    private String returnStatus;
    
    @Column(name = "SAMPLE_CODE")
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    @Column(name = "VOLUME")
    public BigDecimal getVolume()
    {
        return volume;
    }
    
    public void setVolume(BigDecimal volume)
    {
        this.volume = volume;
    }
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @ManyToOne
    @JoinColumn(name = "SAMPLE_BACK_APPLY_ID")
    @JsonIgnore
    public SampleBackApply getSampleBackApply()
    {
        return sampleBackApply;
    }
    
    public void setSampleBackApply(SampleBackApply sampleBackApply)
    {
        this.sampleBackApply = sampleBackApply;
    }
    
    @Column(name = "SAMPLE_ID")
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    @Column(name = "RETURN_STATUS")
    public String getReturnStatus()
    {
        return returnStatus;
    }
    
    public void setReturnStatus(String returnStatus)
    {
        this.returnStatus = returnStatus;
    }
    
}
