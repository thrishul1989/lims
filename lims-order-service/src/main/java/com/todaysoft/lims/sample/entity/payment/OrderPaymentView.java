package com.todaysoft.lims.sample.entity.payment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_PAYMENT_VIEW")
public class OrderPaymentView extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private String protoId;
    
    private String paymentType;
    
    private Integer paymentAmount;
    
    private String operateName;//操作人
    
    private String paymenter; //费用关系人
    
    private Date operateTime;
    
    private String remark;
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "PROTO_ID")
    public String getProtoId()
    {
        return protoId;
    }
    
    public void setProtoId(String protoId)
    {
        this.protoId = protoId;
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
    
    @Column(name = "PAYMENT_AMOUNT")
    public Integer getPaymentAmount()
    {
        return paymentAmount;
    }
    
    public void setPaymentAmount(Integer paymentAmount)
    {
        this.paymentAmount = paymentAmount;
    }
    
    @Column(name = "PAYMENT_CREATOR")
    public String getOperateName()
    {
        return operateName;
    }
    
    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
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
    public Date getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
}
