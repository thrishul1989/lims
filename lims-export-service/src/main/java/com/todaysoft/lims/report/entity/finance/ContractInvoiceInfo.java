package com.todaysoft.lims.report.entity.finance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CONTRACT_INVOICE_INFO")
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
    
    private Date invoiceTime;
    
    private String invoicerNo;
    
    private String receiverName;
    
    private String creatorId;//创建人ID
    
    private String creatorName;//创建人姓名
    
    private Date createTime;//创建时间
    
    private String remark;
    
    private String invoiceTitle;
    
    private String invoicePersonName;
    
    @Transient
    public String getInvoicePersonName()
    {
        return invoicePersonName;
    }

    public void setInvoicePersonName(String invoicePersonName)
    {
        this.invoicePersonName = invoicePersonName;
    }

    @Transient
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    
    @Column(name = "INVOICE_PERSON")
    public String getInvoicePerson()
    {
        return invoicePerson;
    }
    
    public void setInvoicePerson(String invoicePerson)
    {
        this.invoicePerson = invoicePerson;
    }
    
    @Column(name = "INVOICEAPPLY_ID")
    public String getInvoiceApplyId()
    {
        return invoiceApplyId;
    }
    
    public void setInvoiceApplyId(String invoiceApplyId)
    {
        this.invoiceApplyId = invoiceApplyId;
    }
    
    @Column(name = "CONTRACT_ID")
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    @Column(name = "CREATOR_ID")
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    @Column(name = "CREATOR_NAME")
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    @Column(name = "CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "INVOICE_ACCOUNT")
    public Integer getInvoiceAccount()
    {
        return invoiceAccount;
    }
    
    public void setInvoiceAccount(Integer invoiceAccount)
    {
        this.invoiceAccount = invoiceAccount;
    }
    
    @Column(name = "INVOICE_COMPANY")
    public String getInvoiceCompany()
    {
        return invoiceCompany;
    }
    
    public void setInvoiceCompany(String invoiceCompany)
    {
        this.invoiceCompany = invoiceCompany;
    }
    
    @Column(name = "INVOICE_TIME")
    public Date getInvoiceTime()
    {
        return invoiceTime;
    }
    
    public void setInvoiceTime(Date invoiceTime)
    {
        this.invoiceTime = invoiceTime;
    }
    
    @Column(name = "INVOICER_NO")
    public String getInvoicerNo()
    {
        return invoicerNo;
    }
    
    public void setInvoicerNo(String invoicerNo)
    {
        this.invoicerNo = invoicerNo;
    }
    
    @Column(name = "RECEIVER_NAME")
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
