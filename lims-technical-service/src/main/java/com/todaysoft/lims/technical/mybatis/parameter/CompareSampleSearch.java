package com.todaysoft.lims.technical.mybatis.parameter;

public class CompareSampleSearch {
	private String productCode;
	private String methodName;
	private String sampleCode;
	private String orderCode;
	private String currentFastqDateCode;
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getSampleCode() {
		return sampleCode;
	}
	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
    public String getCurrentFastqDateCode()
    {
        return currentFastqDateCode;
    }
    public void setCurrentFastqDateCode(String currentFastqDateCode)
    {
        this.currentFastqDateCode = currentFastqDateCode;
    }
	
	
}
