package com.todaysoft.lims.system.modules.bpm.samplestock.model;

import java.util.Date;

public class TestingTask
{
    
    private String id;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    public TestingSample getInputSample()
    {
        return inputSample;
    }
    
    public void setInputSample(TestingSample inputSample)
    {
        this.inputSample = inputSample;
    }
    
    public Date getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public Date getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }
    
    public Integer getEndType()
    {
        return endType;
    }
    
    public void setEndType(Integer endType)
    {
        this.endType = endType;
    }
    
    public boolean isResubmit()
    {
        return resubmit;
    }
    
    public void setResubmit(boolean resubmit)
    {
        this.resubmit = resubmit;
    }
    
    public Integer getResubmitCount()
    {
        return resubmitCount;
    }
    
    public void setResubmitCount(Integer resubmitCount)
    {
        this.resubmitCount = resubmitCount;
    }
    
    private String name;
    
    private String semantic;
    
    private TestingSample inputSample;
    
    private Date startTime;
    
    private Integer status;//任务状态：1-待分配 2-待实验 3-已结束
    
    private Date endTime;
    
    private Integer endType;
    
    private boolean resubmit;
    
    private Integer resubmitCount;//重做次数RESUBMIT_COUNT
    
    private String testingCode;//实验编号
    
    private String productName;//订单产品-产品名称
    
    private String orderCode;

    private String testingMethodName;//检测方法-方法名称

    private String testingSampleType;//实验样本-样本类型
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }

    public String getTestingMethodName() {
        return testingMethodName;
    }

    public void setTestingMethodName(String testingMethodName) {
        this.testingMethodName = testingMethodName;
    }

    public String getTestingSampleType() {
        return testingSampleType;
    }

    public void setTestingSampleType(String testingSampleType) {
        this.testingSampleType = testingSampleType;
    }
}
