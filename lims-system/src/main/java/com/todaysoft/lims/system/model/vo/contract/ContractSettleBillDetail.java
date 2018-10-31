package com.todaysoft.lims.system.model.vo.contract;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class ContractSettleBillDetail extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private ContractSettleBill settleBill;
    
    private String orderId;
    
    public ContractSettleBill getSettleBill()
    {
        return settleBill;
    }
    
    public void setSettleBill(ContractSettleBill settleBill)
    {
        this.settleBill = settleBill;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
}
