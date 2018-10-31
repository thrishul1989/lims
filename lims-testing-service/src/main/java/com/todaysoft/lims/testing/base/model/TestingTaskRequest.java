package com.todaysoft.lims.testing.base.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.todaysoft.lims.testing.base.entity.TestingSample;

import java.util.Date;

public class TestingTaskRequest
{
    
    private String id;
    
    private String name;
    
    private String semantic;
    
    private Date startTime;
    
    private Integer status;//任务状态：1-待分配 2-待实验 3-已结束
    
    private Date endTime;
    
    private Integer endType;
    
    private boolean resubmit;
    
    private Integer resubmitCount;//重做次数RESUBMIT_COUNT
    
    private TestingSample inputSample;
    
    private String receivedSampleCode;

    private String testingSampleCode;//实验样本编号
    
    private String verifyChromosomePosition;//验证位点-染色体位置

    public String getTestingSampleCode() {
        return testingSampleCode;
    }

    public void setTestingSampleCode(String testingSampleCode) {
        this.testingSampleCode = testingSampleCode;
    }

    public String getVerifyChromosomePosition()
    {
        return verifyChromosomePosition;
    }
    
    public void setVerifyChromosomePosition(String verifyChromosomePosition)
    {
        this.verifyChromosomePosition = verifyChromosomePosition;
    }
    
    public String getReceivedSampleCode()
    {
        return receivedSampleCode;
    }
    
    public void setReceivedSampleCode(String receivedSampleCode)
    {
        this.receivedSampleCode = receivedSampleCode;
    }
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
    public TestingSample getInputSample()
    {
        return inputSample;
    }
    
    public void setInputSample(TestingSample inputSample)
    {
        this.inputSample = inputSample;
    }
    
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
    
}
