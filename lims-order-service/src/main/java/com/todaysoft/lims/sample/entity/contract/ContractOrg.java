package com.todaysoft.lims.sample.entity.contract;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CONTRACT_ORG")
public class ContractOrg extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String companyName;//乙方名称
    
    private String depositBank;//开户银行
    
    private String bankAccountName;//开户名称
    
    private String bankAccountNo;//开户账号
    
    private String testInstitution;//字典项对应检测机构
    
    @Column(name = "COMPANY_NAME")
    public String getCompanyName()
    {
        return companyName;
    }
    
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
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
