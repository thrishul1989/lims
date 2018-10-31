package com.todaysoft.lims.testing.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

import java.util.Date;

@Entity
@Table(name = "TECHNICAL_ANALYSIS_TASK")
public class TechnicalAnalysisTask  extends UuidKeyEntity
{
    private static final long serialVersionUID = -8058293518849005144L;

    private String receivedSampleCode;
    
    private String orderId;
    
    private String productCode;
    
    private String userId;
    
    private String familyGroupId;

    private String receivedSampleType;

    private String receivedSampleName;
    
    private Integer status;

    private String name;

    private Date endTime;

    private Integer endType;
    
    private String semantic;
    
    @Column(name = "RECEIVED_SAMPLE_CODE")
    public String getReceivedSampleCode()
    {
        return receivedSampleCode;
    }
    
    public void setReceivedSampleCode(String receivedSampleCode)
    {
        this.receivedSampleCode = receivedSampleCode;
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
    
    @Column(name = "PRODUCT_CODE")
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    @Column(name = "USER_ID")
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    @Column(name = "FAMILY_GROUP_ID")
    public String getFamilyGroupId()
    {
        return familyGroupId;
    }
    
    public void setFamilyGroupId(String familyGroupId)
    {
        this.familyGroupId = familyGroupId;
    }

    @Column(name = "RECEIVED_SAMPLE_TYPE")
    public String getReceivedSampleType() {
        return receivedSampleType;
    }

    public void setReceivedSampleType(String receivedSampleType) {
        this.receivedSampleType = receivedSampleType;
    }
    @Column(name = "RECEIVED_SAMPLE_NAME")
    public String getReceivedSampleName() {
        return receivedSampleName;
    }

    public void setReceivedSampleName(String receivedSampleName) {
        this.receivedSampleName = receivedSampleName;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "END_TIME")
    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    @Column(name = "STATUS")
    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    @Column(name = "END_TYPE")
    public Integer getEndType()
    {
        return endType;
    }
    
    public void setEndType(Integer endType)
    {
        this.endType = endType;
    }

    @Column(name = "SEMANTIC")
    public String getSemantic()
    {
        return semantic;
    }

    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
}