package com.todaysoft.lims.system.model.vo.contract;

import java.util.Date;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class ContractInvoiceInfo extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String invoiceApplyId;
    
    private String contractId;
    
    private Integer invoiceAccount;
    
    private String invoiceCompany;
    
    private String invoicePerson;
    
    private String invoicePersonName;
    
    private Date invoiceTime;
    
    private String invoicerNo;
    
    private String receiverName;
    
    private String creatorId;//创建人ID
    
    private String creatorName;//创建人姓名
    
    private Date createTime;//创建时间
    
    private String remark;
    
    private String invoiceTitle;
    
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    
    public String getInvoicePersonName()
    {
        return invoicePersonName;
    }
    
    public void setInvoicePersonName(String invoicePersonName)
    {
        this.invoicePersonName = invoicePersonName;
    }
    
    public String getInvoicePerson()
    {
        return invoicePerson;
    }
    
    public void setInvoicePerson(String invoicePerson)
    {
        this.invoicePerson = invoicePerson;
    }
    
    public String getInvoiceApplyId()
    {
        return invoiceApplyId;
    }
    
    public void setInvoiceApplyId(String invoiceApplyId)
    {
        this.invoiceApplyId = invoiceApplyId;
    }
    
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
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
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
}
