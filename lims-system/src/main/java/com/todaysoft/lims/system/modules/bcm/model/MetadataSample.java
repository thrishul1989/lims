package com.todaysoft.lims.system.modules.bcm.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.system.model.vo.Task;

public class MetadataSample
{
    private String id;
    
    private String name;//类型名称
    
    private String testingUnit;//实验单位
    
    private Integer intermediate;//是否中间产物 1-否，2-是
    
    private String storageType;//存储类型
    
    private String receivingUnit;//收样单位
    
    private BigDecimal lsSize;//长期存储量
    
    private String samplingTips;//样本采集要求
    
    private String transportingTips;//样本运输要求
    
    private String storagingTips;//样本保存要求
    
    private Integer delFlag;
    
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
    
    public String getTestingUnit()
    {
        return testingUnit;
    }
    
    public void setTestingUnit(String testingUnit)
    {
        this.testingUnit = testingUnit;
    }
    
    public Integer getIntermediate()
    {
        return intermediate;
    }
    
    public void setIntermediate(Integer intermediate)
    {
        this.intermediate = intermediate;
    }
    
    public String getStorageType()
    {
        return storageType;
    }
    
    public void setStorageType(String storageType)
    {
        this.storageType = storageType;
    }
    
    public String getReceivingUnit()
    {
        return receivingUnit;
    }
    
    public void setReceivingUnit(String receivingUnit)
    {
        this.receivingUnit = receivingUnit;
    }
    
    public BigDecimal getLsSize()
    {
        return lsSize;
    }
    
    public void setLsSize(BigDecimal lsSize)
    {
        this.lsSize = lsSize;
    }
    
    public String getSamplingTips()
    {
        return samplingTips;
    }
    
    public void setSamplingTips(String samplingTips)
    {
        this.samplingTips = samplingTips;
    }
    
    public String getTransportingTips()
    {
        return transportingTips;
    }
    
    public void setTransportingTips(String transportingTips)
    {
        this.transportingTips = transportingTips;
    }
    
    public String getStoragingTips()
    {
        return storagingTips;
    }
    
    public void setStoragingTips(String storagingTips)
    {
        this.storagingTips = storagingTips;
    }
    
    public Integer getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
    
    private List<MetadataSample> targetMetadataSamples = new ArrayList<MetadataSample>();//可转化的样本
    
    public List<MetadataSample> getTargetMetadataSamples()
    {
        return targetMetadataSamples;
    }
    
    public void setTargetMetadataSamples(List<MetadataSample> targetMetadataSamples)
    {
        this.targetMetadataSamples = targetMetadataSamples;
    }
    
    private List<Task> tasks = new ArrayList<Task>();//匹配是否可配置的outputId 获取的task
    
    public List<Task> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<Task> tasks)
    {
        this.tasks = tasks;
    }
    
    @Override
    public int hashCode()
    {
        String in = id + name;
        return in.hashCode();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MetadataSample other = (MetadataSample)obj;
        return id.equals(other.id) && name.equals(other.name);
    }
    
}
