package com.todaysoft.lims.reagent.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_STORAGE_DEVICE")
public class StoreContainer extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -9061060268571051282L;
    
    private String code; //容器编号
    
    private String name; //容器名称
    
    private String deviceType; //容器类型  DEVICE_TYPE
    
    private String storageType; //存储类别 1 临时 ;2:->长期
    
    private String remark; //备注
    
    private Integer frame; //规格--架
    
    private Integer box;//盒
    
    private Integer site;//位
    
    private Integer cell;//位
    
    private Date createTime;
    
    private boolean deleted;
    
    private Date deleteTime;
    
    private String sampleType; //样本类型
    
    @Column(name = "CELL")
    public Integer getCell()
    {
        return cell;
    }
    
    public void setCell(Integer cell)
    {
        this.cell = cell;
    }
    
    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
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
    
    @Column(name = "DEVICE_TYPE", nullable = false, length = 16)
    public String getDeviceType()
    {
        return deviceType;
    }
    
    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }
    
    @Column(name = "STORAGE_TYPE", nullable = false, length = 16)
    public String getStorageType()
    {
        return storageType;
    }
    
    public void setStorageType(String storageType)
    {
        this.storageType = storageType;
    }
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @Column(name = "FRAME", nullable = false)
    public Integer getFrame()
    {
        return frame;
    }
    
    public void setFrame(Integer frame)
    {
        this.frame = frame;
    }
    
    @Column(name = "BOX", nullable = false)
    public Integer getBox()
    {
        return box;
    }
    
    public void setBox(Integer box)
    {
        this.box = box;
    }
    
    @Column(name = "SITE", nullable = false)
    public Integer getSite()
    {
        return site;
    }
    
    public void setSite(Integer site)
    {
        this.site = site;
    }
    
    @Column(name = "CREATE_TIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Column(name = "DEL_FLAG", nullable = false)
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    @Column(name = "DELETE_TIME")
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    @Column(name = "SAMPLE_TYPE")
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
}
