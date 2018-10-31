package com.todaysoft.lims.sample.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_SAMPLE_DETAILS")
public class OrderSampleDetails extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private String orderCode;
    
    private String customerId;//客户ID
    
    private String customerName;//客户姓名
    
    private String salesmanId;
    
    private String salesmanName;
    
    private String sampleId; //传给前台显示  ---样本id
    
    private Integer sampleBtype;//传给前台显示并传入后台  ---业务类型：1-非科研主样本 2-非科研附属样本 3-科研样本
    
    private String sampleTypeId;
    
    private String sampleTypeName;
    
    private String sampleCode;
    
    private String sampleName;
    
    private BigDecimal sampleSize;
    
    private Date samplingDate;
    
    private Integer purpose;// '1-检测 2-验证 3-对照',
    
    private Integer transportStatus;
    
    private Date updateTime;
    
    private Integer sampleErrorStatus;
    
    private String errorReason;
    
    private Integer orderType;
    
    private Date acceptSampleTime;
    
    private String sampleErrorNewNo; //异常样本新的编号
    
    private String errorDealRemark; //异常不送样备注
    
    private String errorOperatorId; //异常处理人id
    
    private String errorOperatorName; //异常处理NAME
    
    private Date errorOperatorTime;//异常处理时间
    
    private Integer sampleStorageStatus;
    
    @Transient
    public String getErrorOperatorId()
    {
        return errorOperatorId;
    }
    
    public void setErrorOperatorId(String errorOperatorId)
    {
        this.errorOperatorId = errorOperatorId;
    }
    
    @Transient
    public String getErrorOperatorName()
    {
        return errorOperatorName;
    }
    
    public void setErrorOperatorName(String errorOperatorName)
    {
        this.errorOperatorName = errorOperatorName;
    }
    
    @Transient
    public Date getErrorOperatorTime()
    {
        return errorOperatorTime;
    }
    
    public void setErrorOperatorTime(Date errorOperatorTime)
    {
        this.errorOperatorTime = errorOperatorTime;
    }
    
    @Column(name = "SAMPLE_ERROR_NEW_NO")
    public String getSampleErrorNewNo()
    {
        return sampleErrorNewNo;
    }
    
    public void setSampleErrorNewNo(String sampleErrorNewNo)
    {
        this.sampleErrorNewNo = sampleErrorNewNo;
    }
    
    @Column(name = "ERROR_DEAL_REMARK")
    public String getErrorDealRemark()
    {
        return errorDealRemark;
    }
    
    public void setErrorDealRemark(String errorDealRemark)
    {
        this.errorDealRemark = errorDealRemark;
    }
    
    @Column(name = "ACCEPT_SAMPLE_TIME")
    public Date getAcceptSampleTime()
    {
        return acceptSampleTime;
    }
    
    public void setAcceptSampleTime(Date acceptSampleTime)
    {
        this.acceptSampleTime = acceptSampleTime;
    }
    
    @Column(name = "ORDER_TYPE")
    public Integer getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(Integer orderType)
    {
        this.orderType = orderType;
    }
    
    @Column(name = "SAMPLE_ERROR_STATUS")
    public Integer getSampleErrorStatus()
    {
        return sampleErrorStatus;
    }
    
    public void setSampleErrorStatus(Integer sampleErrorStatus)
    {
        this.sampleErrorStatus = sampleErrorStatus;
    }
    
    @Column(name = "ERROR_REASON")
    public String getErrorReason()
    {
        return errorReason;
    }
    
    public void setErrorReason(String errorReason)
    {
        this.errorReason = errorReason;
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
    
    @Column(name = "ORDER_CODE")
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    @Column(name = "CUSTOMER_ID")
    public String getCustomerId()
    {
        return customerId;
    }
    
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
    
    @Column(name = "CUSTOMER_NAME")
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    @Column(name = "SALESMAN_ID")
    public String getSalesmanId()
    {
        return salesmanId;
    }
    
    public void setSalesmanId(String salesmanId)
    {
        this.salesmanId = salesmanId;
    }
    
    @Column(name = "SALESMAN_NAME")
    public String getSalesmanName()
    {
        return salesmanName;
    }
    
    public void setSalesmanName(String salesmanName)
    {
        this.salesmanName = salesmanName;
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
    
    @Column(name = "SAMPLE_BTYPE")
    public Integer getSampleBtype()
    {
        return sampleBtype;
    }
    
    public void setSampleBtype(Integer sampleBtype)
    {
        this.sampleBtype = sampleBtype;
    }
    
    @Column(name = "SAMPLE_TYPE_ID")
    public String getSampleTypeId()
    {
        return sampleTypeId;
    }
    
    public void setSampleTypeId(String sampleTypeId)
    {
        this.sampleTypeId = sampleTypeId;
    }
    
    @Column(name = "SAMPLE_TYPE_NAME")
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
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
    
    @Column(name = "SAMPLE_SIZE")
    public BigDecimal getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(BigDecimal sampleSize)
    {
        this.sampleSize = sampleSize;
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
    
    @Column(name = "PURPOSE")
    public Integer getPurpose()
    {
        return purpose;
    }
    
    public void setPurpose(Integer purpose)
    {
        this.purpose = purpose;
    }
    
    @Column(name = "TRANSPORT_STATUS")
    public Integer getTransportStatus()
    {
        return transportStatus;
    }
    
    public void setTransportStatus(Integer transportStatus)
    {
        this.transportStatus = transportStatus;
    }
    
    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Transient
    public Integer getSampleStorageStatus()
    {
        return sampleStorageStatus;
    }

    public void setSampleStorageStatus(Integer sampleStorageStatus)
    {
        this.sampleStorageStatus = sampleStorageStatus;
    }
    
}
