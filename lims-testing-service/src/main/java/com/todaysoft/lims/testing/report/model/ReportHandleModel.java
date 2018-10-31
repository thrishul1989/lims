package com.todaysoft.lims.testing.report.model;

import java.util.List;

import com.todaysoft.lims.testing.base.entity.TestingMethod;

public class ReportHandleModel
{
    private String id;
    
    private String semantic;
    
    private String reportType;
    
    private String analyType;
    
    private String orderId;
    
    private List<TestingMethod> testingMethods;
    
    private boolean uploadPicture;
    
    private String orderCode;
    
    private String productCode;
    
    private String combineUrl;
    
    private int reportStatus;
    
    private String reportTemplateType;
    
    private String instruction;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    public String getReportType()
    {
        return reportType;
    }
    
    public void setReportType(String reportType)
    {
        this.reportType = reportType;
    }
    
    public String getAnalyType()
    {
        return analyType;
    }
    
    public void setAnalyType(String analyType)
    {
        this.analyType = analyType;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public List<TestingMethod> getTestingMethods()
    {
        return testingMethods;
    }
    
    public void setTestingMethods(List<TestingMethod> testingMethods)
    {
        this.testingMethods = testingMethods;
    }
    
    public boolean isUploadPicture()
    {
        return uploadPicture;
    }
    
    public void setUploadPicture(boolean uploadPicture)
    {
        this.uploadPicture = uploadPicture;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public String getCombineUrl()
    {
        return combineUrl;
    }
    
    public void setCombineUrl(String combineUrl)
    {
        this.combineUrl = combineUrl;
    }
    
    public int getReportStatus()
    {
        return reportStatus;
    }
    
    public void setReportStatus(int reportStatus)
    {
        this.reportStatus = reportStatus;
    }

    public String getReportTemplateType()
    {
        return reportTemplateType;
    }

    public void setReportTemplateType(String reportTemplateType)
    {
        this.reportTemplateType = reportTemplateType;
    }

    public String getInstruction()
    {
        return instruction;
    }

    public void setInstruction(String instruction)
    {
        this.instruction = instruction;
    }
    
}
