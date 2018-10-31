package com.todaysoft.lims.testing.base.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_RESEARCH_SAMPLE", uniqueConstraints = {@UniqueConstraint(columnNames = "SAMPLE_CODE")})
public class OrderResearchSample extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String sampleCode; //样本编号
    
    private String sampleTypeId;//样本类型ID
    
    private String sampleName; //样本名称
    
    private BigDecimal sampleSize;//样本量
    
    private String samplingDate;//采样时间
    
    private String diagnosis;
    
    private String focusGene;
    
    private String familyRelation;
    
    private String remark;
    
    private String groupName; //分组名
    
    private Order order; //订单对象
    
    private List<OrderResearchProduct> orderResearchProduct = new ArrayList<OrderResearchProduct>();
    
    private Integer samplePackageStatus = 1; //打包运输状态：0：待送样；1：送样中；2：已接收样本；3：异常样本；4：已返样样本；
    
    private Integer sampleErrorStatus = 0; //异常样本处理状态,0：未处理；1：已处理；
    
    private String sampleErrorNewNo; //异常样本新的编号
    
    private Integer sampleBackStatus = 0; //返样状态：0：待返样；1：返样中；2：已返样；3：无返样；
    
    private Date updateTime;
    
    private String sampleBackOption;//返样备注
    
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
    
    public OrderResearchSample()
    {
        super();
    }
    
    @Column(name = "GROUP_NAME")
    public String getGroupName()
    {
        return groupName;
    }
    
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
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
    
    @Column(name = "SAMPLE_NAME")
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    @Column(name = "DIAGNOSIS")
    public String getDiagnosis()
    {
        return diagnosis;
    }
    
    public void setDiagnosis(String diagnosis)
    {
        this.diagnosis = diagnosis;
    }
    
    @Column(name = "FOCUS_GENE")
    public String getFocusGene()
    {
        return focusGene;
    }
    
    public void setFocusGene(String focusGene)
    {
        this.focusGene = focusGene;
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
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
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
    
    @OneToMany(mappedBy = "orderResearchSample", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.SUBSELECT)
    public List<OrderResearchProduct> getOrderResearchProduct()
    {
        return orderResearchProduct;
    }
    
    public void setOrderResearchProduct(List<OrderResearchProduct> orderResearchProduct)
    {
        this.orderResearchProduct = orderResearchProduct;
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
    
    @Column(name = "SAMPLING_DATE")
    public String getSamplingDate()
    {
        return samplingDate;
    }
    
    public void setSamplingDate(String samplingDate)
    {
        this.samplingDate = samplingDate;
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
