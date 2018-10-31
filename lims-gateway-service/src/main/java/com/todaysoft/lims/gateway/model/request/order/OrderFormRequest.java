package com.todaysoft.lims.gateway.model.request.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class OrderFormRequest {
	
	private String id;

	private Integer orderType;//订单类型（临检、科研、健康）
	//private Contract contract; //归属合同
	private String contractId; //归属合同id ---接收前台
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
	private Date createTime; //创建时间
	
	
	
	private List<OrderProduct> orderProductList  = new ArrayList<OrderProduct>(); 
	private String orderProduct; //接收前台产品
	
	private List<OrderExaminee> orderExaminee  = new ArrayList<OrderExaminee>(); //接收受检人信息 1-1
	private String name;
	private int sex;//性别
	private String nation;//民族
	private String birthday;//出生日期
	private String age;//年龄
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	private String ageSnapshot; //申请检测时年龄（X/X/X）
	private String origin; //籍贯
	private String contactName;//联系人
	private String contactPhone;//联系电话
	private String contactEmail;//联系邮箱
	private String recordNo; //病历号
	private String medicalHistory;//既往病史
	
	
	private String zhiqing; //知情同意书
	private String fujian;//附件（多个）
	
	

	private String parmarySample;  //接收前台主样本
	private String subsidiarySample; //接收前台附属样本
	private List<OrderPrimarySample>  orderPrimarySample =  new ArrayList<OrderPrimarySample>(); //主样本
	private List<OrderSubsidiarySample>  orderSubsidiarySample =  new ArrayList<OrderSubsidiarySample>(); //附属样本
	
	
	private List<OrderSampleGroup> orderSampleGroup = new ArrayList<OrderSampleGroup>();  //订单样本分组组名
	private String orderGroupName; //接收前台分组信息
	
	
	
	private String orderExamineeDiagnosis; //诊断
	
	private List<OrderExamineeDiagnosis> orderExamineeDiagnosisList = new ArrayList<OrderExamineeDiagnosis>(); 
	
	private String orderExamineeGene;//基因
	private List<OrderExamineeGene>  orderExamineeGeneList = new ArrayList<OrderExamineeGene>(); 
	
	private String orderExamineePhenotype;//表型
	private List<OrderExamineePhenotype> orderExamineePhenotypeList = new ArrayList<OrderExamineePhenotype>();
	

	
	
	
	
	
	
	
	
	public List<OrderExaminee> getOrderExaminee() {
		return orderExaminee;
	}
	public void setOrderExaminee(List<OrderExaminee> orderExaminee) {
		this.orderExaminee = orderExaminee;
	}
	public List<OrderSampleGroup> getOrderSampleGroup() {
		return orderSampleGroup;
	}
	public void setOrderSampleGroup(List<OrderSampleGroup> orderSampleGroup) {
		this.orderSampleGroup = orderSampleGroup;
	}
	public List<OrderProduct> getOrderProductList() {
		return orderProductList;
	}
	public void setOrderProductList(List<OrderProduct> orderProductList) {
		this.orderProductList = orderProductList;
	}
	public String getOrderExamineeDiagnosis() {
		return orderExamineeDiagnosis;
	}
	public void setOrderExamineeDiagnosis(String orderExamineeDiagnosis) {
		this.orderExamineeDiagnosis = orderExamineeDiagnosis;
	}
	public List<OrderExamineeDiagnosis> getOrderExamineeDiagnosisList() {
		return orderExamineeDiagnosisList;
	}
	public void setOrderExamineeDiagnosisList(
			List<OrderExamineeDiagnosis> orderExamineeDiagnosisList) {
		this.orderExamineeDiagnosisList = orderExamineeDiagnosisList;
	}
	public String getOrderExamineeGene() {
		return orderExamineeGene;
	}
	public void setOrderExamineeGene(String orderExamineeGene) {
		this.orderExamineeGene = orderExamineeGene;
	}
	public List<OrderExamineeGene> getOrderExamineeGeneList() {
		return orderExamineeGeneList;
	}
	public void setOrderExamineeGeneList(
			List<OrderExamineeGene> orderExamineeGeneList) {
		this.orderExamineeGeneList = orderExamineeGeneList;
	}
	public String getOrderExamineePhenotype() {
		return orderExamineePhenotype;
	}
	public void setOrderExamineePhenotype(String orderExamineePhenotype) {
		this.orderExamineePhenotype = orderExamineePhenotype;
	}
	public List<OrderExamineePhenotype> getOrderExamineePhenotypeList() {
		return orderExamineePhenotypeList;
	}
	public void setOrderExamineePhenotypeList(
			List<OrderExamineePhenotype> orderExamineePhenotypeList) {
		this.orderExamineePhenotypeList = orderExamineePhenotypeList;
	}
	public String getParmarySample() {
		return parmarySample;
	}
	public void setParmarySample(String parmarySample) {
		this.parmarySample = parmarySample;
	}
	public String getSubsidiarySample() {
		return subsidiarySample;
	}
	public void setSubsidiarySample(String subsidiarySample) {
		this.subsidiarySample = subsidiarySample;
	}
	public String getOrderProduct() {
		return orderProduct;
	}
	public void setOrderProduct(String orderProduct) {
		this.orderProduct = orderProduct;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getAgeSnapshot() {
		return ageSnapshot;
	}
	public void setAgeSnapshot(String ageSnapshot) {
		this.ageSnapshot = ageSnapshot;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getRecordNo() {
		return recordNo;
	}
	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}
	public String getMedicalHistory() {
		return medicalHistory;
	}
	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	
	public String getOrderGroupName() {
		return orderGroupName;
	}
	public void setOrderGroupName(String orderGroupName) {
		this.orderGroupName = orderGroupName;
	}

	
	public String getZhiqing() {
		return zhiqing;
	}
	public void setZhiqing(String zhiqing) {
		this.zhiqing = zhiqing;
	}
	public String getFujian() {
		return fujian;
	}
	public void setFujian(String fujian) {
		this.fujian = fujian;
	}
	public List<OrderPrimarySample> getOrderPrimarySample() {
		return orderPrimarySample;
	}
	public void setOrderPrimarySample(List<OrderPrimarySample> orderPrimarySample) {
		this.orderPrimarySample = orderPrimarySample;
	}
	public List<OrderSubsidiarySample> getOrderSubsidiarySample() {
		return orderSubsidiarySample;
	}
	public void setOrderSubsidiarySample(List<OrderSubsidiarySample> orderSubsidiarySample) {
		this.orderSubsidiarySample = orderSubsidiarySample;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	public String getRecipientPhone() {
		return recipientPhone;
	}
	public void setRecipientPhone(String recipientPhone) {
		this.recipientPhone = recipientPhone;
	}
	public String getRecipientEmail() {
		return recipientEmail;
	}
	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}
	public String getRecipientAddress() {
		return recipientAddress;
	}
	public void setRecipientAddress(String recipientAddress) {
		this.recipientAddress = recipientAddress;
	}
	public String getDoctorAssist() {
		return doctorAssist;
	}
	public void setDoctorAssist(String doctorAssist) {
		this.doctorAssist = doctorAssist;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
