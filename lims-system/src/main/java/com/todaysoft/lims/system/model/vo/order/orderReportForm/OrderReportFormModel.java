package com.todaysoft.lims.system.model.vo.order.orderReportForm;

import java.util.Date;

public class OrderReportFormModel
{
    //-----------------基本信息------
    private String orderCode;//订单编号
    private String orderType;//订单类型
    private String customer;//客户
    private String companyName;//送检单位
    private String createName;//业务员
    private String testingStatus;//订单主状态
    private String paymentStatus;//订单支付状态
    private String delayStatus;//订单延迟状态
    private double orderAmount;//订单金额
    private String doctorAssist;//客户参与
    private String contractName;//所属合同
    private double samplingAmount;//采样费用
    private Date createTime;//创建时间
    private Date submitTime;//启动时间
    //---------------------检测产品----
    private String productName;//产品名称
    private String productRefundStatus;//产品退款状态
    private String productReportStatus;//报告状态
    private String productStatus;//产品状态
    private Date productReportDate;//出报告日期
    private Date sendDate;//寄送日期
    //-------------受检人信息----------
    private String examineeName;//名称
    private String nation;//民族
    private String sex;//性别
    private Date birthday;//出生日期
    private String age;//申请检测时年龄
    private String origin;//籍贯
    private String contactName;//联系人
    private String contactPhone;//联系电话
    private String contactEmail;//联系邮箱
    private String recordNo;//病例号
    //--------疾病信息---------
    private String disease;//临床诊断
    private String gene;//重点关注基因
    private String phenotype;//临床表型
    private String medicalHistory;//病史摘要
    private String familyMedicalHistory;//家族史摘要
    private String clinicalRecommend;//临床推荐理由
    //---------样本信息------------
    private String primarySampleCode;//样本编号
    private String primarySampleType;//样本类型ID
    private String primarySampleSize;//样本量
    private Date primarySamplingDate;//采样时间
    private String SreingprimarySampleStatus;//样本状态
    private Date primarySampleAcceptDate;//接收日期
    //--------家属样本信息-------------
    private String subsidiarySampleCode;//样本编号
    private String subsidiarySampleType;//样本类型ID
    private String subsidiarySampleSize;//样本量
    private String subsidiarySamplingDate;//采样时间
    private String ownerName;//样本提供者姓名
    private String ownerAge;//样本提供者年龄
    private String familyRelation;//家属关系
    private String purpose;//样本用途
    private String ownerPhenotype;//家属症状
    private String subsidiarySampleStatus;//样本状态
    private Date subsidiarySampleAcceptDate;//接收日期
    //---------收件信息-----------
    private String recipientName;//收件人姓名
    private String recipientPhone;//收件人电话
    private String recipientAddress;//收件人地址
    //--------费用信息----------
    private String invoiceTitle;//发票抬头
    private String payType;//付款方式
    private String payStatus;//付款状态
    private double reduceAmount;//减免金额
    private double discountAmount;//抵用券
    private String reduceReason;//减免原因
    private String reduceStatus;//减免状态
    private double amount;//应付款
    private double incomingAmount;//实付款
    private Date paymentTime;//付款时间
    private Date checkTime;//付款确认时间
    //-----------退款信息-----------
    private String refundProductCode;//产品编号
    private String refundProductName;//产品名称
    private double refundProductAmount;//产品价格
    private double refundReplyAmount;//退款金额
    private String refundReason;//理由
    private Date refundApplyTime;//申请时间
    private String refundStatus;//状态
    //-----------开票信息--------------
    private String drawerNo;//开票单号
    private String institution;//开票单位
    private double invoiceAmount;//开票金额
    private String drawerName;//开票人
    private Date invoiceTime;//开票时间
    private String receiverName;//领取人
    //---------报告信息-----------
    private String reportNo;//报告编号
    private String reportName;//报告名称
    private String reportSampleName;//样本编号
    private String reportProductName;//检测产品
    private Date reportDate;//出报告时间
    private String reportStatus;//状态
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
    public double getOrderAmount()
    {
        return orderAmount;
    }
    public void setOrderAmount(double orderAmount)
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
    public String getContractName()
    {
        return contractName;
    }
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }
    public double getSamplingAmount()
    {
        return samplingAmount;
    }
    public void setSamplingAmount(double samplingAmount)
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
    public Date getSubmitTime()
    {
        return submitTime;
    }
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
    public String getProductName()
    {
        return productName;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    public String getProductRefundStatus()
    {
        return productRefundStatus;
    }
    public void setProductRefundStatus(String productRefundStatus)
    {
        this.productRefundStatus = productRefundStatus;
    }
    public String getProductReportStatus()
    {
        return productReportStatus;
    }
    public void setProductReportStatus(String productReportStatus)
    {
        this.productReportStatus = productReportStatus;
    }
    public String getProductStatus()
    {
        return productStatus;
    }
    public void setProductStatus(String productStatus)
    {
        this.productStatus = productStatus;
    }
    public Date getProductReportDate()
    {
        return productReportDate;
    }
    public void setProductReportDate(Date productReportDate)
    {
        this.productReportDate = productReportDate;
    }
    public Date getSendDate()
    {
        return sendDate;
    }
    public void setSendDate(Date sendDate)
    {
        this.sendDate = sendDate;
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
    public Date getBirthday()
    {
        return birthday;
    }
    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }
    public String getAge()
    {
        return age;
    }
    public void setAge(String age)
    {
        this.age = age;
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
    public String getContactPhone()
    {
        return contactPhone;
    }
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
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
    public String getPrimarySampleCode()
    {
        return primarySampleCode;
    }
    public void setPrimarySampleCode(String primarySampleCode)
    {
        this.primarySampleCode = primarySampleCode;
    }
    public String getPrimarySampleType()
    {
        return primarySampleType;
    }
    public void setPrimarySampleType(String primarySampleType)
    {
        this.primarySampleType = primarySampleType;
    }
    public String getPrimarySampleSize()
    {
        return primarySampleSize;
    }
    public void setPrimarySampleSize(String primarySampleSize)
    {
        this.primarySampleSize = primarySampleSize;
    }
    public Date getPrimarySamplingDate()
    {
        return primarySamplingDate;
    }
    public void setPrimarySamplingDate(Date primarySamplingDate)
    {
        this.primarySamplingDate = primarySamplingDate;
    }
    public String getSreingprimarySampleStatus()
    {
        return SreingprimarySampleStatus;
    }
    public void setSreingprimarySampleStatus(String sreingprimarySampleStatus)
    {
        SreingprimarySampleStatus = sreingprimarySampleStatus;
    }
    public Date getPrimarySampleAcceptDate()
    {
        return primarySampleAcceptDate;
    }
    public void setPrimarySampleAcceptDate(Date primarySampleAcceptDate)
    {
        this.primarySampleAcceptDate = primarySampleAcceptDate;
    }
    public String getSubsidiarySampleCode()
    {
        return subsidiarySampleCode;
    }
    public void setSubsidiarySampleCode(String subsidiarySampleCode)
    {
        this.subsidiarySampleCode = subsidiarySampleCode;
    }
    public String getSubsidiarySampleType()
    {
        return subsidiarySampleType;
    }
    public void setSubsidiarySampleType(String subsidiarySampleType)
    {
        this.subsidiarySampleType = subsidiarySampleType;
    }
    public String getSubsidiarySampleSize()
    {
        return subsidiarySampleSize;
    }
    public void setSubsidiarySampleSize(String subsidiarySampleSize)
    {
        this.subsidiarySampleSize = subsidiarySampleSize;
    }
    public String getSubsidiarySamplingDate()
    {
        return subsidiarySamplingDate;
    }
    public void setSubsidiarySamplingDate(String subsidiarySamplingDate)
    {
        this.subsidiarySamplingDate = subsidiarySamplingDate;
    }
    public String getOwnerName()
    {
        return ownerName;
    }
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
    public String getOwnerAge()
    {
        return ownerAge;
    }
    public void setOwnerAge(String ownerAge)
    {
        this.ownerAge = ownerAge;
    }
    public String getFamilyRelation()
    {
        return familyRelation;
    }
    public void setFamilyRelation(String familyRelation)
    {
        this.familyRelation = familyRelation;
    }
    public String getPurpose()
    {
        return purpose;
    }
    public void setPurpose(String purpose)
    {
        this.purpose = purpose;
    }
    public String getOwnerPhenotype()
    {
        return ownerPhenotype;
    }
    public void setOwnerPhenotype(String ownerPhenotype)
    {
        this.ownerPhenotype = ownerPhenotype;
    }
    public String getSubsidiarySampleStatus()
    {
        return subsidiarySampleStatus;
    }
    public void setSubsidiarySampleStatus(String subsidiarySampleStatus)
    {
        this.subsidiarySampleStatus = subsidiarySampleStatus;
    }
    public Date getSubsidiarySampleAcceptDate()
    {
        return subsidiarySampleAcceptDate;
    }
    public void setSubsidiarySampleAcceptDate(Date subsidiarySampleAcceptDate)
    {
        this.subsidiarySampleAcceptDate = subsidiarySampleAcceptDate;
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
    public String getPayStatus()
    {
        return payStatus;
    }
    public void setPayStatus(String payStatus)
    {
        this.payStatus = payStatus;
    }
    public double getReduceAmount()
    {
        return reduceAmount;
    }
    public void setReduceAmount(double reduceAmount)
    {
        this.reduceAmount = reduceAmount;
    }
    public double getDiscountAmount()
    {
        return discountAmount;
    }
    public void setDiscountAmount(double discountAmount)
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
    public double getAmount()
    {
        return amount;
    }
    public void setAmount(double amount)
    {
        this.amount = amount;
    }
    public double getIncomingAmount()
    {
        return incomingAmount;
    }
    public void setIncomingAmount(double incomingAmount)
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
    public Date getCheckTime()
    {
        return checkTime;
    }
    public void setCheckTime(Date checkTime)
    {
        this.checkTime = checkTime;
    }
    public String getRefundProductCode()
    {
        return refundProductCode;
    }
    public void setRefundProductCode(String refundProductCode)
    {
        this.refundProductCode = refundProductCode;
    }
    public String getRefundProductName()
    {
        return refundProductName;
    }
    public void setRefundProductName(String refundProductName)
    {
        this.refundProductName = refundProductName;
    }
    public double getRefundProductAmount()
    {
        return refundProductAmount;
    }
    public void setRefundProductAmount(double refundProductAmount)
    {
        this.refundProductAmount = refundProductAmount;
    }
    public double getRefundReplyAmount()
    {
        return refundReplyAmount;
    }
    public void setRefundReplyAmount(double refundReplyAmount)
    {
        this.refundReplyAmount = refundReplyAmount;
    }
    public String getRefundReason()
    {
        return refundReason;
    }
    public void setRefundReason(String refundReason)
    {
        this.refundReason = refundReason;
    }
    public Date getRefundApplyTime()
    {
        return refundApplyTime;
    }
    public void setRefundApplyTime(Date refundApplyTime)
    {
        this.refundApplyTime = refundApplyTime;
    }
    public String getRefundStatus()
    {
        return refundStatus;
    }
    public void setRefundStatus(String refundStatus)
    {
        this.refundStatus = refundStatus;
    }
    public String getDrawerNo()
    {
        return drawerNo;
    }
    public void setDrawerNo(String drawerNo)
    {
        this.drawerNo = drawerNo;
    }
    public String getInstitution()
    {
        return institution;
    }
    public void setInstitution(String institution)
    {
        this.institution = institution;
    }
    public double getInvoiceAmount()
    {
        return invoiceAmount;
    }
    public void setInvoiceAmount(double invoiceAmount)
    {
        this.invoiceAmount = invoiceAmount;
    }
    public String getDrawerName()
    {
        return drawerName;
    }
    public void setDrawerName(String drawerName)
    {
        this.drawerName = drawerName;
    }
    public Date getInvoiceTime()
    {
        return invoiceTime;
    }
    public void setInvoiceTime(Date invoiceTime)
    {
        this.invoiceTime = invoiceTime;
    }
    public String getReceiverName()
    {
        return receiverName;
    }
    public void setReceiverName(String receiverName)
    {
        this.receiverName = receiverName;
    }
    public String getReportNo()
    {
        return reportNo;
    }
    public void setReportNo(String reportNo)
    {
        this.reportNo = reportNo;
    }
    public String getReportName()
    {
        return reportName;
    }
    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }
    public String getReportSampleName()
    {
        return reportSampleName;
    }
    public void setReportSampleName(String reportSampleName)
    {
        this.reportSampleName = reportSampleName;
    }
    public String getReportProductName()
    {
        return reportProductName;
    }
    public void setReportProductName(String reportProductName)
    {
        this.reportProductName = reportProductName;
    }
    public Date getReportDate()
    {
        return reportDate;
    }
    public void setReportDate(Date reportDate)
    {
        this.reportDate = reportDate;
    }
    public String getReportStatus()
    {
        return reportStatus;
    }
    public void setReportStatus(String reportStatus)
    {
        this.reportStatus = reportStatus;
    }
    
}
