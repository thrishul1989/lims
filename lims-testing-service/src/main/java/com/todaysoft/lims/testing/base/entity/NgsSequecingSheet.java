package com.todaysoft.lims.testing.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NGS_SEQUECING_SHEET")
public class NgsSequecingSheet implements Serializable {
	private String id;

	private String ngsTaskId;

	private String code;

	private String description;

	private String testerId;

	private String testerName;

	private String assignerId;

	private String assignerName;

	private Date assignTime;

	private String submiterId;

	private String submiterName;

	private Date submitTime;

	private Date createTime;

	private String reagentKitId;

	@Id
    @Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "NGS_TASK_ID")
	public String getNgsTaskId() {
		return ngsTaskId;
	}

	public void setNgsTaskId(String ngsTaskId) {
		this.ngsTaskId = ngsTaskId;
	}

	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "DESCRIPTTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "TESTER_ID")
	public String getTesterId() {
		return testerId;
	}

	public void setTesterId(String testerId) {
		this.testerId = testerId;
	}

	@Column(name = "TESTER_NAME")
	public String getTesterName() {
		return testerName;
	}

	public void setTesterName(String testerName) {
		this.testerName = testerName;
	}

	@Column(name = "ASSIGNER_ID")
	public String getAssignerId() {
		return assignerId;
	}

	public void setAssignerId(String assignerId) {
		this.assignerId = assignerId;
	}

	@Column(name = "ASSIGNER_NAME")
	public String getAssignerName() {
		return assignerName;
	}

	public void setAssignerName(String assignerName) {
		this.assignerName = assignerName;
	}

	@Column(name = "ASSIGN_TIME")
	public Date getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	@Column(name = "SUBMITER_ID")
	public String getSubmiterId() {
		return submiterId;
	}

	public void setSubmiterId(String submiterId) {
		this.submiterId = submiterId;
	}

	@Column(name = "SUBMITER_NAME")
	public String getSubmiterName() {
		return submiterName;
	}

	public void setSubmiterName(String submiterName) {
		this.submiterName = submiterName;
	}

	@Column(name = "SUBMIT_TIME")
	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "REAGENT_KIT_ID")
	public String getReagentKitId() {
		return reagentKitId;
	}

	public void setReagentKitId(String reagentKitId) {
		this.reagentKitId = reagentKitId;
	}

}