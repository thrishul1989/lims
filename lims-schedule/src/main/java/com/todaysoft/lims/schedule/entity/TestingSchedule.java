package com.todaysoft.lims.schedule.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SCHEDULE")
public class TestingSchedule extends UuidKeyEntity {
	@Override
    public String toString()
    {
        return "TestingSchedule [orderId=" + orderId + ", productId=" + productId + ", methodId=" + methodId + ", sampleId=" + sampleId + ", verifyKey="
            + verifyKey + ", status=" + status + ", activeTask=" + activeTask + ", scheduleNodes=" + scheduleNodes + ", verifyTarget=" + verifyTarget
            + ", startTime=" + startTime + ", endType=" + endType + ", endTime=" + endTime + "]";
    }

    private static final long serialVersionUID = -6194028150736872292L;

	public static final String END_FAILURE = "0";

	public static final String END_SUCCESS = "1";

	private String orderId;

	private String productId;

	private String methodId;

	private String sampleId;

	private String verifyKey;

	private Integer status;

	private String activeTask;

	private String scheduleNodes;

	private String verifyTarget;

	private Date startTime;

	private String endType;

	private Date endTime;

	@Column(name = "END_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndTime()
	{
		return endTime;
	}

	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}

	@Column(name = "ACTIVE_TASK")
	public String getActiveTask() {
		return activeTask;
	}

	public void setActiveTask(String activeTask) {
		this.activeTask = activeTask;
	}

	@Column(name = "END_TYPE")
	public String getEndType() {
		return endType;
	}

	public void setEndType(String endType) {
		this.endType = endType;
	}

	@Column(name = "VERIFY_TARGET")
	public String getVerifyTarget() {
		return verifyTarget;
	}

	public void setVerifyTarget(String verifyTarget) {
		this.verifyTarget = verifyTarget;
	}

	@Column(name = "ORDER_ID")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "PRODUCT_ID")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Column(name = "METHOD_ID")
	public String getMethodId() {
		return methodId;
	}

	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}

	@Column(name = "SAMPLE_ID")
	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	@Column(name = "VERIFY_KEY")
	public String getVerifyKey() {
		return verifyKey;
	}

	public void setVerifyKey(String verifyKey) {
		this.verifyKey = verifyKey;
	}

	@Column(name = "PROCCESS_STATE")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "SCHEDULE_NODES")
	public String getScheduleNodes() {
		return scheduleNodes;
	}

	public void setScheduleNodes(String scheduleNodes) {
		this.scheduleNodes = scheduleNodes;
	}

	@Column(name = "START_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	
}
