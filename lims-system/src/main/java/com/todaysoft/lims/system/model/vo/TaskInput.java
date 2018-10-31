package com.todaysoft.lims.system.model.vo;

import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;

public class TaskInput
{
    private String id;
    
    private String inputSampleId;
    
    private String reagentKitId;
    
    private BigDecimal inputSize;
    
    private BigDecimal inputVolume;
    
    private List<ReagentInfo> reagentInfoList = Lists.newArrayList();
    
    private MetadataSample sample;
    
    public MetadataSample getSample()
    {
        return sample;
    }
    
    public void setSample(MetadataSample sample)
    {
        this.sample = sample;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getInputSampleId()
    {
        return inputSampleId;
    }
    
    public void setInputSampleId(String inputSampleId)
    {
        this.inputSampleId = inputSampleId;
    }
    
    public String getReagentKitId()
    {
        return reagentKitId;
    }
    
    public void setReagentKitId(String reagentKitId)
    {
        this.reagentKitId = reagentKitId;
    }
    
    public BigDecimal getInputSize()
    {
        return inputSize;
    }
    
    public void setInputSize(BigDecimal inputSize)
    {
        this.inputSize = inputSize;
    }
    
    public BigDecimal getInputVolume()
    {
        return inputVolume;
    }
    
    public void setInputVolume(BigDecimal inputVolume)
    {
        this.inputVolume = inputVolume;
    }
    
    public List<ReagentInfo> getReagentInfoList()
    {
        return reagentInfoList;
    }
    
    public void setReagentInfoList(List<ReagentInfo> reagentInfoList)
    {
        this.reagentInfoList = reagentInfoList;
    }
}
