package com.todaysoft.lims.maintain.order.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CONTRACT_PAYMENT_RECORD")
public class ContractPaymentRecord extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String contractId;
    
    private String settleBillId; //账单id
    
    private String checkId;
    
    private String checkName;
    
    private Date checkTime;
    
    private Integer payType;
    
    private String paymenter;
    
    private Date paymentTime;
    
    private Double checkAmount;
    
    private String remark;
    
    private String instrumentImg;
    
    private Integer reconciliation;
    
    @Column(name = "SETTLE_BILL_ID")
    public String getSettleBillId()
    {
        return settleBillId;
    }
    
    public void setSettleBillId(String settleBillId)
    {
        this.settleBillId = settleBillId;
    }
    
    @Column(name = "CONTRACT_ID")
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
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
    
    @Column(name = "PAY_TYPE")
    public Integer getPayType()
    {
        return payType;
    }
    
    public void setPayType(Integer payType)
    {
        this.payType = payType;
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
    
    @Column(name = "PAYMENT_TIME")
    public Date getPaymentTime()
    {
        return paymentTime;
    }
    
    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
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
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @Column(name = "PAYMENT_INSTRUMENT_IMG")
    public String getInstrumentImg()
    {
        return instrumentImg;
    }
    
    public void setInstrumentImg(String instrumentImg)
    {
        this.instrumentImg = instrumentImg;
    }
    
    @Column(name = "RECONCILIATION")
    public Integer getReconciliation()
    {
        return reconciliation;
    }
    
    public void setReconciliation(Integer reconciliation)
    {
        this.reconciliation = reconciliation;
    }
    
}
