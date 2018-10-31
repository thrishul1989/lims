package com.todaysoft.lims.system.model.vo.contract;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class ContractPartyA extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 业务库-合同甲方资料
     */
    private String id;
    
    private String companyName;//甲方名称
    
    private String contactName;//联系人姓名
    
    private String contactPhone;//联系人电话
    
    private String contactEmai;//联系人邮箱
    
    private String zipcode;//政编码
    
    private String address;//详细地址
    
    private String invoiceTitle;//发票抬头
    
    private Contract contract;
    
    @Override
    public String getId()
    {
        return id;
    }
    
    @Override
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Contract getContract()
    {
        return contract;
    }
    
    public void setContract(Contract contract)
    {
        this.contract = contract;
    }
    
    public String getCompanyName()
    {
        return companyName;
    }
    
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    
    public String getContactName()
    {
        return contactName;
    }
    
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }
    
    public String getContactPhone()
    {
        return contactPhone;
    }
    
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    
    public String getContactEmai()
    {
        return contactEmai;
    }
    
    public void setContactEmai(String contactEmai)
    {
        this.contactEmai = contactEmai;
    }
    
    public String getZipcode()
    {
        return zipcode;
    }
    
    public void setZipcode(String zipcode)
    {
        this.zipcode = zipcode;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    
}
