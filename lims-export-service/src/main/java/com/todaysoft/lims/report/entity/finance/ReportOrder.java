package com.todaysoft.lims.report.entity.finance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "REPORT_ORDER")
public class ReportOrder extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private String taskId;
    
    private String code; //订单编号
    
    private String orderType;//订单类型（临检、科研、健康）
    
    private Integer testingStatus;
    
    private String examineeName;
    
    private String ownerName;
    
    private String creatorName;//业务员
    
    private String companyName;//业务员
    
    private Date submitTime;
    
    private Integer needPay;
    
    private Integer reduceAmount; //减免金额
    
    private Integer incomingAmount;
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    @Column(name = "EXAMINEE_NAME")
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    @Column(name = "OWNER_NAME")
    public String getOwnerName()
    {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
    
    @Column(name = "COMPANY_NAME")
    public String getCompanyName()
    {
        return companyName;
    }
    
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    
    @Column(name = "NEED_PAY")
    public Integer getNeedPay()
    {
        return needPay;
    }
    
    public void setNeedPay(Integer needPay)
    {
        this.needPay = needPay;
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
    
    public ReportOrder()
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
    
    @Column(name = "CREATOR_NAME")
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
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
    
    @Column(name = "REDUCE_AMOUNT")
    public Integer getReduceAmount()
    {
        return reduceAmount;
    }
    
    public void setReduceAmount(Integer reduceAmount)
    {
        this.reduceAmount = reduceAmount;
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
}
