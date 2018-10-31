package com.todaysoft.lims.schedule.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_RECEIVED_SAMPLE")
public class ReceivedSample implements Serializable
{
    private static final long serialVersionUID = -5215817938058234117L;
    
    private String id;
    
    private String sampleCode;
    
    private String sampleTypeName;
    
    private String orderId;
    
    private String lsLocation;// '长期存储位置编号',
    
    @Id
    @Column(name = "SAMPLE_ID")
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    @Column(name = "TYPE_NAME")
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
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

    @Column(name = "LS_LOCATION")
    public String getLsLocation()
    {
        return lsLocation;
    }

    public void setLsLocation(String lsLocation)
    {
        this.lsLocation = lsLocation;
    }
    
}
