package com.todaysoft.lims.system.model.vo.payment;

import java.util.Date;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class OrderPaymentConfirm extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private String checkId;
    
    private String checkName;
    
    private Date checkTime;
    
    private Double checkAmount;
    
    private Date paymentTime;
    
    private String remark;
    
    /***************************/
    
    private OrderPos orderPos;
    
    private OrderTransfer orderTransfer;
    
    private String paymenter;
    
    private String paymentType;//收款类别 ： 1收款；2补款
    
    private String payType;//支付类型  1：支付宝；2：微信；3：POS机；4：转账
    
    /**************NEW************************/
    
    private String posNo; //pos机交易参考号
    
    private String receiptRolls; //pos机收据单号
    
    private String transferNo; //转账卡号
    
    /***************NEW***************************/
    
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
    
    private int reconciliationStatus; //对账状态
    
    private String tradeNo;
    
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
    
    public String getTransferNo()
    {
        return transferNo;
    }
    
    public void setTransferNo(String transferNo)
    {
        this.transferNo = transferNo;
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
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Date getPaymentTime()
    {
        return paymentTime;
    }
    
    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
    }
    
    public String getPaymenter()
    {
        return paymenter;
    }
    
    public void setPaymenter(String paymenter)
    {
        this.paymenter = paymenter;
    }
    
    public OrderPos getOrderPos()
    {
        return orderPos;
    }
    
    public void setOrderPos(OrderPos orderPos)
    {
        this.orderPos = orderPos;
    }
    
    public OrderTransfer getOrderTransfer()
    {
        return orderTransfer;
    }
    
    public void setOrderTransfer(OrderTransfer orderTransfer)
    {
        this.orderTransfer = orderTransfer;
    }
    
    public String getPaymentType()
    {
        return paymentType;
    }
    
    public void setPaymentType(String paymentType)
    {
        this.paymentType = paymentType;
    }
    
    public String getPayType()
    {
        return payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    public String getMerNum()
    {
        return merNum;
    }
    
    public void setMerNum(String merNum)
    {
        this.merNum = merNum;
    }
    
    public String getTermId()
    {
        return termId;
    }
    
    public void setTermId(String termId)
    {
        this.termId = termId;
    }
    
    public String getTranDate()
    {
        return tranDate;
    }
    
    public void setTranDate(String tranDate)
    {
        this.tranDate = tranDate;
    }
    
    public String getReferNo()
    {
        return referNo;
    }
    
    public void setReferNo(String referNo)
    {
        this.referNo = referNo;
    }
    
    public String getBatchNo()
    {
        return batchNo;
    }
    
    public void setBatchNo(String batchNo)
    {
        this.batchNo = batchNo;
    }
    
    public String getSerialNo()
    {
        return serialNo;
    }
    
    public void setSerialNo(String serialNo)
    {
        this.serialNo = serialNo;
    }
    
    public String getoBatchno()
    {
        return oBatchno;
    }
    
    public void setoBatchno(String oBatchno)
    {
        this.oBatchno = oBatchno;
    }
    
    public String getoSerialno()
    {
        return oSerialno;
    }
    
    public void setoSerialno(String oSerialno)
    {
        this.oSerialno = oSerialno;
    }
    
    public String getTranType()
    {
        return tranType;
    }
    
    public void setTranType(String tranType)
    {
        this.tranType = tranType;
    }
    
    public String getWangPayType()
    {
        return wangPayType;
    }
    
    public void setWangPayType(String wangPayType)
    {
        this.wangPayType = wangPayType;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getExt1()
    {
        return ext1;
    }
    
    public void setExt1(String ext1)
    {
        this.ext1 = ext1;
    }
    
    public String getExt2()
    {
        return ext2;
    }
    
    public void setExt2(String ext2)
    {
        this.ext2 = ext2;
    }
    
    public String getAddVal()
    {
        return addVal;
    }
    
    public void setAddVal(String addVal)
    {
        this.addVal = addVal;
    }
    
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    
    public int getReconciliationStatus()
    {
        return reconciliationStatus;
    }
    
    public void setReconciliationStatus(int reconciliationStatus)
    {
        this.reconciliationStatus = reconciliationStatus;
    }
    
    public String getTradeNo()
    {
        return tradeNo;
    }
    
    public void setTradeNo(String tradeNo)
    {
        this.tradeNo = tradeNo;
    }
    
}
