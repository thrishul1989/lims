package com.todaysoft.lims.sample.entity.samplereceiving;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "LIMS_RECEIVED_SAMPLE")
@Builder(toBuilder = true)
@AllArgsConstructor
public class ReceivedSample implements Serializable
{
    
    public ReceivedSample()
    {
        super();
    }
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String sampleId;// '主键',
    
    private String orderId; //'冗余字段-订单ID',
    
    private Integer businessType; //业务类型：1-非科研主样本 2-非科研附属样本 3-科研样本
    
    private String typeId; //'冗余字段-类型ID',
    
    private String typeName;//'冗余字段-类型名称',
    
    private String sampleCode;//冗余字段-样本编号',
    
    private String sampleName;//'冗余字段-样本名称',
    
    private Date samplingDate;//'冗余字段-采样时间',
    
    private BigDecimal sampleSize;//'冗余字段-样本量',
    
    private String lsLocation;// '长期存储位置编号',
    
    private BigDecimal lsSize;//'长期存储量',
    
    private Integer lsStatus;//'长期存储状态：1-入库 2-出库',
    
    private String tsLocation;//'临时存储位置编号',
    
    private BigDecimal tsSize;//'临时存储量',
    
    private Integer tsStatus;// '临时存储状态：1-入库 2-出库',
    
    private Integer purpose;// '1-检测 2-验证 3-对照',
    
    private Integer processStatus; //进度状态 1： 收样 2： 转存 3： 入库 
    
    private String parentId; //返回一个已保存id
    
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
    public Integer getBusinessType()
    {
        return businessType;
    }
    
    public void setBusinessType(Integer businessType)
    {
        this.businessType = businessType;
    }
    
    @Column(name = "TYPE_ID")
    public String getTypeId()
    {
        return typeId;
    }
    
    public void setTypeId(String typeId)
    {
        this.typeId = typeId;
    }
    
    @Column(name = "TYPE_NAME")
    public String getTypeName()
    {
        return typeName;
    }
    
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
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
    
    @Column(name = "LS_SIZE")
    public BigDecimal getLsSize()
    {
        return lsSize;
    }
    
    public void setLsSize(BigDecimal lsSize)
    {
        this.lsSize = lsSize;
    }
    
    @Column(name = "LS_STATUS")
    public Integer getLsStatus()
    {
        return lsStatus;
    }
    
    public void setLsStatus(Integer lsStatus)
    {
        this.lsStatus = lsStatus;
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
    
    @Column(name = "TS_SIZE")
    public BigDecimal getTsSize()
    {
        return tsSize;
    }
    
    public void setTsSize(BigDecimal tsSize)
    {
        this.tsSize = tsSize;
    }
    
    @Column(name = "TS_STATUS")
    public Integer getTsStatus()
    {
        return tsStatus;
    }
    
    public void setTsStatus(Integer tsStatus)
    {
        this.tsStatus = tsStatus;
    }
    
    @Column(name = "PURPOSE")
    public Integer getPurpose()
    {
        return purpose;
    }
    
    public void setPurpose(Integer purpose)
    {
        this.purpose = purpose;
    }
    
    @Transient
    public String getParentId()
    {
        return parentId;
    }
    
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }
    
    @Column(name = "PROCESS_STATUS")
    public Integer getProcessStatus()
    {
        return processStatus;
    }
    
    public void setProcessStatus(Integer processStatus)
    {
        this.processStatus = processStatus;
    }
    
    @Column(name = "SAMPLE_SIZE")
    public BigDecimal getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(BigDecimal sampleSize)
    {
        this.sampleSize = sampleSize;
    }
    
}
