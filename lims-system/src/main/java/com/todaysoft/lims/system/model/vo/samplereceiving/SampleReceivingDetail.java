package com.todaysoft.lims.system.model.vo.samplereceiving;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class SampleReceivingDetail extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String recordId;// '接收记录',
    
    private String sampleCode;//'样本编号',
    
    private String sampleName;//样本名称
    
    private String familyRelation; //家系关系
    
    private int mcStatus;//'匹配状态：0-不匹配 1-匹配',
    
    private int qcStatus;// '质检状态：0-不合格 1-合格',
    
    private String qcRemark;//'质检备注',
    
    private int btype;//接收页面的业务类型
    
    private SampleReceiving sampleReceiving;
    
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public SampleReceiving getSampleReceiving()
    {
        return sampleReceiving;
    }
    
    public void setSampleReceiving(SampleReceiving sampleReceiving)
    {
        this.sampleReceiving = sampleReceiving;
    }
    
    public String getRecordId()
    {
        return recordId;
    }
    
    public void setRecordId(String recordId)
    {
        this.recordId = recordId;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public int getMcStatus()
    {
        return mcStatus;
    }
    
    public void setMcStatus(int mcStatus)
    {
        this.mcStatus = mcStatus;
    }
    
    public int getQcStatus()
    {
        return qcStatus;
    }
    
    public void setQcStatus(int qcStatus)
    {
        this.qcStatus = qcStatus;
    }
    
    public String getQcRemark()
    {
        return qcRemark;
    }
    
    public void setQcRemark(String qcRemark)
    {
        this.qcRemark = qcRemark;
    }
    
    public int getBtype()
    {
        return btype;
    }
    
    public void setBtype(int btype)
    {
        this.btype = btype;
    }
    
}
