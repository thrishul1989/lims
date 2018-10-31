package com.todaysoft.lims.product.model.request;

//供查询使用
public class ProductDataListRequest {
	
	private Integer productId; //产品id
	
	private String probeName; //探针名称
	
	private Integer probeId;
	
	

	public Integer getProbeId() {
		return probeId;
	}

	public void setProbeId(Integer probeId) {
		this.probeId = probeId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProbeName() {
		return probeName;
	}

	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}
	
	
	
}
