package com.todaysoft.lims.reagent.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_STORAGE_LOCATION")
public class StorageLocation extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String deviceId; //关联存储容器
    
    private String code; //存储位置编号
    
    private String sampleId; //存储物类型
    
    private Integer state; //是否已用
    
    private String name;
    
    @Column(name = "STATE", nullable = false)
    public Integer getState()
    {
        return state;
    }
    
    public void setState(Integer state)
    {
        this.state = state;
    }
    
    @Column(name = "DEVICE_ID", nullable = false)
    public String getDeviceId()
    {
        return deviceId;
    }
    
    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }
    
    @Column(name = "CODE", nullable = false)
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @Column(name = "SAMPLE_ID")
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    @Transient
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
}
