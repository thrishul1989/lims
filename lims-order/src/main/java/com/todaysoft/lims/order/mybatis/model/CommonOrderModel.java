package com.todaysoft.lims.order.mybatis.model;

import java.math.BigDecimal;

public class CommonOrderModel extends BaseOrderModel
{
    
    private BigDecimal needPay;
    
    private String remark;
    
    private String delayId;
    
    private String planPayTime;
    
    private Integer delayCheckStatus;
    
    private String reduceId;
    
    private BigDecimal reduceApplyAmount;
    
    private BigDecimal reduceCheckAmount;
    
    private String contractPartB;
    
    private String contractsettlementMode;
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getPlanPayTime()
    {
        return planPayTime;
    }
    
    public void setPlanPayTime(String planPayTime)
    {
        this.planPayTime = planPayTime;
    }
    
    public Integer getDelayCheckStatus()
    {
        return delayCheckStatus;
    }
    
    public void setDelayCheckStatus(Integer delayCheckStatus)
    {
        this.delayCheckStatus = delayCheckStatus;
    }
    
    public String getDelayId()
    {
        return delayId;
    }
    
    public void setDelayId(String delayId)
    {
        this.delayId = delayId;
    }
    
    public String getReduceId()
    {
        return reduceId;
    }
    
    public void setReduceId(String reduceId)
    {
        this.reduceId = reduceId;
    }
    
    public BigDecimal getNeedPay()
    {
        return needPay;
    }
    
    public void setNeedPay(BigDecimal needPay)
    {
        this.needPay = needPay;
    }
    
    public BigDecimal getReduceApplyAmount()
    {
        return reduceApplyAmount;
    }
    
    public void setReduceApplyAmount(BigDecimal reduceApplyAmount)
    {
        this.reduceApplyAmount = reduceApplyAmount;
    }
    
    public BigDecimal getReduceCheckAmount()
    {
        return reduceCheckAmount;
    }
    
    public void setReduceCheckAmount(BigDecimal reduceCheckAmount)
    {
        this.reduceCheckAmount = reduceCheckAmount;
    }
    
    public String getContractPartB()
    {
        return contractPartB;
    }
    
    public void setContractPartB(String contractPartB)
    {
        this.contractPartB = contractPartB;
    }
    
    public String getContractsettlementMode()
    {
        return contractsettlementMode;
    }
    
    public void setContractsettlementMode(String contractsettlementMode)
    {
        this.contractsettlementMode = contractsettlementMode;
    }
    
}
