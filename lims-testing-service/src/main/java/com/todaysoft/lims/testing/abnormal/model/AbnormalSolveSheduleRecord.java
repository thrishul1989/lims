package com.todaysoft.lims.testing.abnormal.model;

public class AbnormalSolveSheduleRecord
{
    private String id;
    
    private String productName;
    
    private String methodName;
    
    private String orderCode;

    private String verifyKey;

    private String activeTask;

    private Integer proccessState;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getMethodName()
    {
        return methodName;
    }

    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

    public String getOrderCode()
    {
        return orderCode;
    }

    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }

    public String getVerifyKey() {
        return verifyKey;
    }

    public void setVerifyKey(String verifyKey) {
        this.verifyKey = verifyKey;
    }

    public String getActiveTask() {
        return activeTask;
    }

    public void setActiveTask(String activeTask) {
        this.activeTask = activeTask;
    }

    public Integer getProccessState() {
        return proccessState;
    }

    public void setProccessState(Integer proccessState) {
        this.proccessState = proccessState;
    }
}
