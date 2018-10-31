package com.todaysoft.lims.system.model.vo.order;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class ContractReconciliationRecord extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String paymentRecordId; // '收款记录表id',
    
    private String contractId;// '对账合同id',
    
    private String orderId;// '对账订单id',
    
    private String orderCode;// '对账订单编号',
    
    private Integer reconciliationAmount;// '对账金额', 
    
    private String createId;// '操作人id',
    
    private String createName;// '操作人',
    
    private Date createTime;// '操作时间',
    
    public String getPaymentRecordId()
    {
        return paymentRecordId;
    }
    
    public void setPaymentRecordId(String paymentRecordId)
    {
        this.paymentRecordId = paymentRecordId;
    }
    
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getCreateId()
    {
        return createId;
    }
    
    public void setCreateId(String createId)
    {
        this.createId = createId;
    }
    
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }
    
    public Integer getReconciliationAmount()
    {
        return reconciliationAmount;
    }
    
    public void setReconciliationAmount(Integer reconciliationAmount)
    {
        this.reconciliationAmount = reconciliationAmount;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
}
