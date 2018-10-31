package com.todaysoft.lims.sample.entity.samplereceiving;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SAMPLE_STORAGING_DETAIL")
public class SampleStoragingDetail extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String sampleCode;//    '样本编号',
    
    private String location;//位置
    
    /* private String sampleType; //样本类型
     
     private String sampleName;//样本名称
    */
    private SampleStoraging sampleStoraging;
    
    private String lsLocation;
    
    private String tsLocation;
    
    /* @Column(name = "SAMPLE_TYPE")
     public String getSampleType()
     {
         return sampleType;
     }
     
     public void setSampleType(String sampleType)
     {
         this.sampleType = sampleType;
     }
     
     @Column(name = "SAMPLE_NAME")
     public String getSampleName()
     {
         return sampleName;
     }
     
     public void setSampleName(String sampleName)
     {
         this.sampleName = sampleName;
     }*/
    
    @Column(name = "SAMPLE_CODE")
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    @Column(name = "LOCATION")
    public String getLocation()
    {
        return location;
    }
    
    public void setLocation(String location)
    {
        this.location = location;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECORD_ID")
    @JsonIgnore
    public SampleStoraging getSampleStoraging()
    {
        return sampleStoraging;
    }
    
    public void setSampleStoraging(SampleStoraging sampleStoraging)
    {
        this.sampleStoraging = sampleStoraging;
    }
    
    @Transient
    public String getLsLocation()
    {
        return lsLocation;
    }
    
    public void setLsLocation(String lsLocation)
    {
        this.lsLocation = lsLocation;
    }
    
    @Transient
    public String getTsLocation()
    {
        return tsLocation;
    }
    
    public void setTsLocation(String tsLocation)
    {
        this.tsLocation = tsLocation;
    }
    
}
