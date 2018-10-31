package com.todaysoft.lims.reagent.model.request;

public class StoreContainerPagingRequest
{
    private String id;
    
    private int pageNo;
    
    private int pageSize;
    
    private String code; // 容器编号
    
    private String name; // 容器名称
    
    private String deviceType; //容器类型  DEVICE_TYPE
    
    private String storageType; //存储类别 1 临时 ;2:->长期
    
    private Integer state; //增加存储位置查询状态
    
    private String locationCode; //存储位置编号
    
    private boolean deleted;
    
    private String sampleType;
    
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    public String getLocationCode()
    {
        return locationCode;
    }
    
    public void setLocationCode(String locationCode)
    {
        this.locationCode = locationCode;
    }
    
    public Integer getState()
    {
        return state;
    }
    
    public void setState(Integer state)
    {
        this.state = state;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getDeviceType()
    {
        return deviceType;
    }
    
    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }
    
    public String getStorageType()
    {
        return storageType;
    }
    
    public void setStorageType(String storageType)
    {
        this.storageType = storageType;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
}
