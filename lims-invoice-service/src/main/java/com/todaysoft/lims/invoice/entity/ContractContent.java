package com.todaysoft.lims.invoice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_CONTRACT_CONTENT")
public class ContractContent implements Serializable
{
    private static final long serialVersionUID = -1845021707906199607L;
    
    public static final String INVOICE_TYPE_SINGLE = "0";
    
    private String contractId;
    
    private String invoiceMethod;
    
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
    
    @Column(name = "INVOICE_METHOD")
    public String getInvoiceMethod()
    {
        return invoiceMethod;
    }
    
    public void setInvoiceMethod(String invoiceMethod)
    {
        this.invoiceMethod = invoiceMethod;
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
