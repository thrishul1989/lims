package com.todaysoft.lims.gateway.model.request.order;

import com.todaysoft.lims.persist.UuidKeyEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order extends UuidKeyEntity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orderType;//订单类型（临检、科研、健康）
	
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
	
	
	private Contract contract; //归属合同
	
	private List<OrderExaminee> orderExamineeList = new ArrayList<OrderExaminee>(); //受检人
	
	private List<OrderProduct> orderProductList  = new ArrayList<OrderProduct>(); 
	private List<OrderPrimarySample>  orderPrimarySample =  new ArrayList<OrderPrimarySample>(); //主样本
	private List<OrderSubsidiarySample>  orderSubsidiarySample =  new ArrayList<OrderSubsidiarySample>(); //附属样本
	
	
	private List<OrderSampleGroup>  orderSampleGroup =  new ArrayList<OrderSampleGroup>();  //样本组 ---组样本  产品
	
	private List<OrderSampleView> view = new ArrayList<OrderSampleView>();
	
	
	
	
	public List<OrderSampleView> getView() {
		return view;
	}
	public void setView(List<OrderSampleView> view) {
		this.view = view;
	}
	public List<OrderSampleGroup> getOrderSampleGroup() {
		return orderSampleGroup;
	}
	public void setOrderSampleGroup(List<OrderSampleGroup> orderSampleGroup) {
		this.orderSampleGroup = orderSampleGroup;
	}
	public Order() {
		super();
	}
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	
	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}
	
	public List<OrderExaminee> getOrderExamineeList() {
		return orderExamineeList;
	}
	public void setOrderExamineeList(List<OrderExaminee> orderExamineeList) {
		this.orderExamineeList = orderExamineeList;
	}
	public List<OrderProduct> getOrderProductList() {
		return orderProductList;
	}
	public void setOrderProductList(List<OrderProduct> orderProductList) {
		this.orderProductList = orderProductList;
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
	public void setOrderSubsidiarySample(
			List<OrderSubsidiarySample> orderSubsidiarySample) {
		this.orderSubsidiarySample = orderSubsidiarySample;
	}
	
	
	
	
	
}
