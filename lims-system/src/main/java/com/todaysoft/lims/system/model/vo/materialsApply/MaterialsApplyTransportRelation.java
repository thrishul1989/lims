package com.todaysoft.lims.system.model.vo.materialsApply;

public class MaterialsApplyTransportRelation
{
    private String id;
    
    private String transportId;//物流ID
    
    private String materialsName;//物资名称
    
    private Integer materialSendCount;//物资数量
    
    private String materialsApplyDetailId;
    
    public String getMaterialsApplyDetailId()
    {
        return materialsApplyDetailId;
    }
    
    public void setMaterialsApplyDetailId(String materialsApplyDetailId)
    {
        this.materialsApplyDetailId = materialsApplyDetailId;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTransportId()
    {
        return transportId;
    }
    
    public void setTransportId(String transportId)
    {
        this.transportId = transportId;
    }
    
    public String getMaterialsName()
    {
        return materialsName;
    }
    
    public void setMaterialsName(String materialsName)
    {
        this.materialsName = materialsName;
    }
    
    public Integer getMaterialSendCount()
    {
        return materialSendCount;
    }
    
    public void setMaterialSendCount(Integer materialSendCount)
    {
        this.materialSendCount = materialSendCount;
    }
    
}
