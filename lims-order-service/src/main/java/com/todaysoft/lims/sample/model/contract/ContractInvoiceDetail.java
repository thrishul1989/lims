package com.todaysoft.lims.sample.model.contract;

import java.util.Date;

public class ContractInvoiceDetail
{
    private Integer invoiceAccount;
    
    private String invoiceCompany;
    
    private String invoicePerson;
    
    private Date invoiceTime;
    
    private String invoicerNo;
    
    private String receiverName;
    
    public Integer getInvoiceAccount()
    {
        return invoiceAccount;
    }
    
    public void setInvoiceAccount(Integer invoiceAccount)
    {
        this.invoiceAccount = invoiceAccount;
    }
    
    public String getInvoiceCompany()
    {
        return invoiceCompany;
    }
    
    public void setInvoiceCompany(String invoiceCompany)
    {
        this.invoiceCompany = invoiceCompany;
    }
    
    public String getInvoicePerson()
    {
        return invoicePerson;
    }
    
    public void setInvoicePerson(String invoicePerson)
    {
        this.invoicePerson = invoicePerson;
    }
    
    public Date getInvoiceTime()
    {
        return invoiceTime;
    }
    
    public void setInvoiceTime(Date invoiceTime)
    {
        this.invoiceTime = invoiceTime;
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
    
}
