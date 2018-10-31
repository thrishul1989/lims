package com.todaysoft.lims.sample.entity.contract;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CONTRACT_PRODUCT")
public class ContractProduct extends UuidKeyEntity
{
    
    /**
     * 业务库-合同产品设置
     */
    private static final long serialVersionUID = 1L;
    
    private String productId;//产品ID
    
    private String productName;//产品名称-冗余存储
    
    private String requirement;//服务要求
    
    private BigDecimal signAmount;//金额
    
    private Integer contractPrice;//合同单价
    
    private Integer signCount;//数量
    
    private Contract contract;
    
    private Integer productStatus; //关联产品状态
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACT_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Contract getContract()
    {
        return contract;
    }
    
    public void setContract(Contract contract)
    {
        this.contract = contract;
    }
    
    @Column(name = "PRODUCT_ID")
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    @Column(name = "PRODUCT_NAME")
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    @Column(name = "REQUIREMENT")
    public String getRequirement()
    {
        return requirement;
    }
    
    public void setRequirement(String requirement)
    {
        this.requirement = requirement;
    }
    
    @Column(name = "SIGN_AMOUNT")
    public BigDecimal getSignAmount()
    {
        return signAmount;
    }
    
    public void setSignAmount(BigDecimal signAmount)
    {
        this.signAmount = signAmount;
    }
    
    @Column(name = "CONTRACT_PRICE")
    public Integer getContractPrice()
    {
        return contractPrice;
    }
    
    public void setContractPrice(Integer contractPrice)
    {
        this.contractPrice = contractPrice;
    }
    
    @Column(name = "SIGN_COUNT")
    public Integer getSignCount()
    {
        return signCount;
    }
    
    public void setSignCount(Integer signCount)
    {
        this.signCount = signCount;
    }
    
    @Transient
    public Integer getProductStatus()
    {
        return productStatus;
    }
    
    public void setProductStatus(Integer productStatus)
    {
        this.productStatus = productStatus;
    }
    
}
