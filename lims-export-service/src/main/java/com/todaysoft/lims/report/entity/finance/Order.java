package com.todaysoft.lims.report.entity.finance;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.report.entity.system.Contract;

@Entity
@Table(name = "LIMS_ORDER")
public class Order extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String orderType;//订单类型（临检、科研、健康）
    
    private String code; //订单编号
    
    private Integer status;//订单状态
    
    private Integer amount;//订单金额，单位（分）
    
    private Contract contract; //归属合同
    
    private boolean deleted;
    
    private Date submitTime;
    
    private Integer incomingAmount;
    
    private Integer subsidiarySampleAmount = 0; //附属样本额外收费金额，单位（分）
    
    private String discountCouponId; //抵用优惠券ID
    
    private Integer discountAmount = 0; //优惠券抵用金额
    
    private Integer reduceAmount = 0; //减免金额
    
    private Integer paymentStatus;
    
    private Integer paymentDelayStatus;
    
    private Integer testingStatus;
    
    private Integer settlementType;
    
    private List<OrderReduce> orderReduce; //关联订单减免记录
    
    @OneToMany(mappedBy = "orderId", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<OrderReduce> getOrderReduce()
    {
        return orderReduce;
    }
    
    public void setOrderReduce(List<OrderReduce> orderReduce)
    {
        this.orderReduce = orderReduce;
    }
    
    @Column(name = "INCOMING_AMOUNT")
    public Integer getIncomingAmount()
    {
        return incomingAmount;
    }
    
    public void setIncomingAmount(Integer incomingAmount)
    {
        this.incomingAmount = incomingAmount;
    }
    
    public Order()
    {
        super();
    }
    
    @Column(name = "ORDER_TYPE")
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = StringUtils.isNotEmpty(code) ? code.toUpperCase() : code;
    }
    
    @Column(name = "STATUS")
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    @Column(name = "AMOUNT")
    public Integer getAmount()
    {
        return amount;
    }
    
    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTRACT_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Contract getContract()
    {
        return contract;
    }
    
    public void setContract(Contract contract)
    {
        this.contract = contract;
    }
    
    @Column(name = "DEL_FLAG")
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    @Column(name = "SUBMIT_TIME")
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
    
    @Column(name = "SUBSIDIARY_SAMPLE_AMOUNT")
    public Integer getSubsidiarySampleAmount()
    {
        return subsidiarySampleAmount;
    }
    
    public void setSubsidiarySampleAmount(Integer subsidiarySampleAmount)
    {
        this.subsidiarySampleAmount = subsidiarySampleAmount;
    }
    
    @Column(name = "DISCOUNT_AMOUNT")
    public Integer getDiscountAmount()
    {
        return discountAmount;
    }
    
    public void setDiscountAmount(Integer discountAmount)
    {
        this.discountAmount = discountAmount;
    }
    
    @Column(name = "REDUCE_AMOUNT")
    public Integer getReduceAmount()
    {
        return reduceAmount;
    }
    
    public void setReduceAmount(Integer reduceAmount)
    {
        this.reduceAmount = reduceAmount;
    }
    
    @Column(name = "DISCOUNT_COUPON_ID")
    public String getDiscountCouponId()
    {
        return discountCouponId;
    }
    
    public void setDiscountCouponId(String discountCouponId)
    {
        this.discountCouponId = discountCouponId;
    }
    
    @Column(name = "PAYMENT_STATUS")
    public Integer getPaymentStatus()
    {
        return paymentStatus;
    }
    
    public void setPaymentStatus(Integer paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }
    
    @Column(name = "PAYMENT_DELAY_STATUS")
    public Integer getPaymentDelayStatus()
    {
        return paymentDelayStatus;
    }
    
    public void setPaymentDelayStatus(Integer paymentDelayStatus)
    {
        this.paymentDelayStatus = paymentDelayStatus;
    }
    
    @Column(name = "TESTING_STATUS")
    public Integer getTestingStatus()
    {
        return testingStatus;
    }
    
    public void setTestingStatus(Integer testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    
    @Column(name = "SETTLEMENT_TYPE")
    public Integer getSettlementType()
    {
        return settlementType;
    }
    
    public void setSettlementType(Integer settlementType)
    {
        this.settlementType = settlementType;
    }
    
}
