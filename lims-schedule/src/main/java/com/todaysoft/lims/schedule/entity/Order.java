package com.todaysoft.lims.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER")
public class Order extends UuidKeyEntity
{
    private static final long serialVersionUID = 5216119770727640957L;
    
    public static final String TESTING_STATUS_CANCELED = "4";
    
    public static final String PAYMENT_STATUS_CONFIRMED = "1";
    
    public static final String SAMPLE_STATUS_CONFIRMED = "1";
    
    public static final String SCHEDULE_STARTED = "2";
    
    private String code;
    
    private String contractId;
    
    private Integer productAmount;
    
    private Integer subsidiarySampleAmount;
    
    private Integer incomingAmount;
    
    private Integer discountAmount;
    
    private Integer reduceAmount;
    
    private Date submitTime;
    
    private String status;
    
    private String testingStatus;
    
    private String scheduleStatus;
    
    private String sampleConfirmStatus;
    
    private String paymentConfirmStatus;
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
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
    
    @Column(name = "AMOUNT")
    public Integer getProductAmount()
    {
        return productAmount;
    }
    
    public void setProductAmount(Integer productAmount)
    {
        this.productAmount = productAmount;
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
    
    @Column(name = "INCOMING_AMOUNT")
    public Integer getIncomingAmount()
    {
        return incomingAmount;
    }
    
    public void setIncomingAmount(Integer incomingAmount)
    {
        this.incomingAmount = incomingAmount;
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
    
    @Column(name = "STATUS")
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    @Column(name = "TESTING_STATUS")
    public String getTestingStatus()
    {
        return testingStatus;
    }
    
    public void setTestingStatus(String testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    
    @Column(name = "SHEDULE_STATUS")
    public String getScheduleStatus()
    {
        return scheduleStatus;
    }
    
    public void setScheduleStatus(String scheduleStatus)
    {
        this.scheduleStatus = scheduleStatus;
    }
    
    @Column(name = "RECEIVED_SAMPLE_STATUS")
    public String getSampleConfirmStatus()
    {
        return sampleConfirmStatus;
    }
    
    public void setSampleConfirmStatus(String sampleConfirmStatus)
    {
        this.sampleConfirmStatus = sampleConfirmStatus;
    }
    
    @Column(name = "SCHEDULE_PAYMENT_STATUS")
    public String getPaymentConfirmStatus()
    {
        return paymentConfirmStatus;
    }
    
    public void setPaymentConfirmStatus(String paymentConfirmStatus)
    {
        this.paymentConfirmStatus = paymentConfirmStatus;
    }
    
    @Column(name = "IF_URGENT")
    public Integer getIfUrgent()
    {
        return ifUrgent;
    }
    
    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }
    
    @Column(name = "URGENT_UPDATE_TIME")
    public Date getUrgentUpdateTime()
    {
        return urgentUpdateTime;
    }
    
    public void setUrgentUpdateTime(Date urgentUpdateTime)
    {
        this.urgentUpdateTime = urgentUpdateTime;
    }
    
    @Column(name = "URGENT_NAME")
    public String getUrgentName()
    {
        return urgentName;
    }
    
    public void setUrgentName(String urgentName)
    {
        this.urgentName = urgentName;
    }
}
