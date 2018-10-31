package com.todaysoft.lims.system.modules.bmm.model;

import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.ExcelColumnField;
import com.todaysoft.lims.system.modules.bpm.tecanalys.service.impl.ExcelColumnValueType;

public class DefaultInvoiceImportModel
{
    @ExcelColumnField(index = 0)
    private String orderCode;
    
    @ExcelColumnField(index = 1)
    private String contractCode;
    
    @ExcelColumnField(index = 2)
    private String orderType;
    
    @ExcelColumnField(index = 3)
    private String examineeName;
    
    @ExcelColumnField(index = 4)
    private String customerName;
    
    @ExcelColumnField(index = 5)
    private String company;
    
    @ExcelColumnField(index = 6)
    private String creatorName;
    
    @ExcelColumnField(index = 7)
    private String institution;
    
    @ExcelColumnField(index = 8, type = ExcelColumnValueType.FLOAT)
    private String invoiceAmount;
    
    @ExcelColumnField(index = 9)
    private String updateTime;
    
    @ExcelColumnField(index = 10)
    private String invoiceTitle;
    
    @ExcelColumnField(index = 11)
    private String invoiceType;
    
    @ExcelColumnField(index = 12)
    private String drawerName;//开票人
    
    @ExcelColumnField(index = 13)
    private String invoiceTime;//开票时间
    
    @ExcelColumnField(index = 14)
    private String invoicerNo;//发票号
    
    @ExcelColumnField(index = 15)
    private String receiverName;//领取人
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getContractCode()
    {
        return contractCode;
    }
    
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public String getInstitution()
    {
        return institution;
    }
    
    public void setInstitution(String institution)
    {
        this.institution = institution;
    }
    
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    
    public String getInvoiceType()
    {
        return invoiceType;
    }
    
    public void setInvoiceType(String invoiceType)
    {
        this.invoiceType = invoiceType;
    }
    
    public String getDrawerName()
    {
        return drawerName;
    }
    
    public void setDrawerName(String drawerName)
    {
        this.drawerName = drawerName;
    }
    
    public String getInvoicerNo()
    {
        return invoicerNo;
    }
    
    public void setInvoicerNo(String invoicerNo)
    {
        this.invoicerNo = invoicerNo;
    }
    
    public String getReceiverName()
    {
        return receiverName;
    }
    
    public void setReceiverName(String receiverName)
    {
        this.receiverName = receiverName;
    }
    
    public String getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(String updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public String getInvoiceTime()
    {
        return invoiceTime;
    }
    
    public void setInvoiceTime(String invoiceTime)
    {
        this.invoiceTime = invoiceTime;
    }
    
    public String getInvoiceAmount()
    {
        return invoiceAmount;
    }
    
    public void setInvoiceAmount(String invoiceAmount)
    {
        this.invoiceAmount = invoiceAmount;
    }
    
    public String getCompany()
    {
        return company;
    }
    
    public void setCompany(String company)
    {
        this.company = company;
    }
    
}
