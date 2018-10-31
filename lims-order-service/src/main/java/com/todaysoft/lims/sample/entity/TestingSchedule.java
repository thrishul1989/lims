package com.todaysoft.lims.sample.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SCHEDULE")
public class TestingSchedule extends UuidKeyEntity
{
    private static final long serialVersionUID = -508443442484062284L;
    
    
    //新表属性
    
    private String orderId;
    
    private String productId;
    
    private String methodId;
    
    private String sampleId;
    
    private Date startTime;
    
    private String activeTask;
    
    private Date endTime;
    
    private Integer endType;
    
    private String verifyTarget;
    
//    private List<TestingScheduleTask> testingScheduleTaskList=new ArrayList<TestingScheduleTask>();

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

	@Column(name = "START_TIME")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "ACTIVE_TASK")
	public String getActiveTask() {
		return activeTask;
	}

	public void setActiveTask(String activeTask) {
		this.activeTask = activeTask;
	}

	@Column(name = "END_TIME")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "END_TYPE")
	public Integer getEndType() {
		return endType;
	}

	public void setEndType(Integer endType) {
		this.endType = endType;
	}

	@Column(name = "VERIFY_TARGET")
	public String getVerifyTarget() {
		return verifyTarget;
	}

	public void setVerifyTarget(String verifyTarget) {
		this.verifyTarget = verifyTarget;
	}

/*	@OneToMany(mappedBy = "testingSchedule",cascade= {CascadeType.ALL},fetch=FetchType.EAGER )
	@NotFound(action = NotFoundAction.IGNORE)
	public List<TestingScheduleTask> getTestingScheduleTaskList() {
		return testingScheduleTaskList;
	}

	public void setTestingScheduleTaskList(
			List<TestingScheduleTask> testingScheduleTaskList) {
		this.testingScheduleTaskList = testingScheduleTaskList;
	}
	*/
	
    
}
