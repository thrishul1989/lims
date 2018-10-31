package com.todaysoft.lims.sample.model.order;

import java.util.Date;

public class CostInfoModel
{
  //--------费用信息----------
    private String invoiceTitle;//发票抬头
    private String payType;//付款方式
    private String payStatus;//付款状态
    private double reduceAmount;//减免金额
    private double discountAmount;//抵用券
    private String reduceReason;//减免原因
    private String reduceStatus;//减免状态
    private String reduceCheckTime;//减免审核通过时间
    private double amount;//应付款
    private double incomingAmount;//实付款
    private String paymentTime;//付款时间
    private String checkTime;//付款确认时间
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    public String getPayType()
    {
        return payType;
    }
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    public String getPayStatus()
    {
        return payStatus;
    }
    public void setPayStatus(String payStatus)
    {
        this.payStatus = payStatus;
    }
    public double getReduceAmount()
    {
        return reduceAmount;
    }
    public void setReduceAmount(double reduceAmount)
    {
        this.reduceAmount = reduceAmount;
    }
    public double getDiscountAmount()
    {
        return discountAmount;
    }
    public void setDiscountAmount(double discountAmount)
    {
        this.discountAmount = discountAmount;
    }
    public String getReduceReason()
    {
        return reduceReason;
    }
    public void setReduceReason(String reduceReason)
    {
        this.reduceReason = reduceReason;
    }
    public String getReduceStatus()
    {
        return reduceStatus;
    }
    public void setReduceStatus(String reduceStatus)
    {
        this.reduceStatus = reduceStatus;
    }
    public String getReduceCheckTime()
    {
        return reduceCheckTime;
    }
    public void setReduceCheckTime(String reduceCheckTime)
    {
        this.reduceCheckTime = reduceCheckTime;
    }
    public double getAmount()
    {
        return amount;
    }
    public void setAmount(double amount)
    {
        this.amount = amount;
    }
    public double getIncomingAmount()
    {
        return incomingAmount;
    }
    public void setIncomingAmount(double incomingAmount)
    {
        this.incomingAmount = incomingAmount;
    }
    public String getPaymentTime()
    {
        return paymentTime;
    }
    public void setPaymentTime(String paymentTime)
    {
        this.paymentTime = paymentTime;
    }
    public String getCheckTime()
    {
        return checkTime;
    }
    public void setCheckTime(String checkTime)
    {
        this.checkTime = checkTime;
    }
    
}
