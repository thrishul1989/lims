package com.todaysoft.lims.report.entity.finance;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "REPORT_FINANCIAL_ORDER")
public class ReportFinancialOrder extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private String orderId;
    
    private String taskId;
    
    private String settlementType;
    
    private String code; //订单编号
    
    private Date submitTime;
    
    private String examineeName;
    
    private String ownerName;
    
    private String productNames;
    
    private String companyName;//公司
    
    private String creatorName;//业务员
    
    private Integer samplingAmount; //增加收样费用
    
    private Date checkTime;
    
    private Integer orderAmount;
    
    private Integer needPay;
    
    private Integer reduceAmount; //减免金额
    
    private Integer incomingAmount;
    
    private String payType;
    
    private String posNo;
    
    private String paymenter;
    
    private String invoiceApplyType;
    
    private String invoiceTitle;
    
    private String invoiceContent;
    
    private List<ReportFinancialOrderInvoice> reportFinancialOrderInvoice;
    
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
    
    public ReportFinancialOrder()
    {
        super();
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
    
    @Column(name = "SETTLEMENT_TYPE")
    public String getSettlementType()
    {
        return settlementType;
    }
    
    public void setSettlementType(String settlementType)
    {
        this.settlementType = settlementType;
    }
    
    @Column(name = "PRODUCT_NAMES")
    public String getProductNames()
    {
        return productNames;
    }
    
    public void setProductNames(String productNames)
    {
        this.productNames = productNames;
    }
    
    @Column(name = "SAMPLING_AMOUNT")
    public Integer getSamplingAmount()
    {
        return samplingAmount;
    }
    
    public void setSamplingAmount(Integer samplingAmount)
    {
        this.samplingAmount = samplingAmount;
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
    
    @Column(name = "ORDER_AMOUNT")
    public Integer getOrderAmount()
    {
        return orderAmount;
    }
    
    public void setOrderAmount(Integer orderAmount)
    {
        this.orderAmount = orderAmount;
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
    
    @Column(name = "POS_NO")
    public String getPosNo()
    {
        return posNo;
    }
    
    public void setPosNo(String posNo)
    {
        this.posNo = posNo;
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
    
    @Column(name = "INVOICE_APPLY_TYPE")
    public String getInvoiceApplyType()
    {
        return invoiceApplyType;
    }
    
    public void setInvoiceApplyType(String invoiceApplyType)
    {
        this.invoiceApplyType = invoiceApplyType;
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
    
    @Column(name = "INVOICE_CONTENT")
    public String getInvoiceContent()
    {
        return invoiceContent;
    }
    
    public void setInvoiceContent(String invoiceContent)
    {
        this.invoiceContent = invoiceContent;
    }
    
    @Transient
    public List<ReportFinancialOrderInvoice> getReportFinancialOrderInvoice()
    {
        return reportFinancialOrderInvoice;
    }
    
    public void setReportFinancialOrderInvoice(List<ReportFinancialOrderInvoice> reportFinancialOrderInvoice)
    {
        this.reportFinancialOrderInvoice = reportFinancialOrderInvoice;
    }
    
}
