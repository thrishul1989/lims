package com.todaysoft.lims.system.model.searcher;

public class TaskSearcher
{
    private String semantic;
    
    private String name;
    
    private String type;
    
    private String outputSampleId;
    
    private String inputSampleId;
    
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getOutputSampleId()
    {
        return outputSampleId;
    }
    
    public void setOutputSampleId(String outputSampleId)
    {
        this.outputSampleId = outputSampleId;
    }
    
    public String getInputSampleId()
    {
        return inputSampleId;
    }
    
    public void setInputSampleId(String inputSampleId)
    {
        this.inputSampleId = inputSampleId;
    }
    
}
