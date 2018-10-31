package com.todaysoft.lims.sample.model.order;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.sample.entity.order.OrderExaminee;
import com.todaysoft.lims.sample.entity.order.OrderExamineeDiagnosis;
import com.todaysoft.lims.sample.entity.order.OrderExamineeGene;
import com.todaysoft.lims.sample.entity.order.OrderExamineePhenotype;
import com.todaysoft.lims.sample.entity.order.OrderPrimarySample;
import com.todaysoft.lims.sample.entity.order.OrderProduct;
import com.todaysoft.lims.sample.entity.order.OrderSampleGroup;
import com.todaysoft.lims.sample.entity.order.OrderSubsidiarySample;

public class OrderFormRequest
{
    private String id;
    
    private String orderType;//订单类型（临检、科研、健康）
    
    private String contractId; //归属合同id ---接收前台
    
    private String ownerId; //所属客户
    
    private String submitterId;// --》createID
    
    private String submitterName;
    
    private String code; //订单编号
    
    private int status;//订单状态
    
    private int amount;//订单金额，单位（分）
    
    private String recipientName;//收件人姓名
    
    private String recipientPhone;//收件人电话
    
    private String recipientEmail;//收件人邮箱
    
    private String recipientAddress;//收件人地址
    
    private String doctorAssist;//客户参与（X/X）
    
    private String invoiceTitle;//发票抬头
    
    private String creatorId;//创建人ID
    
    private String creatorName;//创建人姓名
    
    private List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();
    
    private String orderProduct; //接收前台产品
    
    private String name;
    
    private String sex;//性别
    
    private String nation;//民族
    
    private String birthday;//出生日期
    
    private String age;//年龄
    
    private String ageSnapshot; //申请检测时年龄（X/X/X）
    
    private String origin; //籍贯
    
    private String contactName;//联系人
    
    private String contactPhone;//联系电话
    
    private String contactEmail;//联系邮箱
    
    private String recordNo; //病历号
    
    private String medicalHistory;//既往病史
    
    private String familyMedicalHistory; //家族病史
    
    private String clinicalRecommend;
    
    private String consentFigures; //知情同意书
    
    private String recordFigures;//附件（多个）
    
    private String familyFigures;//家系图附件（多附件逗号隔开）;
    
    private List<OrderExaminee> orderExaminee = new ArrayList<OrderExaminee>(); //接收受检人信息 1-1
    
    private String parmarySample; //接收前台主样本
    
    private String subsidiarySample; //接收前台附属样本
    
    private List<OrderPrimarySample> orderPrimarySample = new ArrayList<OrderPrimarySample>(); //主样本
    
    private List<OrderSubsidiarySample> orderSubsidiarySample = new ArrayList<OrderSubsidiarySample>(); //附属样本
    
    private List<OrderSampleGroup> orderSampleGroup = new ArrayList<OrderSampleGroup>(); //订单样本分组组名
    
    private String orderGroupName; //接收前台分组信息
    
    private String orderExamineeDiagnosis; //诊断
    
    private List<OrderExamineeDiagnosis> orderExamineeDiagnosisList = new ArrayList<OrderExamineeDiagnosis>();
    
    private String orderExamineeGene;//基因
    
    private List<OrderExamineeGene> orderExamineeGeneList = new ArrayList<OrderExamineeGene>();
    
    private String orderExamineePhenotype;//表型
    
    private List<OrderExamineePhenotype> orderExamineePhenotypeList = new ArrayList<OrderExamineePhenotype>();
    
    private Integer subsidiarySampleAmount = 0; //附属样本额外收费金额，单位（分）
    
    private Integer discountAmount = 0; //优惠券抵用金额
    
    private Integer reduceAmount = 0; //减免金额
    
    private Integer extraMoney = 0; //每个附属样本费用
    
    private Integer samplingAmount; //增加收样费用
    
    private Integer incomingAmount = 0;//到账金额

    private String projectManager;//项目管理人

    private Integer billingType; // 开票形式:0-不需要;1-电子;2-纸质

    private Integer headerType; // 抬头类型:0-个人;1-企业

    private String dutyParagraph; // 税号

    private String mailbox; // 邮箱

    private Integer belongArea; //南北区

