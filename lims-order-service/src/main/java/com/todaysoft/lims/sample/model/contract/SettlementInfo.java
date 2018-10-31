package com.todaysoft.lims.sample.model.contract;

public class SettlementInfo
{
    private String settlementMode;//结算方式
    private String amount;//合同金额
    private String settlementRemark;//付款明细
    public String getSettlementMode()
    {
        return settlementMode;
    }
    public void setSettlementMode(String settlementMode)
    {
        this.settlementMode = settlementMode;
    }
    public String getAmount()
    {
        return amount;
    }
    public void setAmount(String amount)
    {
        this.amount = amount;
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
