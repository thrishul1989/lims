package com.todaysoft.lims.maintain.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.maintain.entity.Contract;
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
    
    private Integer contractPrice;//合同单价
    
    private Contract contract;
    
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
    
    @Column(name = "CONTRACT_PRICE")
    public Integer getContractPrice()
    {
        return contractPrice;
    }
    
    public void setContractPrice(Integer contractPrice)
    {
        this.contractPrice = contractPrice;
    }
    
}
