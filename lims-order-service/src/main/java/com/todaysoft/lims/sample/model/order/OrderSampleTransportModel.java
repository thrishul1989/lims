package com.todaysoft.lims.sample.model.order;

import java.util.Date;

public class OrderSampleTransportModel
{
    private String sampleId;
    
    private String sampleCode;
    
    private String transportNo;
    
    private String packDate;
    
    private String createName;
    
    private String createId;

    public String getSampleCode()
    {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }

    public String getTransportNo()
    {
        return transportNo;
    }

    public void setTransportNo(String transportNo)
    {
        this.transportNo = transportNo;
    }

    public String getPackDate()
    {
        return packDate;
    }

    public void setPackDate(String packDate)
    {
        this.packDate = packDate;
    }

    public String getCreateName()
    {
        return createName;
    }

    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getSampleId()
    {
        return sampleId;
    }

    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }

    public String getCreateId()
    {
        return createId;
    }

    public void setCreateId(String createId)
    {
        this.createId = createId;
    }
    
}
