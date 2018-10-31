package com.todaysoft.lims.sample.entity.samplereceiving;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.sample.entity.sampleBack.DNAAttributes;

@Entity
@Table(name = "LIMS_TESTING_SAMPLE")
public class TestingSample extends UuidKeyEntity implements Serializable
{
    
    private static final long serialVersionUID = 978602826031627374L;
    
    private String sampleCode;
    
    private String sampleTypeId;
    
    private String sampleTypeName;
    
    private String originalSampleId;
    
    private String parentSampleId;
    
    private String attributes;
    
    private DNAAttributes dnaAttributes;
    
    private String ownerName;
    
    private String locationCode;
    
    private BigDecimal volumn;//体积
    
    private String remark;//备注
    
    private String concn;
    
    @Column(name = "SAMPLE_CODE")
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
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
    
    @Column(name = "ORIGINAL_SAMPLE_ID")
    public String getOriginalSampleId()
    {
        return originalSampleId;
    }
    
    public void setOriginalSampleId(String originalSampleId)
    {
        this.originalSampleId = originalSampleId;
    }
    
    @Column(name = "PARENT_SAMPLE_ID")
    public String getParentSampleId()
    {
        return parentSampleId;
    }
    
    public void setParentSampleId(String parentSampleId)
    {
        this.parentSampleId = parentSampleId;
    }
    
    @Column(name = "ATTRIBUTES")
    public String getAttributes()
    {
        return attributes;
    }
    
    public void setAttributes(String attributes)
    {
        this.attributes = attributes;
    }
    
    @Transient
    public DNAAttributes getDnaAttributes()
    {
        return dnaAttributes;
    }
    
    public void setDnaAttributes(DNAAttributes dnaAttributes)
    {
        this.dnaAttributes = dnaAttributes;
    }
    
    @Transient
    public String getOwnerName()
    {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
    
    @Transient
    public String getLocationCode()
    {
        return locationCode;
    }
    
    public void setLocationCode(String locationCode)
    {
        this.locationCode = locationCode;
    }
    
    @Transient
    public BigDecimal getVolumn()
    {
        return volumn;
    }
    
    public void setVolumn(BigDecimal volumn)
    {
        this.volumn = volumn;
    }
    
    @Transient
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @Transient
    public String getConcn()
    {
        return concn;
    }
    
    public void setConcn(String concn)
    {
        this.concn = concn;
    }
    
}
