package com.todaysoft.lims.sample.entity.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "APP_SAMPLE_TRANSPORT_RELATION")
public class AppSampleTransportRelation extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String userId;
    
    private String packageId;
    
    private String sampleId;
    
    private String sampleType;
    
    private String orderNo;
    
    @Column(name = "USER_ID")
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    @Column(name = "PACKAGE_ID")
    public String getPackageId()
    {
        return packageId;
    }
    
    public void setPackageId(String packageId)
    {
        this.packageId = packageId;
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
    
    @Column(name = "SAMPLE_TYPE")
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    @Column(name = "ORDER_NO")
    public String getOrderNo()
    {
        return orderNo;
    }
    
    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }
    
}
