package com.todaysoft.lims.system.modules.bpm.bioanalysis.model.claimTemplate;

import java.util.Date;

public class AnnotateUploadHistoryModel {
	private String id;

	private String dataCode;

	private Date uploadTime;

	private Integer status;
	
	private String uploadDesc;

	public String getUploadDesc() {
		return uploadDesc;
	}

	public void setUploadDesc(String uploadDesc) {
		this.uploadDesc = uploadDesc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
}
