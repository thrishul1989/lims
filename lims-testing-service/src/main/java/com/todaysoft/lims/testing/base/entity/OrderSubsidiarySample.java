package com.todaysoft.lims.testing.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "LIMS_ORDER_SUBSIDIARY_SAMPLE")
public class OrderSubsidiarySample extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String sampleCode; //样本编号
    
    private String familyRelation;
    
    private String sampleTypeId;//样本类型ID
    
    private int purpose;
    
    private Order order; //订单对象
    
    private String ownerName;//
    
    private Integer sampleErrorStatus;
    
    @Column(name="OWNER_NAME")
    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
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
    
    @Column(name = "SAMPLE_TYPE_ID")
    public String getSampleTypeId()
    {
        return sampleTypeId;
    }
    
    public void setSampleTypeId(String sampleTypeId)
    {
        this.sampleTypeId = sampleTypeId;
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
        this.sampleCode = sampleCode;
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
    
    @Column(name = "SAMPLE_ERROR_STATUS")
    public Integer getSampleErrorStatus()
    {
        return sampleErrorStatus;
    }
    
    public void setSampleErrorStatus(Integer sampleErrorStatus)
    {
        this.sampleErrorStatus = sampleErrorStatus;
    }
}
