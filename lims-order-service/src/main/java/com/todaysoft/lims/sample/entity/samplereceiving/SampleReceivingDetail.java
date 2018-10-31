package com.todaysoft.lims.sample.entity.samplereceiving;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SAMPLE_RECEIVING_DETAIL")
public class SampleReceivingDetail extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String sampleCode;//'样本编号',
    
    private int mcStatus;//'匹配状态：0-不匹配 1-匹配',
    
    private int qcStatus;// '质检状态：0-不合格 1-合格',
    
    private String qcRemark;//'质检备注',
    
    private SampleReceiving sampleReceiving;
    
    private int btype;//接收页面的业务类型
    
    private String sampleName;
    
    private String familyRelation;
    
    @Column(name = "SAMPLE_CODE")
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    @Column(name = "MC_STATUS")
    public int getMcStatus()
    {
        return mcStatus;
    }
    
    public void setMcStatus(int mcStatus)
    {
        this.mcStatus = mcStatus;
    }
    
    @Column(name = "QC_STATUS")
    public int getQcStatus()
    {
        return qcStatus;
    }
    
    public void setQcStatus(int qcStatus)
    {
        this.qcStatus = qcStatus;
    }
    
    @Column(name = "QC_REMARK")
    public String getQcRemark()
    {
        return qcRemark;
    }
    
    public void setQcRemark(String qcRemark)
    {
        this.qcRemark = qcRemark;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RECORD_ID")
    public SampleReceiving getSampleReceiving()
    {
        return sampleReceiving;
    }
    
    public void setSampleReceiving(SampleReceiving sampleReceiving)
    {
        this.sampleReceiving = sampleReceiving;
    }
    
    @Transient
    public int getBtype()
    {
        return btype;
    }
    
    public void setBtype(int btype)
    {
        this.btype = btype;
    }
    
    @Transient
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    @Transient
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    
}
