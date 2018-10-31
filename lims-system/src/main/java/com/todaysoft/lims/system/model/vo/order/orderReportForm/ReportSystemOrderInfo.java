package com.todaysoft.lims.system.model.vo.order.orderReportForm;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.UuidKeyEntity;

public class ReportSystemOrderInfo extends UuidKeyEntity
{
    private static final long serialVersionUID = 3140637188531806673L;
    
    private String taskId;
    private String orderId;
    private String orderCode;
    private String orderType;
    private String customer;
    private String companyName;
    private String createName;
    private String testingStatus;
    private String paymentStatus;
    private String delayStatus;
    private String orderAmount;
    private String doctorAssist;
    private String contractId;
    private String contractName;
    private String samplingAmount;
    private Date createTime;
    private Date startTime;
    private String examineeName;
    private String nation;
    private String sex;
    private String birthday;
    private String ageSnapshot;
    private String origin;
    private String contactName;
    private String contactPhome;
    private String contactEmail;
    private String recordNo;
    private String disease;
    private String gene;
    private String phenotype;
    private String medicalHistory;
    private String familyMedicalHistory;
    private String clinicalRecommend;
    private String recipientName;
    private String recipientPhone;
    private String recipientAddress;
    private String invoiceTitle;
    private String payType;
    private String settlementType;
    private String payStatus;
    private String reduceAmount;
    private String discountAmount;
    private String reduceReason;
    private String reduceStatus;
    private Date reduceCheckTime;
    private String amount;
    private String incomingAmount;
    private Date paymentTime;
    private Date paymentCheckTime;
    private List<ReportSystemProductInfo> productList = Lists.newArrayList();
    private List<ReportSystemSampleInfo> sampleList = Lists.newArrayList();
    private List<ReportSystemInvoiceInfo> invoiceList = Lists.newArrayList();
    
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
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
    
