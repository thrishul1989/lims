package com.todaysoft.lims.gateway.model;

import java.util.Date;

import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveDetail;
import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveOrder;

public class OrderSample {
	private Integer id;
	private Integer orderId; //
	private SampleReceiveOrder order;
	private String sampleType; // 样本类型，0-样本，1-产物
	private Integer sampleDetailId;
	private SampleReceiveDetail sampleDetail; //样本明细
	private Integer sampleKey; // 样本实例的id，如果是样本就保存样本id，如果是实验产物，就保存实验产物id
	private String sampleName;
	private String locationCode; // 存储位置编号
	private Integer source; // 样本来源，0-样本接收，其他为具体任务id
	private String sourceName;
	private Double usageAmount; //使用量
	private String unit; //单位
	private Date storeTime; // 样本存储时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public SampleReceiveOrder getOrder() {
		return order;
	}

	public void setOrder(SampleReceiveOrder order) {
		this.order = order;
	}

	public Integer getSampleDetailId() {
		return sampleDetailId;
	}

	public void setSampleDetailId(Integer sampleDetailId) {
		this.sampleDetailId = sampleDetailId;
	}

	public SampleReceiveDetail getSampleDetail() {
		return sampleDetail;
	}

	public void setSampleDetail(SampleReceiveDetail sampleDetail) {
		this.sampleDetail = sampleDetail;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public Integer getSampleKey() {
		return sampleKey;
	}

	public void setSampleKey(Integer sampleKey) {
		this.sampleKey = sampleKey;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public Double getUsageAmount() {
		return usageAmount;
	}

	public void setUsageAmount(Double usageAmount) {
		this.usageAmount = usageAmount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getStoreTime() {
		return storeTime;
	}

	public void setStoreTime(Date storeTime) {
		this.storeTime = storeTime;
	}
}
