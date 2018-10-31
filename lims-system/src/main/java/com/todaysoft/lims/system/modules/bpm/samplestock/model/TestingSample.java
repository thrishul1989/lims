package com.todaysoft.lims.system.modules.bpm.samplestock.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;

import com.todaysoft.lims.system.model.vo.samplereceiving.ReceivedSample;
import com.todaysoft.lims.system.modules.bmm.model.DNAAttributes;

@Builder
@AllArgsConstructor
public class TestingSample
{
    
    private String id;
    
    private String sampleCode;
    
    private String sampleTypeId;
    
    private String sampleTypeName;
    
    private TestingSample parentSample;
    
    private ReceivedSample receivedSample;
    
    private String attributes;
    
    private DNAAttributes dnaAttributes;
    
    private String ownerName;
    
    private String locationCode;
    
    private BigDecimal volumn;//体积
    
    private String remark;//备注
    
    public TestingSample()
    {
        
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
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
    
    public TestingSample getParentSample()
    {
        return parentSample;
    }
    
    public void setParentSample(TestingSample parentSample)
    {
        this.parentSample = parentSample;
    }
    
    public ReceivedSample getReceivedSample()
    {
        return receivedSample;
    }
    
    public void setReceivedSample(ReceivedSample receivedSample)
    {
        this.receivedSample = receivedSample;
    }
    
    public String getAttributes()
    {
        return attributes;
    }
    
    public void setAttributes(String attributes)
    {
        this.attributes = attributes;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((parentSample == null) ? 0 : parentSample.hashCode());
        result = prime * result + ((receivedSample == null) ? 0 : receivedSample.hashCode());
        result = prime * result + ((sampleCode == null) ? 0 : sampleCode.hashCode());
        result = prime * result + ((sampleTypeId == null) ? 0 : sampleTypeId.hashCode());
        result = prime * result + ((sampleTypeName == null) ? 0 : sampleTypeName.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TestingSample other = (TestingSample)obj;
        if (attributes == null)
        {
            if (other.attributes != null)
                return false;
        }
        else if (!attributes.equals(other.attributes))
            return false;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (parentSample == null)
        {
            if (other.parentSample != null)
                return false;
        }
        else if (!parentSample.equals(other.parentSample))
            return false;
        if (receivedSample == null)
        {
            if (other.receivedSample != null)
                return false;
        }
        else if (!receivedSample.equals(other.receivedSample))
            return false;
        if (sampleCode == null)
        {
            if (other.sampleCode != null)
                return false;
        }
        else if (!sampleCode.equals(other.sampleCode))
            return false;
        if (sampleTypeId == null)
        {
            if (other.sampleTypeId != null)
                return false;
        }
        else if (!sampleTypeId.equals(other.sampleTypeId))
            return false;
        if (sampleTypeName == null)
        {
            if (other.sampleTypeName != null)
                return false;
        }
        else if (!sampleTypeName.equals(other.sampleTypeName))
            return false;
        return true;
    }
    
    public DNAAttributes getDnaAttributes()
    {
        return dnaAttributes;
    }
    
    public void setDnaAttributes(DNAAttributes dnaAttributes)
    {
        this.dnaAttributes = dnaAttributes;
    }
    
    public String getOwnerName()
    {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
    
    public String getLocationCode()
    {
        return locationCode;
    }
    
    public void setLocationCode(String locationCode)
    {
        this.locationCode = locationCode;
    }
    
    public BigDecimal getVolumn()
    {
        return volumn;
    }
    
    public void setVolumn(BigDecimal volumn)
    {
        this.volumn = volumn;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}
