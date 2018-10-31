package com.todaysoft.lims.schedule.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_CONTRACT_CONTENT")
public class ContractContent implements Serializable
{
    private static final long serialVersionUID = 2684686908650613316L;
    
    public static final String SETTLEMENT_SINGLE = "4";
    
    private String contractId;
    
    private String deliveryMode;
    
    private String settlementMode;
    
    @Id
    @Column(name = "CONTRACT_ID")
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    @Column(name = "DELIVERY_MODE")
    public String getDeliveryMode()
    {
        return deliveryMode;
    }
    
    public void setDeliveryMode(String deliveryMode)
    {
        this.deliveryMode = deliveryMode;
    }
    
    @Column(name = "SETTLEMENT_MODE")
    public String getSettlementMode()
    {
        return settlementMode;
    }
    
    public void setSettlementMode(String settlementMode)
    {
        this.settlementMode = settlementMode;
    }
}
