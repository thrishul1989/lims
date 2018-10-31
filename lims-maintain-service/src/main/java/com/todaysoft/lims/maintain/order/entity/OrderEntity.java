package com.todaysoft.lims.maintain.order.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.maintain.entity.OrderProduct;

@Entity
@Table(name = "LIMS_ORDER")
public class OrderEntity implements Serializable
{
    private static final long serialVersionUID = 2161585840633690043L;
    
    private String id;
    
    private String code;
    
    private String doctorAssist;
    
    private Date submitTime;
    
    private String salesmanName;
    
    private Integer sheduleStatus;//流程状态 0未启动1启动异常2成功
    
    private Integer testingStatus;
    
    private Date updateTime;
    
    private Integer amount;//订单金额，单位（分）
    
    private Integer incomingAmount;
    
    private Integer subsidiarySampleAmount = 0; //附属样本额外收费金额，单位（分）
    
    private List<OrderReduce> orderReduce; //关联订单减免记录
    
    private Integer discountAmount = 0; //优惠券抵用金额
    
    private String orderType;//订单类型（临检、科研、健康）
    
    private Integer settlementType;
    
    private String contractId; //归属合同
    
    private Integer paymentStatus;
    
    private boolean deleted;
    
    private Integer receivedSampleStatus;//样本是否入库完成 0-未入库1-已入库
    
    private Date payTime;
    
    @Column(name = "RECEIVED_SAMPLE_STATUS")
    public Integer getReceivedSampleStatus()
    {
        return receivedSampleStatus;
    }
    
    public void setReceivedSampleStatus(Integer receivedSampleStatus)
    {
        this.receivedSampleStatus = receivedSampleStatus;
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
    
    @Column(name = "PAYMENT_STATUS")
    public Integer getPaymentStatus()
    {
        return paymentStatus;
    }
    
    public void setPaymentStatus(Integer paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }
    
    private List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();
    
    @OneToMany(mappedBy = "order", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    public List<OrderProduct> getOrderProductList()
    {
        return orderProductList;
    }
    
    public void setOrderProductList(List<OrderProduct> orderProductList)
    {
        this.orderProductList = orderProductList;
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
    
    @Column(name = "SETTLEMENT_TYPE")
    public Integer getSettlementType()
    {
        return settlementType;
    }
    
    public void setSettlementType(Integer settlementType)
    {
        this.settlementType = settlementType;
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
    
    @Column(name = "INCOMING_AMOUNT")
    public Integer getIncomingAmount()
    {
        return incomingAmount;
    }
    
    public void setIncomingAmount(Integer incomingAmount)
    {
        this.incomingAmount = incomingAmount;
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
    
    @Id
    @Column(name = "ID")
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @Column(name = "DOCTOR_ASSIST")
    public String getDoctorAssist()
    {
        return doctorAssist;
    }
    
    public void setDoctorAssist(String doctorAssist)
    {
        this.doctorAssist = doctorAssist;
    }
    
    @Column(name = "SUBMIT_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
    
    @Column(name = "CREATOR_NAME")
    public String getSalesmanName()
    {
        return salesmanName;
    }
    
    public void setSalesmanName(String salesmanName)
    {
        this.salesmanName = salesmanName;
    }
    
    @Column(name = "SHEDULE_STATUS")
    public Integer getSheduleStatus()
    {
        return sheduleStatus;
    }
    
    public void setSheduleStatus(Integer sheduleStatus)
    {
        this.sheduleStatus = sheduleStatus;
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
    
    @Column(name = "UPDATE_TIME")
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
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
    
    @Column(name = "PAY_TIME")
    public Date getPayTime()
    {
        return payTime;
    }
    
    public void setPayTime(Date payTime)
    {
        this.payTime = payTime;
    }
}
