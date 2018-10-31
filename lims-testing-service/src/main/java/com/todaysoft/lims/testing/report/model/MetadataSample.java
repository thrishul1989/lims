package com.todaysoft.lims.testing.report.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_METADATA_SAMPLE")
public class MetadataSample extends UuidKeyEntity
{
    private static final long serialVersionUID = 8971472981680521731L;
    
    private String name;//类型名称
    
    private String testingUnit;//实验单位
    
    private Integer intermediate;//是否中间产物 1-否，2-是
    
    private String storageType;//存储类型
    
    private String receivingUnit;//收样单位
    
    private BigDecimal lsSize;//长期存储量
    
    private String samplingTips;//样本采集要求
    
    private String transportingTips;//样本运输要求
    
    private String storagingTips;//样本保存要求
    
    private Integer delFlag;//0-未删除，1-已删除
    
    private Integer sort;
    
    @Column(name = "SORT")
    public Integer getSort()
    {
        return sort;
    }
    
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }
    
    @Column(name = "NAME")
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @Column(name = "TESTING_UNIT")
    public String getTestingUnit()
    {
        return testingUnit;
    }
    
    public void setTestingUnit(String testingUnit)
    {
        this.testingUnit = testingUnit;
    }
    
    @Column(name = "INTERMEDIATE")
    public Integer getIntermediate()
    {
        return intermediate;
    }
    
    public void setIntermediate(Integer intermediate)
    {
        this.intermediate = intermediate;
    }
    
    @Column(name = "STORAGE_TYPE")
    public String getStorageType()
    {
        return storageType;
    }
    
    public void setStorageType(String storageType)
    {
        this.storageType = storageType;
    }
    
    @Column(name = "RECEIVING_UNIT")
    public String getReceivingUnit()
    {
        return receivingUnit;
    }
    
    public void setReceivingUnit(String receivingUnit)
    {
        this.receivingUnit = receivingUnit;
    }
    
    @Column(name = "LS_SIZE")
    public BigDecimal getLsSize()
    {
        return lsSize;
    }
    
    public void setLsSize(BigDecimal lsSize)
    {
        this.lsSize = lsSize;
    }
    
    @Column(name = "SAMPLING_TIPS")
    public String getSamplingTips()
    {
        return samplingTips;
    }
    
    public void setSamplingTips(String samplingTips)
    {
        this.samplingTips = samplingTips;
    }
    
    @Column(name = "TRANSPORTING_TIPS")
    public String getTransportingTips()
    {
        return transportingTips;
    }
    
    public void setTransportingTips(String transportingTips)
    {
        this.transportingTips = transportingTips;
    }
    
    @Column(name = "STORAGING_TIPS")
    public String getStoragingTips()
    {
        return storagingTips;
    }
    
    public void setStoragingTips(String storagingTips)
    {
        this.storagingTips = storagingTips;
    }
    
    @Column(name = "DEL_FLAG", columnDefinition = "tinyint default 0")
    public Integer getDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(Integer delFlag)
    {
        this.delFlag = delFlag;
    }
}
