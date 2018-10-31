package com.todaysoft.lims.system.model.vo.order;

import java.math.BigDecimal;
import java.util.Date;

import com.todaysoft.lims.persist.UuidKeyEntity;

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
    
    private String unit;//样本单位
    
    private Integer sampleErrorStatus; //异常处理状态  ,0：未处理；1：新增样本处理；2：无送样处理
    
    private String errorReason;//异常原因  
    
    private Integer orderType;
    
    private Date acceptSampleTime;
    
    private String sampleErrorNewNo; //异常样本新的编号
    
    private String errorDealRemark;
    
    private String errorOperatorId; //异常处理人id
    
    private String errorOperatorName; //异常处理NAME
    
    private Date errorOperatorTime;//异常处理时间
    
    private String sampleErrorNewTypeName;//异常样本新的类型
    
    private Date sampleErrorNewsamplingDate;//异常样本新的收样时间
    
    private BigDecimal sampleErrorNewSize;//异常样本新的样本量
    
    private String sampleErrorUnit;
    
    private Integer sampleStorageStatus;

    private String prjManagerName;

    public String getPrjManagerName() {
        return prjManagerName;
    }

    public void setPrjManagerName(String prjManagerName) {
        this.prjManagerName = prjManagerName;
    }

    public String getSampleErrorUnit()
    {
        return sampleErrorUnit;
    }
    
    public void setSampleErrorUnit(String sampleErrorUnit)
    {
        this.sampleErrorUnit = sampleErrorUnit;
    }
    
    public String getSampleErrorNewTypeName()
    {
        return sampleErrorNewTypeName;
    }
    
    public void setSampleErrorNewTypeName(String sampleErrorNewTypeName)
    {
        this.sampleErrorNewTypeName = sampleErrorNewTypeName;
    }
    
    public Date getSampleErrorNewsamplingDate()
    {
        return sampleErrorNewsamplingDate;
    }
    
    public void setSampleErrorNewsamplingDate(Date sampleErrorNewsamplingDate)
    {
        this.sampleErrorNewsamplingDate = sampleErrorNewsamplingDate;
    }
    
    public BigDecimal getSampleErrorNewSize()
    {
        return sampleErrorNewSize;
    }
    
    public void setSampleErrorNewSize(BigDecimal sampleErrorNewSize)
    {
        this.sampleErrorNewSize = sampleErrorNewSize;
    }
    
    public String getErrorOperatorId()
    {
        return errorOperatorId;
    }
    
    public void setErrorOperatorId(String errorOperatorId)
    {
        this.errorOperatorId = errorOperatorId;
    }
    
    public String getErrorOperatorName()
    {
        return errorOperatorName;
    }
    
    public void setErrorOperatorName(String errorOperatorName)
    {
        this.errorOperatorName = errorOperatorName;
    }
    
    public Date getErrorOperatorTime()
    {
        return errorOperatorTime;
    }
    
    public void setErrorOperatorTime(Date errorOperatorTime)
    {
        this.errorOperatorTime = errorOperatorTime;
    }
    
    public String getSampleErrorNewNo()
    {
        return sampleErrorNewNo;
    }
    
    public void setSampleErrorNewNo(String sampleErrorNewNo)
    {
        this.sampleErrorNewNo = sampleErrorNewNo;
    }
    
    public String getErrorDealRemark()
    {
        return errorDealRemark;
    }
    
    public void setErrorDealRemark(String errorDealRemark)
    {
        this.errorDealRemark = errorDealRemark;
    }
    
    public Date getAcceptSampleTime()
    {
        return acceptSampleTime;
    }
    
    public void setAcceptSampleTime(Date acceptSampleTime)
    {
        this.acceptSampleTime = acceptSampleTime;
    }
    
    public Integer getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(Integer orderType)
    {
        this.orderType = orderType;
    }
    
    public Integer getSampleErrorStatus()
    {
        return sampleErrorStatus;
    }
    
    public void setSampleErrorStatus(Integer sampleErrorStatus)
    {
        this.sampleErrorStatus = sampleErrorStatus;
    }
    
    public String getErrorReason()
    {
        return errorReason;
    }
    
    public void setErrorReason(String errorReason)
    {
        this.errorReason = errorReason;
    }
    
    public String getUnit()
    {
        return unit;
    }
    
    public void setUnit(String unit)
    {
        this.unit = unit;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getCustomerId()
    {
        return customerId;
    }
    
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getSalesmanId()
    {
        return salesmanId;
    }
    
    public void setSalesmanId(String salesmanId)
    {
        this.salesmanId = salesmanId;
    }
    
    public String getSalesmanName()
    {
        return salesmanName;
    }
    
    public void setSalesmanName(String salesmanName)
    {
        this.salesmanName = salesmanName;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public Integer getSampleBtype()
    {
        return sampleBtype;
    }
    
    public void setSampleBtype(Integer sampleBtype)
    {
        this.sampleBtype = sampleBtype;
    }
    
    public String getSampleTypeId()
    {
        return sampleTypeId;
    }
    
    public void setSampleTypeId(String sampleTypeId)
    {
        this.sampleTypeId = sampleTypeId;
    }
    
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public BigDecimal getSampleSize()
    {
        return sampleSize;
    }
    
    public void setSampleSize(BigDecimal sampleSize)
    {
        this.sampleSize = sampleSize;
    }
    
    public Date getSamplingDate()
    {
        return samplingDate;
    }
    
    public void setSamplingDate(Date samplingDate)
    {
        this.samplingDate = samplingDate;
    }
    
    public Integer getPurpose()
    {
        return purpose;
    }
    
    public void setPurpose(Integer purpose)
    {
        this.purpose = purpose;
    }
    
    public Integer getTransportStatus()
    {
        return transportStatus;
    }
    
    public void setTransportStatus(Integer transportStatus)
    {
        this.transportStatus = transportStatus;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public Integer getSampleStorageStatus()
    {
        return sampleStorageStatus;
    }

    public void setSampleStorageStatus(Integer sampleStorageStatus)
    {
        this.sampleStorageStatus = sampleStorageStatus;
    }
    
}
