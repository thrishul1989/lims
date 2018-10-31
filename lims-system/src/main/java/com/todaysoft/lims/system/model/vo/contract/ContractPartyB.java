package com.todaysoft.lims.system.model.vo.contract;

public class ContractPartyB
{
    
    /**
     * 业务库-合同乙方资料
     */
    private String id;
    
    private String companyName;//乙方名称
    
    private String contactName;//联系人姓名
    
    private String contactPhone;//联系人电话
    
    private String depositBank;//开户银行
    
    private String bankAccountName;//开户名称
    
    private String bankAccountNo;//开户账号
    
    private Contract contract;
    
    private String testInstitution;//字典项对应检测机构
    
    public String getId()
    {
        return id;
    }
    
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
    
    public String getDepositBank()
    {
        return depositBank;
    }
    
    public void setDepositBank(String depositBank)
    {
        this.depositBank = depositBank;
    }
    
    public String getBankAccountName()
    {
        return bankAccountName;
    }
    
    public void setBankAccountName(String bankAccountName)
    {
        this.bankAccountName = bankAccountName;
    }
    
    public String getBankAccountNo()
    {
        return bankAccountNo;
    }
    
    public void setBankAccountNo(String bankAccountNo)
    {
        this.bankAccountNo = bankAccountNo;
    }
    
    public String getTestInstitution()
    {
        return testInstitution;
    }
    
    public void setTestInstitution(String testInstitution)
    {
        this.testInstitution = testInstitution;
    }
}
