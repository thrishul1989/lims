package com.todaysoft.lims.sample.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_SUBSIDIARY_SAMPLE")
public class OrderSubsidiarySample extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String sampleCode; //样本编号
    
    private String sampleTypeId;//样本类型ID
    
    private BigDecimal sampleSize;//样本量
    
    private String samplingDate;//采样时间
    
    private String familyRelation;
    
    private String ownerName;
    
    private Integer ownerAge;
    
    private Integer ownerPhenotype;
    
    private int purpose;
    
    private OrderExaminee orderExaminee; //订单受检人
    
    private Order order; //订单对象
    
    private Integer samplePackageStatus = 1; //打包运输状态：0：待送样；1：送样中；2：已接收样本；3：异常样本；4：已返样样本；
    
    private Integer sampleErrorStatus = 0; //异常样本处理状态,0：未处理；1：已处理；
    
    private String sampleErrorNewNo; //异常样本新的编号
    
    private Integer sampleBackStatus = 0; //返样状态：0：待返样；1：返样中；2：已返样；3：无返样；
    
    private String sampleBackOption;//返样备注
    
    private Date updateTime;
    
    private Date acceptTime;
    
    private String errorType;
    
    private String errorReason;
    
    private String errorDealRemark;
    
    private String errorOperatorId; //异常处理人id
    
    private String errorOperatorName; //异常处理NAME
    
    private Date errorOperatorTime;//异常处理时间
    
    private Date receiveTime;
    
    @Column(name = "ERROR_OPERATOR_ID")
    public String getErrorOperatorId()
    {
        return errorOperatorId;
    }
    
    public void setErrorOperatorId(String errorOperatorId)
    {
        this.errorOperatorId = errorOperatorId;
    }
    
    @Column(name = "ERROR_OPERATOR_NAME")
    public String getErrorOperatorName()
    {
        return errorOperatorName;
    }
    
    public void setErrorOperatorName(String errorOperatorName)
    {
        this.errorOperatorName = errorOperatorName;
    }
    
    @Column(name = "ERROR_OPERATOR_TIME")
    public Date getErrorOperatorTime()
    {
        return errorOperatorTime;
    }
    
    public void setErrorOperatorTime(Date errorOperatorTime)
    {
        this.errorOperatorTime = errorOperatorTime;
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
    public Date getAcceptTime()
    {
        return acceptTime;
    }
    
    public void setAcceptTime(Date acceptTime)
    {
        this.acceptTime = acceptTime;
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
    
    public OrderSubsidiarySample()
    {
        super();
    }
    
    @Column(name = "FAMILY_RELATION")
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    
    @Column(name = "OWNER_NAME")
    public String getOwnerName()
    {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
    
    @Column(name = "OWNER_AGE")
    public Integer getOwnerAge()
    {
        return ownerAge;
    }
    
    public void setOwnerAge(Integer ownerAge)
    {
        this.ownerAge = ownerAge;
    }
    
    @Column(name = "OWNER_PHENOTYPE")
    public Integer getOwnerPhenotype()
    {
        return ownerPhenotype;
    }
    
    public void setOwnerPhenotype(Integer ownerPhenotype)
    {
        this.ownerPhenotype = ownerPhenotype;
    }
    
    @Column(name = "PURPOSE")
    public int getPurpose()
    {
        return purpose;
    }
    
    public void setPurpose(int purpose)
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
        this.sampleCode = StringUtils.isNotEmpty(sampleCode) ? sampleCode.toUpperCase() : sampleCode;
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
    public String getSamplingDate()
    {
        return samplingDate;
    }
    
    public void setSamplingDate(String samplingDate)
    {
        this.samplingDate = samplingDate;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    @JsonIgnore
    public Order getOrder()
    {
        return order;
    }
    
    public void setOrder(Order order)
    {
        this.order = order;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EXAMINEE_ID")
    @JsonIgnore
    public OrderExaminee getOrderExaminee()
    {
        return orderExaminee;
    }
    
    public void setOrderExaminee(OrderExaminee orderExaminee)
    {
        this.orderExaminee = orderExaminee;
    }
    
    @Column(name = "SAMPLE_PACKAGE_STATUS")
    public Integer getSamplePackageStatus()
    {
        return samplePackageStatus;
    }
    
    public void setSamplePackageStatus(Integer samplePackageStatus)
    {
        this.samplePackageStatus = samplePackageStatus;
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
    
    @Column(name = "SAMPLE_ERROR_NEW_NO")
    public String getSampleErrorNewNo()
    {
        return sampleErrorNewNo;
    }
    
    public void setSampleErrorNewNo(String sampleErrorNewNo)
    {
        this.sampleErrorNewNo = sampleErrorNewNo;
    }
    
    @Column(name = "SAMPLE_BACK_STATUS")
    public Integer getSampleBackStatus()
    {
        return sampleBackStatus;
    }
    
    public void setSampleBackStatus(Integer sampleBackStatus)
    {
        this.sampleBackStatus = sampleBackStatus;
    }
    
    @Column(name = "SAMPLE_BACK_OPTION")
    public String getSampleBackOption()
    {
        return sampleBackOption;
    }
    
    public void setSampleBackOption(String sampleBackOption)
    {
        this.sampleBackOption = sampleBackOption;
    }
    
    @Column(name = "SAMPLE_ERROR_TYPE")
    public String getErrorType()
    {
        return errorType;
    }
    
    public void setErrorType(String errorType)
    {
        this.errorType = errorType;
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
    
    @Transient
    public Date getReceiveTime()
    {
        return receiveTime;
    }
    
    public void setReceiveTime(Date receiveTime)
    {
        this.receiveTime = receiveTime;
    }
    
}
