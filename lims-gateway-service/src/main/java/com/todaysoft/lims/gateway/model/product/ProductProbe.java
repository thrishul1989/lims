package com.todaysoft.lims.gateway.model.product;

import com.todaysoft.lims.gateway.model.Product;



public class ProductProbe  {


	private String id;
	private Integer probeId;
	private String probeName; //显示名称
	private Double dataSize; //数据量
	private String probeCompany; //单位
    private Product productId;    //任务id
    private Double qualitySize; //质量
    
    
	public Double getQualitySize() {
		return qualitySize;
	}
	public void setQualitySize(Double qualitySize) {
		this.qualitySize = qualitySize;
	}
	public String getProbeName() {
		return probeName;
	}
	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public Product getProductId() {
		return productId;
	}
	public void setProductId(Product productId) {
		this.productId = productId;
	}
    
    
    


}
