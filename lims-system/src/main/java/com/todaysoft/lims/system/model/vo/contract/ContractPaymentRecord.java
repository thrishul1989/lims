package com.todaysoft.lims.system.model.vo.contract;

import java.util.Date;

public class ContractPaymentRecord
{
    /**
     * 
     */
    
    private String contractId;
    
    private String checkId;
    
    private String checkName;
    
    private Date checkTime;
    
    private Integer payType;
    
    private String paymenter;
    
    private Date paymentTime;
    
    private Double checkAmount;
    
    private String remark;
    
    private String paymentInstrumentImg;
    
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    public String getCheckId()
    {
        return checkId;
    }
    
    public void setCheckId(String checkId)
    {
        this.checkId = checkId;
    }
    
    public String getCheckName()
    {
        return checkName;
    }
    
    public void setCheckName(String checkName)
    {
        this.checkName = checkName;
    }
    
    public Date getCheckTime()
    {
        return checkTime;
    }
    
    public void setCheckTime(Date checkTime)
    {
        this.checkTime = checkTime;
    }
    
    public Integer getPayType()
    {
        return payType;
    }
    
    public void setPayType(Integer payType)
    {
        this.payType = payType;
    }
    
    public String getPaymenter()
    {
        return paymenter;
    }
    
    public void setPaymenter(String paymenter)
    {
        this.paymenter = paymenter;
    }
    
    public Date getPaymentTime()
    {
        return paymentTime;
    }
    
    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
    }
    
    public Double getCheckAmount()
    {
        return checkAmount;
    }
    
    public void setCheckAmount(Double checkAmount)
    {
        this.checkAmount = checkAmount;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getPaymentInstrumentImg()
    {
        return paymentInstrumentImg;
    }
    
    public void setPaymentInstrumentImg(String paymentInstrumentImg)
    {
        this.paymentInstrumentImg = paymentInstrumentImg;
    }
    
}
