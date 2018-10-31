package com.todaysoft.lims.system.model.vo.order;

import com.todaysoft.lims.persist.UuidKeyEntity;

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
    
    private OrderSampleDetails sampleDetail;
    
    public OrderSampleDetails getSampleDetail()
    {
        return sampleDetail;
    }
    
    public void setSampleDetail(OrderSampleDetails sampleDetail)
    {
        this.sampleDetail = sampleDetail;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getPackageId()
    {
        return packageId;
    }
    
    public void setPackageId(String packageId)
    {
        this.packageId = packageId;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    public String getOrderNo()
    {
        return orderNo;
    }
    
    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }
    
}
