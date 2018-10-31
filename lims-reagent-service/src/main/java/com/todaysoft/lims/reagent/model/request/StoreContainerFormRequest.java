package com.todaysoft.lims.reagent.model.request;

import java.util.Date;

public class StoreContainerFormRequest
{
    private String id;
    
    private String code; //容器编号
    
    private String name; //容器名称
    
    private String deviceType; //容器类型  DEVICE_TYPE
    
    private String storageType; //存储类别 1 临时 ;2:->长期
    
    private String remark; //备注
    
    private Integer frame; //规格--架
    
    private Integer box;//盒
    
    private Integer site;//位
    
    private Integer cell;//位2
    
    private Date createTime;
    
    private boolean delFlag;
    
    private String sampleType;
    
    public Integer getCell()
    {
        return cell;
    }
    
    public void setCell(Integer cell)
    {
        this.cell = cell;
    }
    
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Integer getFrame()
    {
        return frame;
    }
    
    public void setFrame(Integer frame)
    {
        this.frame = frame;
    }
    
    public Integer getBox()
    {
        return box;
    }
    
    public void setBox(Integer box)
    {
        this.box = box;
    }
    
    public Integer getSite()
    {
        return site;
    }
    
    public void setSite(Integer site)
    {
        this.site = site;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public boolean isDelFlag()
    {
        return delFlag;
    }
    
    public void setDelFlag(boolean delFlag)
    {
        this.delFlag = delFlag;
    }
    
}
