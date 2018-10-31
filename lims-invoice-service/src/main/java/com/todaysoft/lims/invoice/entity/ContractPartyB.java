package com.todaysoft.lims.invoice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CONTRACT_PARTY_B")
public class ContractPartyB extends UuidKeyEntity
{
    
    /**
     * 业务库-合同乙方资料
     */
    private static final long serialVersionUID = 1L;
    
    private String companyName;//乙方名称
    
    private String contactName;//联系人姓名
    
    private String contactPhone;//联系人电话
    
    private String depositBank;//开户银行
    
    private String bankAccountName;//开户名称
    
    private String bankAccountNo;//开户账号
    
    private Contract contract;
    
    private String testInstitution;//字典项对应检测机构
    
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
    
    @Column(name = "DEPOSIT_BANK")
    public String getDepositBank()
    {
        return depositBank;
    }
    
    public void setDepositBank(String depositBank)
    {
        this.depositBank = depositBank;
    }
    
    @Column(name = "BANK_ACCOUNT_NAME")
    public String getBankAccountName()
    {
        return bankAccountName;
    }
    
    public void setBankAccountName(String bankAccountName)
    {
        this.bankAccountName = bankAccountName;
    }
    
    @Column(name = "BANK_ACCOUNT_NO")
    public String getBankAccountNo()
    {
        return bankAccountNo;
    }
    
    public void setBankAccountNo(String bankAccountNo)
    {
        this.bankAccountNo = bankAccountNo;
    }
    
    @Column(name = "TEST_INSTITUTION")
    public String getTestInstitution()
    {
        return testInstitution;
    }
    
    public void setTestInstitution(String testInstitution)
    {
        this.testInstitution = testInstitution;
    }
}
