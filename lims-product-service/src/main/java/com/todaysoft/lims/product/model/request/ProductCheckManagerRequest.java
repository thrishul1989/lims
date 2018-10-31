package com.todaysoft.lims.product.model.request;

public class ProductCheckManagerRequest {

	
	private Integer productId; //产品id
	
	private Integer probeId;//探针id
	
	private String probeName;//检测方法name
	
	private Integer id; //检测方法id
	
	private String name ;//检测方法name

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getProbeId() {
		return probeId;
	}

	public void setProbeId(Integer probeId) {
		this.probeId = probeId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