    public Integer getBelongArea() {
        return belongArea;
    }

    public void setBelongArea(Integer belongArea) {
        this.belongArea = belongArea;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public Integer getBillingType() {
        return billingType;
    }

    public void setBillingType(Integer billingType) {
        this.billingType = billingType;
    }

    public Integer getHeaderType() {
        return headerType;
    }

    public void setHeaderType(Integer headerType) {
        this.headerType = headerType;
    }

    public String getDutyParagraph() {
        return dutyParagraph;
    }

    public void setDutyParagraph(String dutyParagraph) {
        this.dutyParagraph = dutyParagraph;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public Integer getIncomingAmount()
    {
        return incomingAmount;
    }
    
    public void setIncomingAmount(Integer incomingAmount)
    {
        this.incomingAmount = incomingAmount;
    }
    
    public Integer getSamplingAmount()
    {
        return samplingAmount;
    }
    
    public void setSamplingAmount(Integer samplingAmount)
    {
        this.samplingAmount = samplingAmount;
    }
    
    public Integer getExtraMoney()
    {
        return extraMoney;
    }
    
    public void setExtraMoney(Integer extraMoney)
    {
        this.extraMoney = extraMoney;
    }
    
    public Integer getSubsidiarySampleAmount()
    {
        return subsidiarySampleAmount;
    }
    
    public void setSubsidiarySampleAmount(Integer subsidiarySampleAmount)
    {
        this.subsidiarySampleAmount = subsidiarySampleAmount;
    }
    
    public Integer getDiscountAmount()
    {
        return discountAmount;
    }
    
    public void setDiscountAmount(Integer discountAmount)
    {
        this.discountAmount = discountAmount;
    }
    
    public Integer getReduceAmount()
    {
        return reduceAmount;
    }
    
    public void setReduceAmount(Integer reduceAmount)
    {
        this.reduceAmount = reduceAmount;
    }
    
    public String getClinicalRecommend()
    {
        return clinicalRecommend;
    }
    
    public void setClinicalRecommend(String clinicalRecommend)
    {
        this.clinicalRecommend = clinicalRecommend;
    }
    
    public List<OrderExaminee> getOrderExaminee()
    {
        return orderExaminee;
    }
    
    public void setOrderExaminee(List<OrderExaminee> orderExaminee)
    {
        this.orderExaminee = orderExaminee;
    }
    
    public List<OrderSampleGroup> getOrderSampleGroup()
    {
        return orderSampleGroup;
    }
    
    public void setOrderSampleGroup(List<OrderSampleGroup> orderSampleGroup)
    {
        this.orderSampleGroup = orderSampleGroup;
    }
    
    public String getAge()
    {
        return age;
    }
    
    public void setAge(String age)
    {
        this.age = age;
    }
    
    public List<OrderProduct> getOrderProductList()
    {
        return orderProductList;
    }
    
    public void setOrderProductList(List<OrderProduct> orderProductList)
    {
        this.orderProductList = orderProductList;
    }
    
    public String getOrderExamineeDiagnosis()
    {
        return orderExamineeDiagnosis;
    }
    
    public void setOrderExamineeDiagnosis(String orderExamineeDiagnosis)
    {
        this.orderExamineeDiagnosis = orderExamineeDiagnosis;
    }
    
    public List<OrderExamineeDiagnosis> getOrderExamineeDiagnosisList()
    {
        return orderExamineeDiagnosisList;
    }
    
    public void setOrderExamineeDiagnosisList(List<OrderExamineeDiagnosis> orderExamineeDiagnosisList)
    {
        this.orderExamineeDiagnosisList = orderExamineeDiagnosisList;
    }
    
    public String getOrderExamineeGene()
    {
        return orderExamineeGene;
    }
    
    public void setOrderExamineeGene(String orderExamineeGene)
    {
        this.orderExamineeGene = orderExamineeGene;
    }
    
    public List<OrderExamineeGene> getOrderExamineeGeneList()
    {
        return orderExamineeGeneList;
    }
    
    public void setOrderExamineeGeneList(List<OrderExamineeGene> orderExamineeGeneList)
    {
        this.orderExamineeGeneList = orderExamineeGeneList;
    }
    
    public String getOrderExamineePhenotype()
    {
        return orderExamineePhenotype;
    }
    
    public void setOrderExamineePhenotype(String orderExamineePhenotype)
    {
        this.orderExamineePhenotype = orderExamineePhenotype;
    }
    
    public List<OrderExamineePhenotype> getOrderExamineePhenotypeList()
    {
        return orderExamineePhenotypeList;
    }
    
    public void setOrderExamineePhenotypeList(List<OrderExamineePhenotype> orderExamineePhenotypeList)
    {
        this.orderExamineePhenotypeList = orderExamineePhenotypeList;
    }
    
    public String getParmarySample()
    {
        return parmarySample;
    }
    
    public void setParmarySample(String parmarySample)
    {
        this.parmarySample = parmarySample;
    }
    
    public String getSubsidiarySample()
    {
        return subsidiarySample;
    }
    
    public void setSubsidiarySample(String subsidiarySample)
    {
        this.subsidiarySample = subsidiarySample;
    }
    
    public String getOrderProduct()
    {
        return orderProduct;
    }
    
    public void setOrderProduct(String orderProduct)
    {
        this.orderProduct = orderProduct;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getSex()
    {
        return sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    public String getNation()
    {
        return nation;
    }
    
    public void setNation(String nation)
    {
        this.nation = nation;
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
    
    public String getMedicalHistory()
    {
        return medicalHistory;
    }
    
    public void setMedicalHistory(String medicalHistory)
    {
        this.medicalHistory = medicalHistory;
    }
    
    public String getOrderGroupName()
    {
        return orderGroupName;
    }
    
    public void setOrderGroupName(String orderGroupName)
    {
        this.orderGroupName = orderGroupName;
    }
    
    public String getConsentFigures()
    {
        return consentFigures;
    }
    
    public void setConsentFigures(String consentFigures)
    {
        this.consentFigures = consentFigures;
    }
    
    public String getRecordFigures()
    {
        return recordFigures;
    }
    
    public void setRecordFigures(String recordFigures)
    {
        this.recordFigures = recordFigures;
    }
    
    public List<OrderPrimarySample> getOrderPrimarySample()
    {
        return orderPrimarySample;
    }
    
    public void setOrderPrimarySample(List<OrderPrimarySample> orderPrimarySample)
    {
        this.orderPrimarySample = orderPrimarySample;
    }
    
    public List<OrderSubsidiarySample> getOrderSubsidiarySample()
    {
        return orderSubsidiarySample;
    }
    
    public void setOrderSubsidiarySample(List<OrderSubsidiarySample> orderSubsidiarySample)
    {
        this.orderSubsidiarySample = orderSubsidiarySample;
    }
    
    public String getSubmitterId()
    {
        return submitterId;
    }
    
    public void setSubmitterId(String submitterId)
    {
        this.submitterId = submitterId;
    }
    
    public String getSubmitterName()
    {
        return submitterName;
    }
    
    public void setSubmitterName(String submitterName)
    {
        this.submitterName = submitterName;
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public int getAmount()
    {
        return amount;
    }
    
    public void setAmount(int amount)
    {
        this.amount = amount;
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
    
    public String getRecipientEmail()
    {
        return recipientEmail;
    }
    
    public void setRecipientEmail(String recipientEmail)
    {
        this.recipientEmail = recipientEmail;
    }
    
    public String getRecipientAddress()
    {
        return recipientAddress;
    }
    
    public void setRecipientAddress(String recipientAddress)
    {
        this.recipientAddress = recipientAddress;
    }
    
    public String getDoctorAssist()
    {
        return doctorAssist;
    }
    
    public void setDoctorAssist(String doctorAssist)
    {
        this.doctorAssist = doctorAssist;
    }
    
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getFamilyMedicalHistory()
    {
        return familyMedicalHistory;
    }
    
    public void setFamilyMedicalHistory(String familyMedicalHistory)
    {
        this.familyMedicalHistory = familyMedicalHistory;
    }
    
    public String getFamilyFigures()
    {
        return familyFigures;
    }
    
    public void setFamilyFigures(String familyFigures)
    {
        this.familyFigures = familyFigures;
    }
    
    public String getOwnerId()
    {
        return ownerId;
    }
    
    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }
    
}
