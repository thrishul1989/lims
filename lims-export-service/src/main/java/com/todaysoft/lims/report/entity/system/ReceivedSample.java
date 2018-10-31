package com.todaysoft.lims.report.entity.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "LIMS_RECEIVED_SAMPLE")
public class ReceivedSample implements Serializable
{
    private static final long serialVersionUID = 5540635164703890827L;
    
    private String sampleId;
    
    private String orderId;
    
    private String businessType;
    
    private String purpose;//1-验证  2-检测 3-对照
    
    private String sampleCode;
    
    private String sampleTypeId;
    
    private String sampleTypeName;
    
    private String sampleName;
    
    private Date samplingDate;
    
    private String lsLocation;// '长期存储位置编号',
    
    private String tsLocation;//'临时存储位置编号',
    
    @Id
    @Column(name = "SAMPLE_ID")
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "BUSINESS_TYPE")
    public String getBusinessType()
    {
        return businessType;
    }
    
    public void setBusinessType(String businessType)
    {
        this.businessType = businessType;
    }
    
    @Column(name = "PURPOSE")
    public String getPurpose()
    {
        return purpose;
    }
    
    public void setPurpose(String purpose)
    {
        this.purpose = purpose;
    }
    
    @Column(name = "SAMPLE_CODE")
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    @Column(name = "TYPE_ID")
    public String getSampleTypeId()
    {
        return sampleTypeId;
    }
    
    public void setSampleTypeId(String sampleTypeId)
    {
        this.sampleTypeId = sampleTypeId;
    }
    
    @Column(name = "TYPE_NAME")
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }
    
    @Column(name = "SAMPLE_NAME")
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    @Column(name = "SAMPLING_DATE")
    @Temporal(TemporalType.DATE)
    public Date getSamplingDate()
    {
        return samplingDate;
    }
    
    public void setSamplingDate(Date samplingDate)
    {
        this.samplingDate = samplingDate;
    }
    
    @Column(name = "LS_LOCATION")
    public String getLsLocation()
    {
        return lsLocation;
    }
    
    public void setLsLocation(String lsLocation)
    {
        this.lsLocation = lsLocation;
    }
    
    @Column(name = "TS_LOCATION")
    public String getTsLocation()
    {
        return tsLocation;
    }
    
    public void setTsLocation(String tsLocation)
    {
        this.tsLocation = tsLocation;
    }
    
}
