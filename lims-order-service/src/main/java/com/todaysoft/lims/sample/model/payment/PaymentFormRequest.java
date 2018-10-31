package com.todaysoft.lims.sample.model.payment;

import java.util.Date;

public class PaymentFormRequest

{
    private String id;
    
    private String orderId;
    
    private String applyId; //退款申请id
    
    private String checkId;
    
    private String checkName;
    
    private Date checkTime;
    
    private Double checkAmount;
    
    private Date paymentTime;
    
    private String remark;
    
    private String payType;
    
    private String posNo;
    
    private String receiptRolls;
    
    private String transferNo; //转账卡号
    
    private String discountCouponId; //优惠券id
    
    private String paymenter;
    
    private String instrumentImg;
    
    private String posOrTransId; //前台传递POS机或者转账对象
    
    private String contractId; //合同id
    
    private String settleBillId; //账单id
    
    private String bankType;
    
    private String bankNo;
    
    private String bankOwnerName;
    
    public String getBankType()
    {
        return bankType;
    }
    
    public void setBankType(String bankType)
    {
        this.bankType = bankType;
    }
    
    public String getBankNo()
    {
        return bankNo;
    }
    
    public void setBankNo(String bankNo)
    {
        this.bankNo = bankNo;
    }
    
    public String getBankOwnerName()
    {
        return bankOwnerName;
    }
    
    public void setBankOwnerName(String bankOwnerName)
    {
        this.bankOwnerName = bankOwnerName;
    }
    
    public String getTransferNo()
    {
        return transferNo;
    }
    
    public void setTransferNo(String transferNo)
    {
        this.transferNo = transferNo;
    }
    
    public String getApplyId()
    {
        return applyId;
    }
    
    public void setApplyId(String applyId)
    {
        this.applyId = applyId;
    }
    
    public String getSettleBillId()
    {
        return settleBillId;
    }
    
    public void setSettleBillId(String settleBillId)
    {
        this.settleBillId = settleBillId;
    }
    
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    public String getPosOrTransId()
    {
        return posOrTransId;
    }
    
    public void setPosOrTransId(String posOrTransId)
    {
        this.posOrTransId = posOrTransId;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
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
    
    public Double getCheckAmount()
    {
        return checkAmount;
    }
    
    public void setCheckAmount(Double checkAmount)
    {
        this.checkAmount = checkAmount;
    }
    
    public Date getPaymentTime()
    {
        return paymentTime;
    }
    
    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getPayType()
    {
        return payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    public String getPosNo()
    {
        return posNo;
    }
    
    public void setPosNo(String posNo)
    {
        this.posNo = posNo;
    }
    
    public String getReceiptRolls()
    {
        return receiptRolls;
    }
    
    public void setReceiptRolls(String receiptRolls)
    {
        this.receiptRolls = receiptRolls;
    }
    
    public String getDiscountCouponId()
    {
        return discountCouponId;
    }
    
    public void setDiscountCouponId(String discountCouponId)
    {
        this.discountCouponId = discountCouponId;
    }
    
    public String getPaymenter()
    {
        return paymenter;
    }
    
    public void setPaymenter(String paymenter)
    {
        this.paymenter = paymenter;
    }
    
    public String getInstrumentImg()
    {
        return instrumentImg;
    }
    
    public void setInstrumentImg(String instrumentImg)
    {
        this.instrumentImg = instrumentImg;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
}
