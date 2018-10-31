package com.todaysoft.lims.order.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class OrderPaymentConfirm
{
    private String id;
    
    private String orderId;
    
    private String oposId;
    
    private String otrsId;
    
    private String checkId;
    
    private String checkName;
    
    private Date checkTime;
    
    private Date paymentTime;
    
    private BigDecimal checkAmount;
    
    private String remark;
    
    private String paymenter;
    
    private Integer paymentType;
    
    private Integer payType;
    
    private String transferNo;
    
    private String posNo;
    
    private String receiptRolls;
    
    private String tradeNo;
    
    private String tradeParams;
    
    private String merNum;
    
    private String termId;
    
    private String tranDate;
    
    private String referNo;
    
    private String batchNo;
    
    private String serialNo;
    
    private String oBatchno;
    
    private String oSerialno;
    
    private String tranType;
    
    private String wangPayType;
    
    private String orderCode;
    
    private String ext1;
    
    private String ext2;
    
    private String addVal;
    
    private Integer reconciliationStatus; //对账状态
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id == null ? null : id.trim();
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId == null ? null : orderId.trim();
    }
    
    public String getOposId()
    {
        return oposId;
    }
    
    public void setOposId(String oposId)
    {
        this.oposId = oposId == null ? null : oposId.trim();
    }
    
    public String getOtrsId()
    {
        return otrsId;
    }
    
    public void setOtrsId(String otrsId)
    {
        this.otrsId = otrsId == null ? null : otrsId.trim();
    }
    
    public String getCheckId()
    {
        return checkId;
    }
    
    public void setCheckId(String checkId)
    {
        this.checkId = checkId == null ? null : checkId.trim();
    }
    
    public String getCheckName()
    {
        return checkName;
    }
    
    public void setCheckName(String checkName)
    {
        this.checkName = checkName == null ? null : checkName.trim();
    }
    
    public Date getCheckTime()
    {
        return checkTime;
    }
    
    public void setCheckTime(Date checkTime)
    {
        this.checkTime = checkTime;
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
        this.remark = remark == null ? null : remark.trim();
    }
    
    public String getPaymenter()
    {
        return paymenter;
    }
    
    public void setPaymenter(String paymenter)
    {
        this.paymenter = paymenter == null ? null : paymenter.trim();
    }
    
    public Integer getPaymentType()
    {
        return paymentType;
    }
    
    public void setPaymentType(Integer paymentType)
    {
        this.paymentType = paymentType;
    }
    
    public Integer getPayType()
    {
        return payType;
    }
    
    public void setPayType(Integer payType)
    {
        this.payType = payType;
    }
    
    public String getTransferNo()
    {
        return transferNo;
    }
    
    public void setTransferNo(String transferNo)
    {
        this.transferNo = transferNo == null ? null : transferNo.trim();
    }
    
    public String getPosNo()
    {
        return posNo;
    }
    
    public void setPosNo(String posNo)
    {
        this.posNo = posNo == null ? null : posNo.trim();
    }
    
    public String getReceiptRolls()
    {
        return receiptRolls;
    }
    
    public void setReceiptRolls(String receiptRolls)
    {
        this.receiptRolls = receiptRolls == null ? null : receiptRolls.trim();
    }
    
    public String getTradeNo()
    {
        return tradeNo;
    }
    
    public void setTradeNo(String tradeNo)
    {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }
    
    public String getTradeParams()
    {
        return tradeParams;
    }
    
    public void setTradeParams(String tradeParams)
    {
        this.tradeParams = tradeParams == null ? null : tradeParams.trim();
    }
    
    public String getMerNum()
    {
        return merNum;
    }
    
    public void setMerNum(String merNum)
    {
        this.merNum = merNum == null ? null : merNum.trim();
    }
    
    public String getTermId()
    {
        return termId;
    }
    
    public void setTermId(String termId)
    {
        this.termId = termId == null ? null : termId.trim();
    }
    
    public String getTranDate()
    {
        return tranDate;
    }
    
    public void setTranDate(String tranDate)
    {
        this.tranDate = tranDate == null ? null : tranDate.trim();
    }
    
    public String getReferNo()
    {
        return referNo;
    }
    
    public void setReferNo(String referNo)
    {
        this.referNo = referNo == null ? null : referNo.trim();
    }
    
    public String getBatchNo()
    {
        return batchNo;
    }
    
    public void setBatchNo(String batchNo)
    {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }
    
    public String getSerialNo()
    {
        return serialNo;
    }
    
    public void setSerialNo(String serialNo)
    {
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }
    
    public String getoBatchno()
    {
        return oBatchno;
    }
    
    public void setoBatchno(String oBatchno)
    {
        this.oBatchno = oBatchno == null ? null : oBatchno.trim();
    }
    
    public String getoSerialno()
    {
        return oSerialno;
    }
    
    public void setoSerialno(String oSerialno)
    {
        this.oSerialno = oSerialno == null ? null : oSerialno.trim();
    }
    
    public String getTranType()
    {
        return tranType;
    }
    
    public void setTranType(String tranType)
    {
        this.tranType = tranType == null ? null : tranType.trim();
    }
    
    public String getWangPayType()
    {
        return wangPayType;
    }
    
    public void setWangPayType(String wangPayType)
    {
        this.wangPayType = wangPayType == null ? null : wangPayType.trim();
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }
    
    public String getExt1()
    {
        return ext1;
    }
    
    public void setExt1(String ext1)
    {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }
    
    public String getExt2()
    {
        return ext2;
    }
    
    public void setExt2(String ext2)
    {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }
    
    public String getAddVal()
    {
        return addVal;
    }
    
    public void setAddVal(String addVal)
    {
        this.addVal = addVal == null ? null : addVal.trim();
    }
    
    public BigDecimal getCheckAmount()
    {
        return checkAmount;
    }
    
    public void setCheckAmount(BigDecimal checkAmount)
    {
        this.checkAmount = checkAmount;
    }
    
    public Integer getReconciliationStatus()
    {
        return reconciliationStatus;
    }
    
    public void setReconciliationStatus(Integer reconciliationStatus)
    {
        this.reconciliationStatus = reconciliationStatus;
    }
    
}