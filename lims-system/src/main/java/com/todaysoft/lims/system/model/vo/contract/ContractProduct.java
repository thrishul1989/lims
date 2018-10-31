package com.todaysoft.lims.system.model.vo.contract;

import java.math.BigDecimal;

import com.todaysoft.lims.system.model.vo.Product;

public class ContractProduct
{
    
    /**
     * 业务库-合同产品设置
     */
    private String id;
    
    private String productId;//产品ID
    
    private String productName;//产品名称-冗余存储
    
    private String requirement;//服务要求
    
    private BigDecimal signAmount = new BigDecimal(0);//金额
    
    private Integer contractPrice;//合同单价
    
    private Integer signCount;//数量
    
    private Contract contract;
    
    private String realContractPrice;
    
    private String realSignAmount;
    
    private Integer productStatus; //关联产品状态
    
    public String getRealSignAmount()
    {
        if (null != signAmount)
        {
            // return BigDecimal.valueOf(Double.valueOf(signAmount)).divide(new BigDecimal(100)).toString();
            
            return signAmount.divide(new BigDecimal(100)).toString();
        }
        else
        {
            return "0";
        }
    }
    
    public void setRealSignAmount(String realSignAmount)
    {
        this.realSignAmount = realSignAmount;
    }
    
    public String getRealContractPrice()
    {
        if (null != contractPrice)
        {
            //return BigDecimal.valueOf(Double.valueOf(contractPrice)).divide(new BigDecimal(100)).toString();
            double allamount = contractPrice / 100.0;
            return String.valueOf(allamount);
        }
        else
        {
            return "0";
        }
    }
    
    public void setRealContractPrice(String realContractPrice)
    {
        this.realContractPrice = realContractPrice;
    }
    
    private Product p;
    
    public Product getP()
    {
        return p;
    }
    
    public void setP(Product p)
    {
        this.p = p;
    }
    
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
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getRequirement()
    {
        return requirement;
    }
    
    public void setRequirement(String requirement)
    {
        this.requirement = requirement;
    }
    
    public BigDecimal getSignAmount()
    {
        return signAmount;
    }
    
    public void setSignAmount(BigDecimal signAmount)
    {
        this.signAmount = signAmount;
    }
    
    public Integer getContractPrice()
    {
        return contractPrice;
    }
    
    public void setContractPrice(Integer contractPrice)
    {
        this.contractPrice = contractPrice;
    }
    
    public Integer getSignCount()
    {
        return signCount;
    }
    
    public void setSignCount(Integer signCount)
    {
        this.signCount = signCount;
    }
    
    public Integer getProductStatus()
    {
        return productStatus;
    }
    
    public void setProductStatus(Integer productStatus)
    {
        this.productStatus = productStatus;
    }
    
}
