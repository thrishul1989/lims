package com.todaysoft.lims.product.model.request;

public class SamplePagingRequest
{
    private String id;
    
    private String name; // 样本名称
    
    private String testingUnit;
    
    private Integer intermediate;
    
    private String storageType;
    
    private String receivingUnit;
    
    private String lsSize;
    
    private String samplingTips;
    
    private String transportingTips;
    
    private String storagingTips;
    
    private Integer delFlag;
    
    private String keywords;
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    private boolean matchModel; //匹配模式 true 精确匹配 ，默认false
    
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
    
    public String getLsSize()
    {
        return lsSize;
    }
    
    public void setLsSize(String lsSize)
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
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public String getKeywords()
    {
        return keywords;
    }
    
    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }
    
    public boolean isMatchModel()
    {
        return matchModel;
    }
    
    public void setMatchModel(boolean matchModel)
    {
        this.matchModel = matchModel;
    }
}
