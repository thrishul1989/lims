package com.todaysoft.lims.sample.entity.materialsApply;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_MATERIALS_APPLY_DETAIL")
public class MaterialsApplyDetail extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String applyId;//申请id
    
    private String materialsType;//物资类别
    
    private String materialsName;//物资名称
    
    private String remark;//备注
    
    private Integer materialsCount;//申请物资数量
    
    private Integer materialsSendCount;//寄送物资数量
    
    @Column(name = "APPLY_ID")
    public String getApplyId()
    {
        return applyId;
    }
    
    public void setApplyId(String applyId)
    {
        this.applyId = applyId;
    }
    
    @Column(name = "MATERIALS_TYPE")
    public String getMaterialsType()
    {
        return materialsType;
    }
    
    public void setMaterialsType(String materialsType)
    {
        this.materialsType = materialsType;
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
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @Column(name = "MATERIALS_COUNT")
    public Integer getMaterialsCount()
    {
        return materialsCount;
    }
    
    public void setMaterialsCount(Integer materialsCount)
    {
        this.materialsCount = materialsCount;
    }
    
    @Column(name = "MATERIALS_SEND_COUNT")
    public Integer getMaterialsSendCount()
    {
        return materialsSendCount;
    }
    
    public void setMaterialsSendCount(Integer materialsSendCount)
    {
        this.materialsSendCount = materialsSendCount;
    }
    
}
