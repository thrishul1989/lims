package com.todaysoft.lims.sample.entity.materialsApply;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_MATERIALS_APPLY_TRANSPORT_RELATION")
public class MaterialsApplyTransportRelation extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String transportId;//物流ID
    
    private String materialsName;//物资名称
    
    private Integer materialSendCount;//物资数量
    
    @Column(name = "TRANSPORT_ID")
    public String getTransportId()
    {
        return transportId;
    }
    
    public void setTransportId(String transportId)
    {
        this.transportId = transportId;
    }
    
    @Column(name = "MATERIALS_NAME")
    public String getMaterialsName()
    {
        return materialsName;
    }
    
    public void setMaterialsName(String materialsName)
    {
        this.materialsName = materialsName;
    }
    
    @Column(name = "MATERIALS_SEND_COUNT")
    public Integer getMaterialSendCount()
    {
        return materialSendCount;
    }
    
    public void setMaterialSendCount(Integer materialSendCount)
    {
        this.materialSendCount = materialSendCount;
    }
    
}
