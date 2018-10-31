package com.todaysoft.lims.order.request;

import java.util.Date;

public class TestingReportModel
{
    private String id;
    
    private String orderId;
    
    private String productId;
    
    private Date createDate;
    
    private Date updateDate;
    
    private String reportCode;
    
    private String reportName;
    
    private String dataUrl;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getReportCode()
    {
        return reportCode;
    }
    
    public void setReportCode(String reportCode)
    {
        this.reportCode = reportCode;
    }
    
    public String getReportName()
    {
        return reportName;
    }
    
    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }
    
    public Date getCreateDate()
    {
        return createDate;
    }
    
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }
    
    public Date getUpdateDate()
    {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }
    
    public String getDataUrl()
    {
        return dataUrl;
    }
    
    public void setDataUrl(String dataUrl)
    {
        this.dataUrl = dataUrl;
    }
    
}
