package com.todaysoft.lims.system.model.vo.reconciliation;

import java.math.BigDecimal;
import java.util.Date;

public class AccountCheckMistake
{
    private String id;
    
    private String accountCheckTaskId;
    
    private String billDate;
    
    private String bankType;
    
    private String orderNo;
    
    private String tradeTime;
    
    private String trxNo;
    
    private BigDecimal orderAmount;
    
    private BigDecimal fee;
    
    private String bankTradeTime;
    
    private String bankOrderNo;
    
    private String bankTrxNo;
    
    private BigDecimal bankAmount;
    
    private BigDecimal bankFee;
    
    private String errType;
    
    private String handleStatus;
    
    private String handleValue;
    
    private String handleRemark;
    
    private String operatorName;
    
    private Date createTime;
    
    private Date paymentFullTime;
    
    private Date bankPaymentFullTime;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id == null ? null : id.trim();
    }
    
    public String getAccountCheckTaskId()
    {
        return accountCheckTaskId;
    }
    
    public void setAccountCheckTaskId(String accountCheckTaskId)
    {
        this.accountCheckTaskId = accountCheckTaskId == null ? null : accountCheckTaskId.trim();
    }
    
    public String getBillDate()
    {
        return billDate;
    }
    
    public void setBillDate(String billDate)
    {
        this.billDate = billDate == null ? null : billDate.trim();
    }
    
    public String getBankType()
    {
        return bankType;
    }
    
    public void setBankType(String bankType)
    {
        this.bankType = bankType == null ? null : bankType.trim();
    }
    
    public String getOrderNo()
    {
        return orderNo;
    }
    
    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }
    
    public String getTradeTime()
    {
        return tradeTime;
    }
    
    public void setTradeTime(String tradeTime)
    {
        this.tradeTime = tradeTime == null ? null : tradeTime.trim();
    }
    
    public String getTrxNo()
    {
        return trxNo;
    }
    
    public void setTrxNo(String trxNo)
    {
        this.trxNo = trxNo == null ? null : trxNo.trim();
    }
    
    public BigDecimal getOrderAmount()
    {
        return orderAmount;
    }
    
    public void setOrderAmount(BigDecimal orderAmount)
    {
        this.orderAmount = orderAmount;
    }
    
    public BigDecimal getFee()
    {
        return fee;
    }
    
    public void setFee(BigDecimal fee)
    {
        this.fee = fee;
    }
    
    public String getBankTradeTime()
    {
        return bankTradeTime;
    }
    
    public void setBankTradeTime(String bankTradeTime)
    {
        this.bankTradeTime = bankTradeTime == null ? null : bankTradeTime.trim();
    }
    
    public String getBankOrderNo()
    {
        return bankOrderNo;
    }
    
    public void setBankOrderNo(String bankOrderNo)
    {
        this.bankOrderNo = bankOrderNo == null ? null : bankOrderNo.trim();
    }
    
    public String getBankTrxNo()
    {
        return bankTrxNo;
    }
    
    public void setBankTrxNo(String bankTrxNo)
    {
        this.bankTrxNo = bankTrxNo == null ? null : bankTrxNo.trim();
    }
    
    public BigDecimal getBankAmount()
    {
        return bankAmount;
    }
    
    public void setBankAmount(BigDecimal bankAmount)
    {
        this.bankAmount = bankAmount;
    }
    
    public BigDecimal getBankFee()
    {
        return bankFee;
    }
    
    public void setBankFee(BigDecimal bankFee)
    {
        this.bankFee = bankFee;
    }
    
    public String getErrType()
    {
        return errType;
    }
    
    public void setErrType(String errType)
    {
        this.errType = errType == null ? null : errType.trim();
    }
    
    public String getHandleStatus()
    {
        return handleStatus;
    }
    
    public void setHandleStatus(String handleStatus)
    {
        this.handleStatus = handleStatus == null ? null : handleStatus.trim();
    }
    
    public String getHandleValue()
    {
        return handleValue;
    }
    
    public void setHandleValue(String handleValue)
    {
        this.handleValue = handleValue == null ? null : handleValue.trim();
    }
    
    public String getHandleRemark()
    {
        return handleRemark;
    }
    
    public void setHandleRemark(String handleRemark)
    {
        this.handleRemark = handleRemark == null ? null : handleRemark.trim();
    }
    
    public String getOperatorName()
    {
        return operatorName;
    }
    
    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Date getPaymentFullTime()
    {
        return paymentFullTime;
    }
    
    public void setPaymentFullTime(Date paymentFullTime)
    {
        this.paymentFullTime = paymentFullTime;
    }
    
    public Date getBankPaymentFullTime()
    {
        return bankPaymentFullTime;
    }
    
    public void setBankPaymentFullTime(Date bankPaymentFullTime)
    {
        this.bankPaymentFullTime = bankPaymentFullTime;
    }
}