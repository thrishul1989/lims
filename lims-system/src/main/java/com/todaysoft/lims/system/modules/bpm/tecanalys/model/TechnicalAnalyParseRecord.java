package com.todaysoft.lims.system.modules.bpm.tecanalys.model;

import org.springframework.util.StringUtils;

public class TechnicalAnalyParseRecord
{
    private String temporaryId;
    
    private boolean valid;
    
    private String message;
    
    private TechnicalAnalyRecord data;
    
    private TechnicalAnalySheetReceivedSample relatedSample;
    
    public boolean isVerify()
    {
        return null != data && !StringUtils.isEmpty(data.getVerify()) && "验证".equals(data.getVerify());
    }
    
    public String getTemporaryId()
    {
        return temporaryId;
    }
    
    public void setTemporaryId(String temporaryId)
    {
        this.temporaryId = temporaryId;
    }
    
    public boolean isValid()
    {
        return valid;
    }
    
    public void setValid(boolean valid)
    {
        this.valid = valid;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public TechnicalAnalyRecord getData()
    {
        return data;
    }
    
    public void setData(TechnicalAnalyRecord data)
    {
        this.data = data;
    }
    
    public TechnicalAnalySheetReceivedSample getRelatedSample()
    {
        return relatedSample;
    }
    
    public void setRelatedSample(TechnicalAnalySheetReceivedSample relatedSample)
    {
        this.relatedSample = relatedSample;
    }
}
