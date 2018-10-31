package com.todaysoft.lims.sample.model.request.internal;

public class TestingSheetTask
{
    private String activitiTaskId;
    
    private String testingCode;
    
    private String inputSampleInstanceId;
    
    private String outputSampleCode;
    
    private String outputSampleLocation;
    
    public String getActivitiTaskId()
    {
        return activitiTaskId;
    }
    
    public void setActivitiTaskId(String activitiTaskId)
    {
        this.activitiTaskId = activitiTaskId;
    }
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    public String getInputSampleInstanceId()
    {
        return inputSampleInstanceId;
    }
    
    public void setInputSampleInstanceId(String inputSampleInstanceId)
    {
        this.inputSampleInstanceId = inputSampleInstanceId;
    }
    
    public String getOutputSampleCode()
    {
        return outputSampleCode;
    }
    
    public void setOutputSampleCode(String outputSampleCode)
    {
        this.outputSampleCode = outputSampleCode;
    }
    
    public String getOutputSampleLocation()
    {
        return outputSampleLocation;
    }
    
    public void setOutputSampleLocation(String outputSampleLocation)
    {
        this.outputSampleLocation = outputSampleLocation;
    }
}
