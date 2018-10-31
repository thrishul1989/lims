package com.todaysoft.lims.report.entity.finance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_CONTRACT_CONTENT")
public class ContractContent implements Serializable
{
    
    /**
     * 业务库-合同内容
     */
    private static final long serialVersionUID = 1L;
    
    private String testingPeriod;//实验周期
    
    private String deliveryResult;//交付结果
    
    private String deliveryMode;//交付形式
    
    private String settlementMode;//结算方式
    
    private String settlementRemark;//结算备注
    
    private String contractId;
    
    private String invoiceMethod;
    
    @Column(name = "INVOICE_METHOD")
    public String getInvoiceMethod()
    {
        return invoiceMethod;
    }
    
    public void setInvoiceMethod(String invoiceMethod)
    {
        this.invoiceMethod = invoiceMethod;
    }
    
    @Column(name = "TESTING_PERIOD")
    public String getTestingPeriod()
    {
        return testingPeriod;
    }
    
    public void setTestingPeriod(String testingPeriod)
    {
        this.testingPeriod = testingPeriod;
    }
    
    @Column(name = "DELIVERY_RESULT")
    public String getDeliveryResult()
    {
        return deliveryResult;
    }
    
    public void setDeliveryResult(String deliveryResult)
    {
        this.deliveryResult = deliveryResult;
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
    
    @Column(name = "SETTLEMENT_REMARK")
    public String getSettlementRemark()
    {
        return settlementRemark;
    }
    
    public void setSettlementRemark(String settlementRemark)
    {
        this.settlementRemark = settlementRemark;
    }
    
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
    
}
