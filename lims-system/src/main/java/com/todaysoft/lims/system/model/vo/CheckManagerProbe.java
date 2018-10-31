package com.todaysoft.lims.system.model.vo;




public class CheckManagerProbe  {


	private Integer id;
	private Integer probeId;
	private String probeName; //显示名称
	private Double dataSize; //数据量
	
	private String unit; //新的单位
	
	private Double qualitySize; //质量
	
	private String qualityUnit; //质量单位
	
	private String probeCompany; //单位 ---弃用
    
	
	
    private CheckManagement checkManagementId;    //任务id
    
    
    
    
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getQualitySize() {
		return qualitySize;
	}
	public void setQualitySize(Double qualitySize) {
		this.qualitySize = qualitySize;
	}
	public String getQualityUnit() {
		return qualityUnit;
	}
	public void setQualityUnit(String qualityUnit) {
		this.qualityUnit = qualityUnit;
	}
	public String getProbeName() {
		return probeName;
	}
	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProbeId() {
		return probeId;
	}
	public void setProbeId(Integer probeId) {
		this.probeId = probeId;
	}
	public Double getDataSize() {
		return dataSize;
	}
	public void setDataSize(Double dataSize) {
		this.dataSize = dataSize;
	}
	public String getProbeCompany() {
		return probeCompany;
	}
	public void setProbeCompany(String probeCompany) {
		this.probeCompany = probeCompany;
	}
	public CheckManagement getCheckManagementId() {
		return checkManagementId;
	}
	public void setCheckManagementId(CheckManagement checkManagementId) {
		this.checkManagementId = checkManagementId;
	}
	
	
	
	
    
    
    


}
