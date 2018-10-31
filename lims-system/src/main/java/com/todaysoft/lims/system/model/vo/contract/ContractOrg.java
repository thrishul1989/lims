package com.todaysoft.lims.system.model.vo.contract;

public class ContractOrg
{
    
    private String id;
    
    private String companyName;//乙方名称
    
    private String depositBank;//开户银行
    
    private String bankAccountName;//开户名称
    
    private String bankAccountNo;//开户账号
    
    private String testInstitution;//字典项对应检测机构
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCompanyName()
    {
        return companyName;
    }
    
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
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
