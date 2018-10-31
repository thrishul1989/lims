package com.todaysoft.lims.system.modules.bpm.abnormal.model;

public class AbnormalTaskSolveRequest
{
    private String id;

    private String inputSampleId;
    
    private String strategy;
    
    private String remark;

    private String semantic;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }

    public String getInputSampleId() {
        return inputSampleId;
    }

    public void setInputSampleId(String inputSampleId) {
        this.inputSampleId = inputSampleId;
    }

    public String getStrategy()
    {
        return strategy;
    }
    
    public void setStrategy(String strategy)
    {
        this.strategy = strategy;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getSemantic() {
        return semantic;
    }

    public void setSemantic(String semantic) {
        this.semantic = semantic;
    }
}
