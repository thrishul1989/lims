package com.todaysoft.lims.biology.model.request;

public class UploadAnnotationModel {

	private String dataCode;

	private String bamFilePath;

	private String baiFilePath;
	
	private String vcfFilePath;

	private String qtControlFilePath;

	private String rgcFilePath;

	private String snvFilePath;
	
	private String snvJsonFilePath;

	private String svFilePath;
	
	private String svJsonFilePath;

	private String capCnvFilePath;

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public String getBamFilePath() {
		return bamFilePath;
	}

	public void setBamFilePath(String bamFilePath) {
		this.bamFilePath = bamFilePath;
	}

	public String getBaiFilePath() {
		return baiFilePath;
	}

	public void setBaiFilePath(String baiFilePath) {
		this.baiFilePath = baiFilePath;
	}

	public String getVcfFilePath() {
		return vcfFilePath;
	}

	public void setVcfFilePath(String vcfFilePath) {
		this.vcfFilePath = vcfFilePath;
	}

	public String getQtControlFilePath() {
		return qtControlFilePath;
	}

	public void setQtControlFilePath(String qtControlFilePath) {
		this.qtControlFilePath = qtControlFilePath;
	}

	public String getRgcFilePath() {
		return rgcFilePath;
	}

	public void setRgcFilePath(String rgcFilePath) {
		this.rgcFilePath = rgcFilePath;
	}

	public String getSnvFilePath() {
		return snvFilePath;
	}

	public void setSnvFilePath(String snvFilePath) {
		this.snvFilePath = snvFilePath;
	}

	public String getSnvJsonFilePath() {
		return snvJsonFilePath;
	}

	public void setSnvJsonFilePath(String snvJsonFilePath) {
		this.snvJsonFilePath = snvJsonFilePath;
	}

	public String getSvFilePath() {
		return svFilePath;
	}

	public void setSvFilePath(String svFilePath) {
		this.svFilePath = svFilePath;
	}

	public String getSvJsonFilePath() {
		return svJsonFilePath;
	}

	public void setSvJsonFilePath(String svJsonFilePath) {
		this.svJsonFilePath = svJsonFilePath;
	}

	public String getCapCnvFilePath() {
		return capCnvFilePath;
	}

	public void setCapCnvFilePath(String capCnvFilePath) {
		this.capCnvFilePath = capCnvFilePath;
	}

	
}
