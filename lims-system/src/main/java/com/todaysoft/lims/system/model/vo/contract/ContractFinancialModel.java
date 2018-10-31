package com.todaysoft.lims.system.model.vo.contract;

public class ContractFinancialModel
{
    
    private String code;
    
    private String name;
    
    private String creatorName; //销售员 ---创建人
    
    private String marketingCenter;//营销中心
    
    private String companyName; ///甲方名称
    
    private String status;
    
    private Integer amount;
    
    private Integer ordersAmount;//已产生费用
    
    private Integer invoiceAmount; //开票金额
    
    private Integer incomingAmount; //回款
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public String getCompanyName()
    {
        return companyName;
    }
    
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public Integer getAmount()
    {
        return amount;
    }
    
    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
    
    public Integer getOrdersAmount()
    {
        return ordersAmount;
    }
    
    public void setOrdersAmount(Integer ordersAmount)
    {
        this.ordersAmount = ordersAmount;
    }
    
    public Integer getInvoiceAmount()
    {
        return invoiceAmount;
    }
    
    public void setInvoiceAmount(Integer invoiceAmount)
    {
        this.invoiceAmount = invoiceAmount;
    }
    
    public Integer getIncomingAmount()
    {
        return incomingAmount;
    }
    
    public void setIncomingAmount(Integer incomingAmount)
    {
        this.incomingAmount = incomingAmount;
    }
    
}
