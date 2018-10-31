package com.todaysoft.lims.system.model.vo.reconciliation;

import java.math.BigDecimal;

public class OrderAccountStateRecord
{
    private String id;
    
    private String seqNo;
    
    private String merNum;
    
    private String merName;
    
    private String termId;
    
    private String settlementDate;
    
    private String transactionDate;
    
    private String tradingTime;
    
    private String orderId;
    
    private String tradingType;
    
    private BigDecimal tradingAmount;
    
    private BigDecimal serviceCharge; //手续费
    
    private BigDecimal enterAmount;
    
    private String cardNumber;
    
    private String cardDomain;
    
    private String cardSpecies;
    
    private String referNo;
    
    private String oReferNo;
    
    private String oTradingTime;
    
    private String payType;
    
    private String bankCode;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSeqNo()
    {
        return seqNo;
    }
    
    public void setSeqNo(String seqNo)
    {
        this.seqNo = seqNo;
    }
    
    public String getMerNum()
    {
        return merNum;
    }
    
    public void setMerNum(String merNum)
    {
        this.merNum = merNum;
    }
    
    public String getMerName()
    {
        return merName;
    }
    
    public void setMerName(String merName)
    {
        this.merName = merName;
    }
    
    public String getTermId()
    {
        return termId;
    }
    
    public void setTermId(String termId)
    {
        this.termId = termId;
    }
    
    public String getSettlementDate()
    {
        return settlementDate;
    }
    
    public void setSettlementDate(String settlementDate)
    {
        this.settlementDate = settlementDate;
    }
    
    public String getTransactionDate()
    {
        return transactionDate;
    }
    
    public void setTransactionDate(String transactionDate)
    {
        this.transactionDate = transactionDate;
    }
    
    public String getTradingTime()
    {
        return tradingTime;
    }
    
    public void setTradingTime(String tradingTime)
    {
        this.tradingTime = tradingTime;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getTradingType()
    {
        return tradingType;
    }
    
    public void setTradingType(String tradingType)
    {
        this.tradingType = tradingType;
    }
    
    public String getCardNumber()
    {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }
    
    public String getCardDomain()
    {
        return cardDomain;
    }
    
    public void setCardDomain(String cardDomain)
    {
        this.cardDomain = cardDomain;
    }
    
    public String getCardSpecies()
    {
        return cardSpecies;
    }
    
    public void setCardSpecies(String cardSpecies)
    {
        this.cardSpecies = cardSpecies;
    }
    
    public String getReferNo()
    {
        return referNo;
    }
    
    public void setReferNo(String referNo)
    {
        this.referNo = referNo;
    }
    
    public String getoReferNo()
    {
        return oReferNo;
    }
    
    public void setoReferNo(String oReferNo)
    {
        this.oReferNo = oReferNo;
    }
    
    public String getoTradingTime()
    {
        return oTradingTime;
    }
    
    public void setoTradingTime(String oTradingTime)
    {
        this.oTradingTime = oTradingTime;
    }
    
    public String getPayType()
    {
        return payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    public String getBankCode()
    {
        return bankCode;
    }
    
    public void setBankCode(String bankCode)
    {
        this.bankCode = bankCode;
    }
    
    public BigDecimal getTradingAmount()
    {
        return tradingAmount;
    }
    
    public void setTradingAmount(BigDecimal tradingAmount)
    {
        this.tradingAmount = tradingAmount;
    }
    
    public BigDecimal getServiceCharge()
    {
        return serviceCharge;
    }
    
    public void setServiceCharge(BigDecimal serviceCharge)
    {
        this.serviceCharge = serviceCharge;
    }
    
    public BigDecimal getEnterAmount()
    {
        return enterAmount;
    }
    
    public void setEnterAmount(BigDecimal enterAmount)
    {
        this.enterAmount = enterAmount;
    }
    
    @Override
    public String toString()
    {
        return "OrderAccountStateRecord [id=" + id + ", seqNo=" + seqNo + ", merNum=" + merNum + ", merName=" + merName + ", termId=" + termId
            + ", settlementDate=" + settlementDate + ", transactionDate=" + transactionDate + ", tradingTime=" + tradingTime + ", orderId=" + orderId
            + ", tradingType=" + tradingType + ", tradingAmount=" + tradingAmount + ", serviceCharge=" + serviceCharge + ", enterAmount=" + enterAmount
            + ", cardNumber=" + cardNumber + ", cardDomain=" + cardDomain + ", cardSpecies=" + cardSpecies + ", referNo=" + referNo + ", oReferNo=" + oReferNo
            + ", oTradingTime=" + oTradingTime + ", payType=" + payType + ", bankCode=" + bankCode + "]";
    }
    
}