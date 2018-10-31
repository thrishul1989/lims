package com.todaysoft.lims.report.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "LIMS_ORDER")
public class Order implements Serializable
{
    private static final long serialVersionUID = -1912802180977673553L;
    
    private String id;
    
    private String code;
    
    private TestingType type;
    
    private Integer status;
    
    private Integer sheduleStatus;
    
    private String recipientName;//收件人姓名
    
    private String recipientPhone;//收件人电话
    
    private String recipientAddress;//收件人地址
    
    private Integer schedulePaymentStatus; //合同订单检测状态
    
    private Integer receivedSampleStatus;//样本是否入库完成 0-未入库1-已入库
    
    private String creatorName;//业务员
    
    private String remark;//启动异常原因
    
    private List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();
    
    private List<OrderExaminee> orderExamineeList = new ArrayList<OrderExaminee>(); //受检人
    
    private List<OrderPrimarySample> orderPrimarySample = new ArrayList<OrderPrimarySample>(); //主样本
    
    private Contract contract; //归属合同
    
    private Date createTime;
    
    private Date submitTime;
    
    private String doctorAssist;//客户参与（X/X）
    
    private String ownerId; //订单所属客户
    
    private Integer issueInvoice;//状态：1-待开票 2-可开票 3-已开票 4-已寄送
    
    private Integer invoiceApplyType;//开票类型 ：1默认开票 2提前开票
    
    private Integer testingStatus;
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;

    private String projectManager;

    @Column(name = "PROJECT_MANAGER")
    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
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
    
    @Column(name = "CREATOR_NAME")
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
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
    
    @Column(name = "RECIPIENT_ADDRESS")
    public String getRecipientAddress()
    {
        return recipientAddress;
    }
    
    public void setRecipientAddress(String recipientAddress)
    {
        this.recipientAddress = recipientAddress;
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
    
    @ManyToOne
    @JoinColumn(name = "ORDER_TYPE")
    public TestingType getType()
    {
        return type;
    }
    
    public void setType(TestingType type)
    {
        this.type = type;
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
    
    @Column(name = "SCHEDULE_PAYMENT_STATUS")
    public Integer getSchedulePaymentStatus()
    {
        return schedulePaymentStatus;
    }
    
    public void setSchedulePaymentStatus(Integer schedulePaymentStatus)
    {
        this.schedulePaymentStatus = schedulePaymentStatus;
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
    
    @Column(name = "STATUS")
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    @OneToMany(mappedBy = "order", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    public List<OrderProduct> getOrderProductList()
    {
        return orderProductList;
    }
    
    public void setOrderProductList(List<OrderProduct> orderProductList)
    {
        this.orderProductList = orderProductList;
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
    
    @Column(name = "CREATE_TIME")
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
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
    
    @Column(name = "OWNER_ID")
    public String getOwnerId()
    {
        return ownerId;
    }
    
    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }
    
    @OneToMany(mappedBy = "order", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<OrderPrimarySample> getOrderPrimarySample()
    {
        return orderPrimarySample;
    }
    
    public void setOrderPrimarySample(List<OrderPrimarySample> orderPrimarySample)
    {
        this.orderPrimarySample = orderPrimarySample;
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
    
    @Column(name="IF_URGENT")
    public Integer getIfUrgent()
    {
        return ifUrgent;
    }

    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }
    
    @Column(name="URGENT_UPDATE_TIME")
    public Date getUrgentUpdateTime()
    {
        return urgentUpdateTime;
    }

    public void setUrgentUpdateTime(Date urgentUpdateTime)
    {
        this.urgentUpdateTime = urgentUpdateTime;
    }

    @Column(name="URGENT_NAME")
    public String getUrgentName()
    {
        return urgentName;
    }

    public void setUrgentName(String urgentName)
    {
        this.urgentName = urgentName;
    }
}
