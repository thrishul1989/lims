package com.todaysoft.lims.sample.entity.payment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_PAYMENT_CONFIRM")
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
    
    private String payType; //支付类型，1：支付宝；2：微信；3：POS机；4：转账
    
    /***********NEW********************/
    
    private String posNo; //pos机交易参考号
    
    private String receiptRolls; //pos机收据单号
    
    private String transferNo; //转账卡号
    
    /*********NEW*********/
    
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
    
    private String tradeNo;
    
    @Column(name = "POS_NO")
    public String getPosNo()
    {
        return posNo;
    }
    
    public void setPosNo(String posNo)
    {
        this.posNo = posNo;
    }
    
    @Column(name = "RECEIPT_ROLLS")
    public String getReceiptRolls()
    {
        return receiptRolls;
    }
    
    public void setReceiptRolls(String receiptRolls)
    {
        this.receiptRolls = receiptRolls;
    }
    
    @Column(name = "TRANSFER_NO")
    public String getTransferNo()
    {
        return transferNo;
    }
    
    public void setTransferNo(String transferNo)
    {
        this.transferNo = transferNo;
    }
    
    @Column(name = "PAY_TYPE")
    public String getPayType()
    {
        return payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "CHECK_ID")
    public String getCheckId()
    {
        return checkId;
    }
    
    public void setCheckId(String checkId)
    {
        this.checkId = checkId;
    }
    
    @Column(name = "CHECK_NAME")
    public String getCheckName()
    {
        return checkName;
    }
    
    public void setCheckName(String checkName)
    {
        this.checkName = checkName;
    }
    
    @Column(name = "CHECK_TIME")
    public Date getCheckTime()
    {
        return checkTime;
    }
    
    public void setCheckTime(Date checkTime)
    {
        this.checkTime = checkTime;
    }
    
    @Column(name = "CHECK_AMOUNT")
    public Double getCheckAmount()
    {
        return checkAmount;
    }
    
    public void setCheckAmount(Double checkAmount)
    {
        this.checkAmount = checkAmount;
    }
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return StringUtils.isEmpty(remark) ? "" : remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @Column(name = "PAYMENT_TIME")
    public Date getPaymentTime()
    {
        return paymentTime;
    }
    
    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
    }
    
    @Column(name = "PAYMENTER")
    public String getPaymenter()
    {
        return paymenter;
    }
    
    public void setPaymenter(String paymenter)
    {
        this.paymenter = paymenter;
    }
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OPOS_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public OrderPos getOrderPos()
    {
        return orderPos;
    }
    
    public void setOrderPos(OrderPos orderPos)
    {
        this.orderPos = orderPos;
    }
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OTRS_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public OrderTransfer getOrderTransfer()
    {
        return orderTransfer;
    }
    
    public void setOrderTransfer(OrderTransfer orderTransfer)
    {
        this.orderTransfer = orderTransfer;
    }
    
    @Column(name = "PAYMENT_TYPE")
    public String getPaymentType()
    {
        return paymentType;
    }
    
    public void setPaymentType(String paymentType)
    {
        this.paymentType = paymentType;
    }
    
    @Column(name = "MER_NUM")
    public String getMerNum()
    {
        return merNum;
    }
    
    public void setMerNum(String merNum)
    {
        this.merNum = merNum;
    }
    
    @Column(name = "TERM_ID")
    public String getTermId()
    {
        return termId;
    }
    
    public void setTermId(String termId)
    {
        this.termId = termId;
    }
    
    @Column(name = "TRAN_DATE")
    public String getTranDate()
    {
        return tranDate;
    }
    
    public void setTranDate(String tranDate)
    {
        this.tranDate = tranDate;
    }
    
    @Column(name = "REFER_NO")
    public String getReferNo()
    {
        return referNo;
    }
    
    public void setReferNo(String referNo)
    {
        this.referNo = referNo;
    }
    
    @Column(name = "BATCH_NO")
    public String getBatchNo()
    {
        return batchNo;
    }
    
    public void setBatchNo(String batchNo)
    {
        this.batchNo = batchNo;
    }
    
    @Column(name = "SERIAL_NO")
    public String getSerialNo()
    {
        return serialNo;
    }
    
    public void setSerialNo(String serialNo)
    {
        this.serialNo = serialNo;
    }
    
    @Column(name = "O_BATCHNO")
    public String getoBatchno()
    {
        return oBatchno;
    }
    
    public void setoBatchno(String oBatchno)
    {
        this.oBatchno = oBatchno;
    }
    
    @Column(name = "O_SERIALNO")
    public String getoSerialno()
    {
        return oSerialno;
    }
    
    public void setoSerialno(String oSerialno)
    {
        this.oSerialno = oSerialno;
    }
    
    @Column(name = "TRAN_TYPE")
    public String getTranType()
    {
        return tranType;
    }
    
    public void setTranType(String tranType)
    {
        this.tranType = tranType;
    }
    
    @Column(name = "WANG_PAY_TYPE")
    public String getWangPayType()
    {
        return wangPayType;
    }
    
    public void setWangPayType(String wangPayType)
    {
        this.wangPayType = wangPayType;
    }
    
    @Column(name = "ORDER_CODE")
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    @Column(name = "TRADE_NO")
    public String getTradeNo()
    {
        return tradeNo;
    }
    
    public void setTradeNo(String tradeNo)
    {
        this.tradeNo = tradeNo;
    }
    
}
