package com.todaysoft.lims.testing.report.model;

import java.util.List;

public class ReportSendDataModel
{
    private String reportKey;
    
    private String reportCode;
    
    private String orderCode;
    
    private String reportTemplateType;
    
    private String reportType;//报告分类
    
    private String analyResult;//分析结果
    
    private String instruction;//结果说明
    
    private String marketingCenter;
    
    private String contractCode;
    
    private String contractName;
    
    private String customerName;
    
    private String customerCompanyName;
    
    private String salesmanName;
    
    private String orderRemark;
    
    private String orderSubmitTime;
    
    private String productCode;
    
    private String productName;
    
    private String productPrincipalName;
    
    private String technicalPrincipalName;
    
    private ExamineeModel  examinee;
    
    private List<SampleModel> samples;
    
    private List<TestingDataModel> testingDatas;
    
    private List<VerifyDataModel> verifyDatas;

    public String getReportKey()
    {
        return reportKey;
    }

    public void setReportKey(String reportKey)
    {
        this.reportKey = reportKey;
    }

    public String getReportCode()
    {
        return reportCode;
    }

    public void setReportCode(String reportCode)
    {
        this.reportCode = reportCode;
    }

    public String getOrderCode()
    {
        return orderCode;
    }

    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }

    public String getMarketingCenter()
    {
        return marketingCenter;
    }

    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }

    public String getContractCode()
    {
        return contractCode;
    }

    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }

    public String getContractName()
    {
        return contractName;
    }

    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getCustomerCompanyName()
    {
        return customerCompanyName;
    }

    public void setCustomerCompanyName(String customerCompanyName)
    {
        this.customerCompanyName = customerCompanyName;
    }

    public String getSalesmanName()
    {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName)
    {
        this.salesmanName = salesmanName;
    }

    public String getOrderRemark()
    {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark)
    {
        this.orderRemark = orderRemark;
    }

    public String getOrderSubmitTime()
    {
        return orderSubmitTime;
    }

    public void setOrderSubmitTime(String orderSubmitTime)
    {
        this.orderSubmitTime = orderSubmitTime;
    }

    public String getProductCode()
    {
        return productCode;
    }

    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductPrincipalName()
    {
        return productPrincipalName;
    }

    public void setProductPrincipalName(String productPrincipalName)
    {
        this.productPrincipalName = productPrincipalName;
    }

    public String getTechnicalPrincipalName()
    {
        return technicalPrincipalName;
    }

    public void setTechnicalPrincipalName(String technicalPrincipalName)
    {
        this.technicalPrincipalName = technicalPrincipalName;
    }

    public ExamineeModel getExaminee()
    {
        return examinee;
    }

    public void setExaminee(ExamineeModel examinee)
    {
        this.examinee = examinee;
    }

    public List<SampleModel> getSamples()
    {
        return samples;
    }

    public void setSamples(List<SampleModel> samples)
    {
        this.samples = samples;
    }

    public List<TestingDataModel> getTestingDatas()
    {
        return testingDatas;
    }

    public void setTestingDatas(List<TestingDataModel> testingDatas)
    {
        this.testingDatas = testingDatas;
    }

    public String getReportTemplateType()
    {
        return reportTemplateType;
    }

    public void setReportTemplateType(String reportTemplateType)
    {
        this.reportTemplateType = reportTemplateType;
    }

    public String getReportType()
    {
        return reportType;
    }

    public void setReportType(String reportType)
    {
        this.reportType = reportType;
    }

    public String getAnalyResult()
    {
        return analyResult;
    }

    public void setAnalyResult(String analyResult)
    {
        this.analyResult = analyResult;
    }

    public String getInstruction()
    {
        return instruction;
    }

    public void setInstruction(String instruction)
    {
        this.instruction = instruction;
    }

    public List<VerifyDataModel> getVerifyDatas()
    {
        return verifyDatas;
    }

    public void setVerifyDatas(List<VerifyDataModel> verifyDatas)
    {
        this.verifyDatas = verifyDatas;
    }
    
}
