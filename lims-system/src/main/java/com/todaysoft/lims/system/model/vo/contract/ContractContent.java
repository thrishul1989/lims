package com.todaysoft.lims.system.model.vo.contract;

import java.util.List;

public class ContractContent
{
    
    /**
     * 业务库-合同内容
     */
    
    private String testingPeriod;//实验周期
    
    private String deliveryResult;//交付结果
    
    private String deliveryMode;//交付形式
    
    private String settlementMode;//结算方式
    
    private String settlementRemark;//结算备注
    
    private Contract contract;
    
    private String invoiceMethod;
    
    private List<String> deliveryModes;
    
    public String getInvoiceMethod()
    {
        return invoiceMethod;
    }
    
    public void setInvoiceMethod(String invoiceMethod)
    {
        this.invoiceMethod = invoiceMethod;
    }
    
    public List<String> getDeliveryModes()
    {
        return deliveryModes;
    }
    
    public void setDeliveryModes(List<String> deliveryModes)
    {
        this.deliveryModes = deliveryModes;
    }
    
    public Contract getContract()
    {
        return contract;
    }
    
    public void setContract(Contract contract)
    {
        this.contract = contract;
    }
    
    public String getTestingPeriod()
    {
        return testingPeriod;
    }
    
    public void setTestingPeriod(String testingPeriod)
    {
        this.testingPeriod = testingPeriod;
    }
    
    public String getDeliveryResult()
    {
        return deliveryResult;
    }
    
    public void setDeliveryResult(String deliveryResult)
    {
        this.deliveryResult = deliveryResult;
    }
    
    public String getDeliveryMode()
    {
        return deliveryMode;
    }
    
    public void setDeliveryMode(String deliveryMode)
    {
        this.deliveryMode = deliveryMode;
    }
    
    public String getSettlementMode()
    {
        return settlementMode;
    }
    
    public void setSettlementMode(String settlementMode)
    {
        this.settlementMode = settlementMode;
    }
    
    public String getSettlementRemark()
    {
        return settlementRemark;
    }
    
    public void setSettlementRemark(String settlementRemark)
    {
        this.settlementRemark = settlementRemark;
    }
    
}
