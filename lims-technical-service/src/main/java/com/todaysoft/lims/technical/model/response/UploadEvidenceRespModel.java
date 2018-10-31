package com.todaysoft.lims.technical.model.response;

public class UploadEvidenceRespModel
{
    
    private String statusCode;
    
    private String message;
    
    private UploadEvidenceRespModelData data;
    
    public String getStatusCode()
    {
        return statusCode;
    }
    
    public void setStatusCode(String statusCode)
    {
        this.statusCode = statusCode;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public UploadEvidenceRespModelData getData()
    {
        return data;
    }
    
    public void setData(UploadEvidenceRespModelData data)
    {
        this.data = data;
    }
    
}
