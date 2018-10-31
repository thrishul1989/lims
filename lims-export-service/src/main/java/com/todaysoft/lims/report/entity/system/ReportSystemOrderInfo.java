package com.todaysoft.lims.report.entity.system;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.google.common.collect.Lists;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "REPORT_SYSTEM_ORDER_INFO")
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
    
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "ORDER_CODE")
    public String getOrderCode()
    {
        return orderCode;
    }
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
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
    
    @Column(name = "CUSTOMER")
    public String getCustomer()
    {
        return customer;
    }
    public void setCustomer(String customer)
    {
        this.customer = customer;
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
    
    @Column(name = "CREATE_NAME")
    public String getCreateName()
    {
        return createName;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
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
    
    @Column(name = "PAYMENT_STATUS")
    public String getPaymentStatus()
    {
        return paymentStatus;
    }
    public void setPaymentStatus(String paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }
    
    @Column(name = "DELAY_STATUS")
    public String getDelayStatus()
    {
        return delayStatus;
    }
    public void setDelayStatus(String delayStatus)
    {
        this.delayStatus = delayStatus;
    }
    
    @Column(name = "ORDER_AMOUNT")
    public String getOrderAmount()
    {
        return orderAmount;
    }
    public void setOrderAmount(String orderAmount)
    {
        this.orderAmount = orderAmount;
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
    
    @Column(name = "CONTRACT_ID")
    public String getContractId()
    {
        return contractId;
    }
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    @Column(name = "CONTRACT_NAME")
    public String getContractName()
    {
        return contractName;
    }
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }
    
    @Column(name = "SAMPLING_AMOUNT")
    public String getSamplingAmount()
    {
        return samplingAmount;
    }
    public void setSamplingAmount(String samplingAmount)
    {
        this.samplingAmount = samplingAmount;
    }
    
    @Column(name = "CREATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime()
    {
        return createTime;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "START_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartTime()
    {
        return startTime;
    }
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
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
    
    @Column(name = "NATION")
    public String getNation()
    {
        return nation;
    }
    public void setNation(String nation)
    {
        this.nation = nation;
    }
    
    @Column(name = "SEX")
    public String getSex()
    {
        return sex;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    @Column(name = "BIRTHDAY")
    public String getBirthday()
    {
        return birthday;
    }
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }
    
    @Column(name = "AGE_SNAPSHOT")
    public String getAgeSnapshot()
    {
        return ageSnapshot;
    }
    public void setAgeSnapshot(String ageSnapshot)
    {
        this.ageSnapshot = ageSnapshot;
    }
    
    @Column(name = "ORIGIN")
    public String getOrigin()
    {
        return origin;
    }
    public void setOrigin(String origin)
    {
        this.origin = origin;
    }
    
    @Column(name = "CONTACT_NAME")
    public String getContactName()
    {
        return contactName;
    }
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }
    
    @Column(name = "CONTACT_PHONE")
    public String getContactPhome()
    {
        return contactPhome;
    }
    public void setContactPhome(String contactPhome)
    {
        this.contactPhome = contactPhome;
    }
    
    @Column(name = "CONTACT_EMAIL")
    public String getContactEmail()
    {
        return contactEmail;
    }
    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }
    
    @Column(name = "RECORD_NO")
    public String getRecordNo()
    {
        return recordNo;
    }
    public void setRecordNo(String recordNo)
    {
        this.recordNo = recordNo;
    }
    
    @Column(name = "DISEASE")
    public String getDisease()
    {
        return disease;
    }
    public void setDisease(String disease)
    {
        this.disease = disease;
    }
    
    @Column(name = "GENE")
    public String getGene()
    {
        return gene;
    }
    public void setGene(String gene)
    {
        this.gene = gene;
    }
    
    @Column(name = "PHENOTYPE")
    public String getPhenotype()
    {
        return phenotype;
    }
    public void setPhenotype(String phenotype)
    {
        this.phenotype = phenotype;
    }
    
    @Column(name = "MEDICAL_HISTORY")
    public String getMedicalHistory()
    {
        return medicalHistory;
    }
    public void setMedicalHistory(String medicalHistory)
    {
        this.medicalHistory = medicalHistory;
    }
    
    @Column(name = "FAMILY_MEDICAL_HISTORY")
    public String getFamilyMedicalHistory()
    {
        return familyMedicalHistory;
    }
    public void setFamilyMedicalHistory(String familyMedicalHistory)
    {
        this.familyMedicalHistory = familyMedicalHistory;
    }
    
    @Column(name = "CLINICAL_RECOMMEND")
    public String getClinicalRecommend()
    {
        return clinicalRecommend;
    }
    public void setClinicalRecommend(String clinicalRecommend)
    {
        this.clinicalRecommend = clinicalRecommend;
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
    
    @Column(name = "INVOICE_TITLE")
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
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
    
    @Column(name = "SETTLEMENT_TYPE")
    public String getSettlementType()
    {
        return settlementType;
    }
    public void setSettlementType(String settlementType)
    {
        this.settlementType = settlementType;
    }
    
    @Column(name = "PAY_STATUS")
    public String getPayStatus()
    {
        return payStatus;
    }
    public void setPayStatus(String payStatus)
    {
        this.payStatus = payStatus;
    }
    
    @Column(name = "REDUCE_AMOUNT")
    public String getReduceAmount()
    {
        return reduceAmount;
    }
    public void setReduceAmount(String reduceAmount)
    {
        this.reduceAmount = reduceAmount;
    }
    
    @Column(name = "DISCOUNT_AMOUNT")
    public String getDiscountAmount()
    {
        return discountAmount;
    }
    public void setDiscountAmount(String discountAmount)
    {
        this.discountAmount = discountAmount;
    }
    
    @Column(name = "REDUCE_REASON")
    public String getReduceReason()
    {
        return reduceReason;
    }
    public void setReduceReason(String reduceReason)
    {
        this.reduceReason = reduceReason;
    }
    
    @Column(name = "REDUCE_STATUS")
    public String getReduceStatus()
    {
        return reduceStatus;
    }
    public void setReduceStatus(String reduceStatus)
    {
        this.reduceStatus = reduceStatus;
    }
    
    @Column(name = "REDUCE_CHECK_TIME")
    public Date getReduceCheckTime()
    {
        return reduceCheckTime;
    }
    public void setReduceCheckTime(Date reduceCheckTime)
    {
        this.reduceCheckTime = reduceCheckTime;
    }
    
    @Column(name = "AMOUNT")
    public String getAmount()
    {
        return amount;
    }
    public void setAmount(String amount)
    {
        this.amount = amount;
    }
    
    @Column(name = "INCOMING_AMOUNT")
    public String getIncomingAmount()
    {
        return incomingAmount;
    }
    public void setIncomingAmount(String incomingAmount)
    {
        this.incomingAmount = incomingAmount;
    }
    
    @Column(name = "PAYMENT_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPaymentTime()
    {
        return paymentTime;
    }
    public void setPaymentTime(Date paymentTime)
    {
        this.paymentTime = paymentTime;
    }
    
    @Column(name = "PAYMENT_CHECK_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPaymentCheckTime()
    {
        return paymentCheckTime;
    }
    public void setPaymentCheckTime(Date paymentCheckTime)
    {
        this.paymentCheckTime = paymentCheckTime;
    }
    
    @OneToMany(mappedBy = "orderInfo", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<ReportSystemProductInfo> getProductList()
    {
        return productList;
    }
    public void setProductList(List<ReportSystemProductInfo> productList)
    {
        this.productList = productList;
    }
    
    @OneToMany(mappedBy = "orderInfo",  fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<ReportSystemSampleInfo> getSampleList()
    {
        return sampleList;
    }
    public void setSampleList(List<ReportSystemSampleInfo> sampleList)
    {
        this.sampleList = sampleList;
    }
    
    @OneToMany(mappedBy = "orderInfo", fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<ReportSystemInvoiceInfo> getInvoiceList()
    {
        return invoiceList;
    }
    public void setInvoiceList(List<ReportSystemInvoiceInfo> invoiceList)
    {
        this.invoiceList = invoiceList;
    }
    
}