    public String getOrderType()
    {
        return orderType;
    }
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public String getCustomer()
    {
        return customer;
    }
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }
    
    public String getCompanyName()
    {
        return companyName;
    }
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    
    public String getCreateName()
    {
        return createName;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }
    
    public String getTestingStatus()
    {
        return testingStatus;
    }
    public void setTestingStatus(String testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    
    public String getPaymentStatus()
    {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }
    
    public String getDelayStatus()
    {
        return delayStatus;
    }
    public void setDelayStatus(String delayStatus)
    {
        this.delayStatus = delayStatus;
    }
    
    public String getOrderAmount()
    {
        return orderAmount;
    }
    public void setOrderAmount(String orderAmount)
    {
        this.orderAmount = orderAmount;
    }
    
    public String getDoctorAssist()
    {
        return doctorAssist;
    }
    public void setDoctorAssist(String doctorAssist)
    {
        this.doctorAssist = doctorAssist;
    }
    
    public String getContractId()
    {
        return contractId;
    }
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    public String getContractName()
    {
        return contractName;
    }
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }
    
    public String getSamplingAmount()
    {
        return samplingAmount;
    }
    public void setSamplingAmount(String samplingAmount)
    {
        this.samplingAmount = samplingAmount;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Date getStartTime()
    {
        return startTime;
    }
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public String getNation()
    {
        return nation;
    }
    public void setNation(String nation)
    {
        this.nation = nation;
    }
    
    public String getSex()
    {
        return sex;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    public String getBirthday()
    {
        return birthday;
    }
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }
    
    public String getAgeSnapshot()
    {
        return ageSnapshot;
    }
    public void setAgeSnapshot(String ageSnapshot)
    {
        this.ageSnapshot = ageSnapshot;
    }
    
    public String getOrigin()
    {
        return origin;
    }
    public void setOrigin(String origin)
    {
        this.origin = origin;
    }
    
    public String getContactName()
    {
        return contactName;
    }
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }
    
    public String getContactPhome()
    {
        return contactPhome;
    }
    public void setContactPhome(String contactPhome)
    {
        this.contactPhome = contactPhome;
    }
    
    public String getContactEmail()
    {
        return contactEmail;
    }
    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }
    
    public String getRecordNo()
    {
        return recordNo;
    }
    public void setRecordNo(String recordNo)
    {
        this.recordNo = recordNo;
    }
    
    public String getDisease()
    {
        return disease;
    }
    public void setDisease(String disease)
    {
        this.disease = disease;
    }
    
    public String getGene()
    {
        return gene;
    }
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
    public String getPhenotype()
    {
        return phenotype;
    }
    public void setPhenotype(String phenotype)
    {
        this.phenotype = phenotype;
    }
    
    public String getMedicalHistory()
    {
        return medicalHistory;
    }
    public void setMedicalHistory(String medicalHistory)
    {
        this.medicalHistory = medicalHistory;
    }
    
    public String getFamilyMedicalHistory()
    {
        return familyMedicalHistory;
    }
    public void setFamilyMedicalHistory(String familyMedicalHistory)
    {
        this.familyMedicalHistory = familyMedicalHistory;
    }
    
    public String getClinicalRecommend()
    {
        return clinicalRecommend;
    }
    public void setClinicalRecommend(String clinicalRecommend)
    {
        this.clinicalRecommend = clinicalRecommend;
    }
    
    public String getRecipientName()
    {
        return recipientName;
    }
    public void setRecipientName(String recipientName)
    {
        this.recipientName = recipientName;
    }
    
    public String getRecipientPhone()
    {
        return recipientPhone;
    }
    public void setRecipientPhone(String recipientPhone)
    {
        this.recipientPhone = recipientPhone;
    }
    
    public String getRecipientAddress()
    {
        return recipientAddress;
    }
    public void setRecipientAddress(String recipientAddress)
    {
        this.recipientAddress = recipientAddress;
    }
    
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    
    public String getPayType()
    {
        return payType;
    }
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    public String getSettlementType()
    {
        return settlementType;
    }
    public void setSettlementType(String settlementType)
    {
        this.settlementType = settlementType;
    }
    
    public String getPayStatus()
    {
        return payStatus;
    }
    public void setPayStatus(String payStatus)
    {
        this.payStatus = payStatus;
    }
    
    public String getReduceAmount()
    {
        return reduceAmount;
    }
    public void setReduceAmount(String reduceAmount)
    {
        this.reduceAmount = reduceAmount;
    }
    
    public String getDiscountAmount()
    {
        return discountAmount;
    }
    public void setDiscountAmount(String discountAmount)
    {
        this.discountAmount = discountAmount;
    }
    
    public String getReduceReason()
    {
        return reduceReason;
    }
    public void setReduceReason(String reduceReason)
    {
        this.reduceReason = reduceReason;
    }
    
    public String getReduceStatus()
    {
        return reduceStatus;
    }
    public void setReduceStatus(String reduceStatus)
    {
        this.reduceStatus = reduceStatus;
    }
    
    public Date getReduceCheckTime()
    {
        return reduceCheckTime;
    }
    public void setReduceCheckTime(Date reduceCheckTime)
    {
        this.reduceCheckTime = reduceCheckTime;
    }
    
    public String getAmount()
    {
        return amount;
    }
    public void setAmount(String amount)
    {
        this.amount = amount;
    }
    
    public String getIncomingAmount()
    {
        return incomingAmount;
    }
    public void setIncomingAmount(String incomingAmount)
    {
        this.incomingAmount = incomingAmount;
    }
    
    public Date getPaymentTime()
    {
        return paymentTime;
    }
    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
    }
    
    public Date getPaymentCheckTime()
    {
        return paymentCheckTime;
    }
    public void setPaymentCheckTime(Date paymentCheckTime)
    {
        this.paymentCheckTime = paymentCheckTime;
    }
    
    public List<ReportSystemProductInfo> getProductList()
    {
        return productList;
    }
    public void setProductList(List<ReportSystemProductInfo> productList)
    {
        this.productList = productList;
    }
    
    public List<ReportSystemSampleInfo> getSampleList()
    {
        return sampleList;
    }
    public void setSampleList(List<ReportSystemSampleInfo> sampleList)
    {
        this.sampleList = sampleList;
    }
    
    public List<ReportSystemInvoiceInfo> getInvoiceList()
    {
        return invoiceList;
    }
    public void setInvoiceList(List<ReportSystemInvoiceInfo> invoiceList)
    {
        this.invoiceList = invoiceList;
    }
    
}
