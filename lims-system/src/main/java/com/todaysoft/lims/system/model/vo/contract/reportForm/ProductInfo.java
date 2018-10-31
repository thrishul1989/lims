package com.todaysoft.lims.system.model.vo.contract.reportForm;

public class ProductInfo
{
    private String productName;//产品名称
    private String price;//单价
    private String count;//数据
    private String amount;//价格
    private String requirement;//服务要求
    public String getProductName()
    {
        return productName;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    public String getPrice()
    {
        return price;
    }
    public void setPrice(String price)
    {
        this.price = price;
    }
    public String getCount()
    {
        return count;
    }
    public void setCount(String count)
    {
        this.count = count;
    }
    public String getAmount()
    {
        return amount;
    }
    public void setAmount(String amount)
    {
        this.amount = amount;
    }
    public String getRequirement()
    {
        return requirement;
    }
    public void setRequirement(String requirement)
    {
        this.requirement = requirement;
    }
    
}
