package com.todaysoft.lims.gateway.model.request;

public class TestingNodeTodoRequest
{
	private String userId;
	
    private String semantic;
    
    private Integer status;
    
    private String sampleType;
    
    private String sampleInstanceCode;
    
    private String receiveType;//订单类型
    

    public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public String getSampleInstanceCode() {
		return sampleInstanceCode;
	}

	public void setSampleInstanceCode(String sampleInstanceCode) {
		this.sampleInstanceCode = sampleInstanceCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSemantic()
    {
        return semantic;
    }

    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}
}
