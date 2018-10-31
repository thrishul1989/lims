package com.todaysoft.lims.report.entity.finance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.report.entity.system.Contract;

@Entity
@Table(name = "LIMS_CONTRACT_PARTY_A")
public class ContractPartyA extends UuidKeyEntity
{
    
    /**
     * 业务库-合同甲方资料
     */
    private static final long serialVersionUID = 1L;
    
    private String companyName;//甲方名称
    
    private String contactName;//联系人姓名
    
    private String contactPhone;//联系人电话
    
    private String contactEmai;//联系人邮箱
    
    private String zipcode;//政编码
    
    private String address;//详细地址
    
    private String invoiceTitle;//发票抬头
    
    private Contract contract;
    
    @JoinColumn(name = "CONTRACT_ID")
    @OneToOne(targetEntity = Contract.class, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    public Contract getContract()
    {
        return contract;
    }
    
    public void setContract(Contract contract)
    {
        this.contract = contract;
    }
    
    @Column(name = "COMPANY_NAME")
    public String getCompanyName()
    {
        return companyName;
    }
    
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    
    @Column(name = "CONTACT_NAME")
    public String getContactName()
    {
        return contactName;
    }
    
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }
    
    @Column(name = "CONTACT_PHONE")
    public String getContactPhone()
    {
        return contactPhone;
    }
    
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    
    @Column(name = "CONTACT_EMAIL")
    public String getContactEmai()
    {
        return contactEmai;
    }
    
    public void setContactEmai(String contactEmai)
    {
        this.contactEmai = contactEmai;
    }
    
    @Column(name = "ZIPCODE")
    public String getZipcode()
    {
        return zipcode;
    }
    
    public void setZipcode(String zipcode)
    {
        this.zipcode = zipcode;
    }
    
    @Column(name = "ADDRESS")
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    @Column(name = "INVOICE_TITLE")
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    
}
