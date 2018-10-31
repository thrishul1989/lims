package com.todaysoft.lims.sample.entity.order;

import java.util.ArrayList;
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
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OrderBy;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.sample.entity.contract.Contract;
import com.todaysoft.lims.sample.entity.payment.OrderDelay;
import com.todaysoft.lims.sample.entity.payment.OrderReduce;
import com.todaysoft.lims.sample.entity.payment.OrderRefund;
import com.todaysoft.lims.sample.model.order.OrderSampleViewRequest;

@Entity
@Table(name = "LIMS_ORDER")
public class SimpleOrder extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String orderType;//订单类型（临检、科研、健康）
    
    private String code; //订单编号
    
    private Integer status;//订单状态
    
    private Integer amount;//订单金额，单位（分）
    
    private String recipientName;//收件人姓名
    
    private String recipientPhone;//收件人电话
    
    private String recipientEmail;//收件人邮箱
    
    private String recipientAddress;//收件人地址
    
    private String doctorAssist;//客户参与（X/X）
    
    private String invoiceTitle;//发票抬头
    
    private String creatorId;//创建人ID
    
    private String creatorName;//业务员
    
    private Date createTime; //创建时间
    
    private Contract contract; //归属合同
    
    private boolean deleted;
    
    //  private Date deleteTime;
    
    private List<OrderExaminee> orderExamineeList = new ArrayList<OrderExaminee>(); //受检人
    
    private List<OrderSampleGroup> orderSampleGroup = new ArrayList<OrderSampleGroup>();
    
    //  private List<OrderSampleView> sampleView = new ArrayList<OrderSampleView>();
    
    private String ownerId; //订单所属客户
    
    private String submitterId;
    
    private String submitterName;
    
    private Integer submitSource; //1--app  2--业务后台
    
    private Date updateTime; //创建时间
    
    private String updatorName;
    
    private String updatorId;
    
    private Date submitTime;
    
    private Integer incomingAmount;
    
    private Integer subsidiarySampleAmount = 0; //附属样本额外收费金额，单位（分）
    
    private String discountCouponId; //抵用优惠券ID
    
    private Integer discountAmount = 0; //优惠券抵用金额
    
    private Integer reduceAmount = 0; //减免金额
    
    private String payType; //付款方式
    
    private Integer sheduleStatus;//流程状态 0未启动1启动异常2成功
    
    private Integer schedulePaymentStatus; //合同订单检测状态
    
    private Integer receivedSampleStatus;//样本是否入库完成 0-未入库1-已入库
    
    private String remark;//启动异常原因
    
    private Integer draft;
    
    private Integer paymentStatus;
    
    private Integer fullPaid;
    
    private Integer paymentDelayStatus;
    
    private Integer testingStatus;
    
    private Integer settlementType;
    
    private Integer issueInvoice;//状态：1-待开票 2-可开票 3-已开票 4-已寄送
    
    private Integer invoiceApplyType;//开票类型 ：1默认开票 2提前开票
    
    private Integer samplingAmount; //增加收样费用
    
    private Date startTime;//启动时间
    
    @Column(name = "SAMPLING_AMOUNT")
    public Integer getSamplingAmount()
    {
        return samplingAmount;
    }
    
    public void setSamplingAmount(Integer samplingAmount)
    {
        this.samplingAmount = samplingAmount;
    }
    
    @Column(name = "INVOICE_APPLY_TYPE")
    public Integer getInvoiceApplyType()
    {
        return invoiceApplyType;
    }
    
    public void setInvoiceApplyType(Integer invoiceApplyType)
    {
        this.invoiceApplyType = invoiceApplyType;
    }
    
    @Column(name = "ISSUE_INVOICE")
    public Integer getIssueInvoice()
    {
        return issueInvoice;
    }
    
    public void setIssueInvoice(Integer issueInvoice)
    {
        this.issueInvoice = issueInvoice;
    }
    
    @Column(name = "SCHEDULE_PAYMENT_STATUS")
    public Integer getSchedulePaymentStatus()
    {
        return schedulePaymentStatus;
    }
    
    public void setSchedulePaymentStatus(Integer schedulePaymentStatus)
    {
        this.schedulePaymentStatus = schedulePaymentStatus;
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
    
    @Column(name = "RECIPIENT_NAME")
    public String getRecipientName()
    {
        return recipientName;
    }
    
    public void setRecipientName(String recipientName)
    {
        this.recipientName = recipientName;
    }
    
    @Column(name = "RECIPIENT_PHONE")
    public String getRecipientPhone()
    {
        return recipientPhone;
    }
    
    public void setRecipientPhone(String recipientPhone)
    {
        this.recipientPhone = recipientPhone;
    }
    
    @Column(name = "RECIPIENT_EMAIL")
    public String getRecipientEmail()
    {
        return recipientEmail;
    }
    
    public void setRecipientEmail(String recipientEmail)
    {
        this.recipientEmail = recipientEmail;
    }
    
    @Column(name = "RECIPIENT_ADDRESS")
    public String getRecipientAddress()
    {
        return recipientAddress;
    }
    
    public void setRecipientAddress(String recipientAddress)
    {
        this.recipientAddress = recipientAddress;
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
    
    @Column(name = "INVOICE_TITLE")
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    
    @Column(name = "CREATOR_ID")
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    @Column(name = "CREATOR_NAME")
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    @Column(name = "CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
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
    
    @OneToMany(mappedBy = "order", cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<OrderExaminee> getOrderExamineeList()
    {
        return orderExamineeList;
    }
    
    public void setOrderExamineeList(List<OrderExaminee> orderExamineeList)
    {
        this.orderExamineeList = orderExamineeList;
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
    
    @Column(name = "OWNER_ID")
    public String getOwnerId()
    {
        return ownerId;
    }
    
    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
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
    
    @Column(name = "SUBMITTER_ID")
    public String getSubmitterId()
    {
        return submitterId;
    }
    
    public void setSubmitterId(String submitterId)
    {
        this.submitterId = submitterId;
    }
    
    @Column(name = "SUBMITTER_NAME")
    public String getSubmitterName()
    {
        return submitterName;
    }
    
    public void setSubmitterName(String submitterName)
    {
        this.submitterName = submitterName;
    }
    
    @Column(name = "SUBMIT_SOURCE")
    public Integer getSubmitSource()
    {
        return submitSource;
    }
    
    public void setSubmitSource(Integer submitSource)
    {
        this.submitSource = submitSource;
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
    
    @Column(name = "UPDATOR_NAME")
    public String getUpdatorName()
    {
        return updatorName;
    }
    
    public void setUpdatorName(String updatorName)
    {
        this.updatorName = updatorName;
    }
    
    @Column(name = "UPDATOR_ID")
    public String getUpdatorId()
    {
        return updatorId;
    }
    
    public void setUpdatorId(String updatorId)
    {
        this.updatorId = updatorId;
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
    
    @Transient
    public List<OrderSampleGroup> getOrderSampleGroup()
    {
        return orderSampleGroup;
    }
    
    public void setOrderSampleGroup(List<OrderSampleGroup> orderSampleGroup)
    {
        this.orderSampleGroup = orderSampleGroup;
    }
    
    /*   @OneToMany(mappedBy = "orderId", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
       @NotFound(action = NotFoundAction.IGNORE)
       public List<OrderSampleView> getSampleView()
       {
           return sampleView;
       }
       
       public void setSampleView(List<OrderSampleView> sampleView)
       {
           this.sampleView = sampleView;
       }*/
    
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
    
    @Column(name = "PAY_TYPE")
    public String getPayType()
    {
        return payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
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
    
    @Column(name = "RECEIVED_SAMPLE_STATUS")
    public Integer getReceivedSampleStatus()
    {
        return receivedSampleStatus;
    }
    
    public void setReceivedSampleStatus(Integer receivedSampleStatus)
    {
        this.receivedSampleStatus = receivedSampleStatus;
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
    
    @Column(name = "DRAFT_FLAG")
    public Integer getDraft()
    {
        return draft;
    }
    
    public void setDraft(Integer draft)
    {
        this.draft = draft;
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
    
    @Column(name = "FULL_PAID_FLAG")
    public Integer getFullPaid()
    {
        return fullPaid;
    }
    
    public void setFullPaid(Integer fullPaid)
    {
        this.fullPaid = fullPaid;
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
    
    @Transient
    public Date getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
}
